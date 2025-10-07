package com.udacity.webcrawler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.udacity.webcrawler.config.CrawlerConfiguration;
import com.udacity.webcrawler.config.ConfigurationLoader;
import com.udacity.webcrawler.di.WebCrawlerModule;
import com.udacity.webcrawler.crawler.WebCrawler;
import com.udacity.webcrawler.result.CrawlResult;
import com.udacity.webcrawler.result.CrawlResultWriter;
import java.io.InputStream;
import java.nio.file.Path;

public final class WebCrawlerMain {

    public static void main(String[] args) throws Exception {
        CrawlerConfiguration config;

        if (args.length > 0) {
            Path configPath = Path.of(args[0]);
            config = new ConfigurationLoader(configPath).load();
        } else {
            try (InputStream is = WebCrawlerMain.class.getResourceAsStream("/config.json")) {
                if (is == null) {
                    throw new IllegalArgumentException("config.json not found in resources!");
                }
                config = new ConfigurationLoader(is).load();
            }
        }

        Injector injector = Guice.createInjector(new WebCrawlerModule(config));

        WebCrawler crawler = injector.getInstance(WebCrawler.class);
        CrawlResultWriter writer = injector.getInstance(CrawlResultWriter.class);

        CrawlResult result = crawler.crawl(config.getStartPages());

        writer.setCrawlResult(result);
        writer.write();
    }
}