package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

class WebCrawlerTest {
    @Test
    void sequentialAndParallelMatch() throws Exception {

        // The Result constructor requires (Map<String, Integer> wordCounts, Set<String> links).
        PageParser parser = url -> new Result(Map.of("foo", 1), Set.of());

        // SequentialWebCrawler constructor takes (PageParser, int maxDepth)
        SequentialWebCrawler seq = new SequentialWebCrawler(parser, 1);

        // FIX: ParallelWebCrawler now takes three arguments (PageParser, maxDepth, timeoutSeconds)
        ParallelWebCrawler par = new ParallelWebCrawler(parser, 1, 5);

        assertThat(seq.crawl(List.of("a.com")).getWordCounts()).isEqualTo(par.crawl(List.of("a.com")).getWordCounts());
    }
}