package com.udacity.webcrawler.crawler;

import com.udacity.webcrawler.result.CrawlResult;
import java.util.List;

public interface WebCrawler {
    CrawlResult crawl(List<String> startingUrls);
    int getMaxParallelism();
}