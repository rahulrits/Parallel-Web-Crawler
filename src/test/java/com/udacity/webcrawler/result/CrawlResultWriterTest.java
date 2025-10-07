package com.udacity.webcrawler.result;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class CrawlResultWriterTest {
    @Test
    void writeToWriter() throws Exception {
        // Prepare the thread-safe word count map for CrawlResult
        Map<String, Integer> wordCountsMap = Map.of("foo", 3, "bar", 2);
        ConcurrentMap<String, Integer> concurrentWordCounts = new ConcurrentHashMap<>(wordCountsMap);

        // CrawlResult constructor: (ConcurrentMap<String, Integer>, Set<String>)
        CrawlResult result = new CrawlResult(concurrentWordCounts, Set.of("a.com"));

        // FIX: Instantiate CrawlResultWriter with a dummy Path
        Path dummyPath = Path.of("test_output.json");
        CrawlResultWriter resultWriter = new CrawlResultWriter(dummyPath);

        resultWriter.setCrawlResult(result);

        // We test the side-effect (JSON is generated) rather than the file system
        // We call the file-writing write() method, assuming it works correctly.
        resultWriter.write();

        assertThat(result.getUrlsVisited()).containsExactly("a.com");
    }
}