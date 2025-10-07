package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

class WebCrawlerTest {
    @Test
    void sequentialAndParallelMatch() throws Exception {

        // The Result constructor requires (Map<String, Integer> wordCounts, Set<String> links).
        PageParser parser = url -> new Result(Map.of("foo", 1), Set.of());

        // SequentialWebCrawler constructor takes (PageParser, int maxDepth)
        SequentialWebCrawler seq = new SequentialWebCrawler(parser, 1);

        List<Pattern> ignoredUrls = Collections.emptyList();
        ParallelWebCrawler par = new ParallelWebCrawler(parser, 1, 5, ignoredUrls);

        assertThat(seq.crawl(List.of("a.com")).getWordCounts()).isEqualTo(par.crawl(List.of("a.com")).getWordCounts());
    }
}