package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParser.Result;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;

class ParallelWebCrawlerTest {
    @Test
    void maxDepthEnforced() throws Exception {

        PageParser parser = url -> new Result(Map.of("word",1), Set.of("b.com"));


        List<Pattern> ignoredUrls = Collections.emptyList();
        ParallelWebCrawler crawler = new ParallelWebCrawler(parser, 0, 5, ignoredUrls);

        var result = crawler.crawl(List.of("a.com"));
        assertThat(result.getUrlsVisited()).containsExactly("a.com");
    }
}