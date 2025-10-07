package com.udacity.webcrawler.di;

import com.google.inject.AbstractModule;
import com.udacity.webcrawler.config.CrawlerConfiguration;
import com.udacity.webcrawler.crawler.ParallelWebCrawler;
import com.udacity.webcrawler.crawler.SequentialWebCrawler;
import com.udacity.webcrawler.crawler.WebCrawler;
import com.udacity.webcrawler.parser.PageParserFactory;
import com.udacity.webcrawler.result.CrawlResultWriter;
import com.google.inject.Provides;

import javax.inject.Inject;
import java.nio.file.Path;

public class WebCrawlerModule extends AbstractModule {
    private final CrawlerConfiguration config;

    @Inject
    public WebCrawlerModule(CrawlerConfiguration config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        // FIX: The CrawlResultWriter now requires the output Path
        bind(CrawlResultWriter.class).toInstance(new CrawlResultWriter(
                Path.of(config.getResultPath() != null ? config.getResultPath() : "")
        ));

        boolean isParallel = config.getParallelism() > 1;

        if (isParallel) {
            bind(WebCrawler.class).toInstance(new ParallelWebCrawler(
                    getParserFactory().createParser(),
                    config.getMaxDepth(),
                    config.getTimeoutSeconds() // FIX: Pass timeoutSeconds
            ));
        } else {
            bind(WebCrawler.class).toInstance(new SequentialWebCrawler(
                    getParserFactory().createParser(),
                    config.getMaxDepth()
            ));
        }
    }

    @Provides
    PageParserFactory getParserFactory() {
        return new PageParserFactory(config.getTimeoutSeconds(), config.getIgnoredWords());
    }
}