package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import com.udacity.webcrawler.result.CrawlResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern; // Necessary import

public class ParallelWebCrawler implements WebCrawler {
    private final PageParser parser;
    private final int maxDepth;
    private final long deadline;
    private final List<Pattern> ignoredUrls; // Add Ignored URLs list
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<String, Integer> wordCounts = new ConcurrentHashMap<>();

    public ParallelWebCrawler(PageParser parser, int maxDepth, int timeoutSeconds, List<Pattern> ignoredUrls) {
        this.parser = parser;
        this.maxDepth = maxDepth;
        this.deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutSeconds);
        this.ignoredUrls = ignoredUrls; // Initialize ignoredUrls
    }

    @Override
    public int getMaxParallelism() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public CrawlResult crawl(List<String> startingUrls) {
        List<CrawlTask> tasks = new ArrayList<>();
        for (String url : startingUrls) tasks.add(new CrawlTask(url, 0));

        // FIX: Revert to manual fork/join loop for initial tasks outside the ForkJoinTask context
        for (CrawlTask task : tasks) task.fork();
        for (CrawlTask task : tasks) task.join();

        return new CrawlResult(wordCounts, visited);
    }

    private class CrawlTask extends RecursiveAction {
        private final String url;
        private final int depth;

        CrawlTask(String url, int depth) {
            this.url = url;
            this.depth = depth;
        }

        @Override
        protected void compute() {
            if (System.nanoTime() > deadline) {
                return;
            }

            if (depth > maxDepth || !visited.add(url)) return;

            if (ignoredUrls.stream().anyMatch(p -> p.matcher(url).matches())) {
                return;
            }

            Result page;
            try {
                page = parser.parse(url);

                if (System.nanoTime() > deadline) {
                    return;
                }

                page.getWordCounts().forEach((k, v) -> wordCounts.merge(k, v, Integer::sum));

                List<CrawlTask> subtasks = page.getLinks().stream()
                        .filter(link -> System.nanoTime() <= deadline)
                        .map(link -> new CrawlTask(link, depth + 1))
                        .toList();

                // Correct: invokeAll() is available inside a RecursiveAction's compute()
                invokeAll(subtasks);

            } catch (Exception ignored) {
            }
        }
    }
}