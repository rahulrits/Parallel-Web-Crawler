package com.udacity.webcrawler.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class PageParserImpl implements PageParser {

    private final int popularWordCount;
    private final Set<String> ignoredUrls;

    // Constructor matching your factory usage
    public PageParserImpl(int popularWordCount, Set<String> ignoredUrls) {
        this.popularWordCount = popularWordCount;
        this.ignoredUrls = ignoredUrls;
    }

    // No-arg constructor for Dagger / other use
    public PageParserImpl() {
        this.popularWordCount = 5;
        this.ignoredUrls = new HashSet<>();
    }

    @Override
    public Result parse(String url) {
        // Simulated parsing logic
        if (ignoredUrls.contains(url)) {
            return new Result(new HashMap<>(), new HashSet<>());
        }

        Map<String, Integer> wordCounts = new HashMap<>();
        wordCounts.put("example", 1);
        wordCounts.put("webcrawler", 2);

        Set<String> links = new HashSet<>();
        links.add("http://example.com/about");
        links.add("http://example.com/contact");

        return new Result(wordCounts, links);
    }
}
