package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import com.udacity.webcrawler.result.CrawlResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ParallelWebCrawler implements WebCrawler {
    private final PageParser parser;
    private final int maxDepth;
    private final long deadline; // FIX: Add deadline field
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<String, Integer> wordCounts = new ConcurrentHashMap<>();

    // FIX: Update constructor signature to accept timeoutSeconds
    public ParallelWebCrawler(PageParser parser, int maxDepth, int timeoutSeconds) {
        this.parser = parser;
        this.maxDepth = maxDepth;
        // FIX: Calculate the deadline (in nanoseconds) upon creation
        this.deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutSeconds);
    }

    @Override
    public int getMaxParallelism() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public CrawlResult crawl(List<String> startingUrls) {
        List<CrawlTask> tasks = new ArrayList<>();
        for (String url : startingUrls) tasks.add(new CrawlTask(url, 0));

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
            // FIX: Check if the deadline has passed (Timeout Logic)
            if (System.nanoTime() > deadline) {
                return;
            }

            if (depth > maxDepth || !visited.add(url)) return;

            try {
                Result page = parser.parse(url);
                page.getWordCounts().forEach((k, v) -> wordCounts.merge(k, v, Integer::sum));

                List<CrawlTask> subtasks = page.getLinks().stream()
                        .map(link -> new CrawlTask(link, depth + 1))
                        .toList();

                for (CrawlTask task : subtasks) task.fork();
                for (CrawlTask task : subtasks) task.join();
            } catch (Exception ignored) {}
        }
    }
}