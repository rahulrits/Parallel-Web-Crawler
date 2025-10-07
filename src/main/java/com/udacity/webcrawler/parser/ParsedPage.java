package com.udacity.webcrawler.parser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ParsedPage {
    private final String url;
    private final Set<String> links;
    private final Map<String, Integer> wordCounts;

    public ParsedPage(String url, Set<String> links, Map<String, Integer> wordCounts) {
        this.url = url;
        this.links = links;
        this.wordCounts = wordCounts;
    }

    public String getUrl() { return url; }
    public Set<String> getLinks() { return links; }
    public Map<String, Integer> getWordCounts() { return wordCounts; }
}
