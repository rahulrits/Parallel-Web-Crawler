package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import com.udacity.webcrawler.result.CrawlResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SequentialWebCrawler implements WebCrawler {
    private final PageParser parser;
    private final int maxDepth;
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<String, Integer> wordCounts = new ConcurrentHashMap<>();

    public SequentialWebCrawler(PageParser parser, int maxDepth) {
        this.parser = parser;
        this.maxDepth = maxDepth;
    }

    @Override
    public int getMaxParallelism() {
        return 1;
    }

    @Override
    public CrawlResult crawl(List<String> startingUrls) {
        for (String url : startingUrls) crawl(url, 0);
        return new CrawlResult(wordCounts, visited);
    }

    private void crawl(String url, int depth) {
        if (depth > maxDepth || !visited.add(url)) return;

        try {
            Result page = parser.parse(url);
            page.getWordCounts().forEach((k, v) -> wordCounts.merge(k, v, Integer::sum));
            page.getLinks().forEach(link -> crawl(link, depth + 1));
        } catch (Exception ignored) {}
    }
}
