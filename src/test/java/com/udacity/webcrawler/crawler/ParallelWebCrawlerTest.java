package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

class ParallelWebCrawlerTest {
    @Test
    void maxDepthEnforced() throws Exception {
        // Change ParsedPage to Result and use the correct constructor signature
        PageParser parser = url -> new Result(Map.of("word",1), Set.of("b.com"));
        ParallelWebCrawler crawler = new ParallelWebCrawler(parser, 0, 5);

        var result = crawler.crawl(List.of("a.com"));
        assertThat(result.getUrlsVisited()).containsExactly("a.com");
    }
}