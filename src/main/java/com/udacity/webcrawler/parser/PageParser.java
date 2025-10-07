package com.udacity.webcrawler.parser;

import java.util.Map;
import java.util.Set;

public interface PageParser {

    /**
     * A simple container for parsed page data: word counts and discovered links.
     */
    final class Result {
        private final Map<String, Integer> wordCounts;
        private final Set<String> links;

        public Result(Map<String, Integer> wordCounts, Set<String> links) {
            this.wordCounts = wordCounts;
            this.links = links;
        }

        public Map<String, Integer> getWordCounts() {
            return wordCounts;
        }

        public Set<String> getLinks() {
            return links;
        }
    }

    /**
     * Parses the given URL and returns the words and links found.
     */
    Result parse(String url);
}
