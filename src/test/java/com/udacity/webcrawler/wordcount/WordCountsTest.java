// File: src/test/java/com/udacity/webcrawler/wordcount/WordCountsTest.java
package com.udacity.webcrawler.wordcount;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.Map;

class WordCountsTest {
    @Test
    void sortTest() {
        Map<String, Integer> counts = Map.of("foo",3,"bar",3,"baz",2);
        var sorted = WordCounts.sort(counts,3);
        assertThat(sorted.get(0).getKey()).isEqualTo("bar"); // same frequency but alphabetically first
    }
}
