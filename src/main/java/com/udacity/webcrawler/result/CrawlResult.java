package com.udacity.webcrawler.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public final class CrawlResult {

    private final Map<String, Integer> wordCounts;
    private final Set<String> urlsVisited;

    public CrawlResult(ConcurrentMap<String, Integer> wordCounts, Set<String> urlsVisited) {
        this.wordCounts = Collections.unmodifiableMap(wordCounts);
        this.urlsVisited = Collections.unmodifiableSet(urlsVisited);
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public Set<String> getUrlsVisited() {
        return urlsVisited;
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Map.of(
                    "wordCounts", wordCounts,
                    "urlsVisited", urlsVisited // Updated key to reflect the Set
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert CrawlResult to JSON", e);
        }
    }
}