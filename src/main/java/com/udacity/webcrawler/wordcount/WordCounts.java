package com.udacity.webcrawler.wordcount;

import java.util.*;

public final class WordCounts {
    public static List<Map.Entry<String, Integer>> sort(Map<String, Integer> wordCounts, int popularWordCount) {
        return wordCounts.entrySet().stream()
                .sorted(Comparator
                        .comparing(Map.Entry<String, Integer>::getValue, Comparator.reverseOrder())
                        .thenComparing(e -> e.getKey().length(), Comparator.reverseOrder())
                        .thenComparing(Map.Entry::getKey))
                .limit(popularWordCount)
                .toList();
    }
}
