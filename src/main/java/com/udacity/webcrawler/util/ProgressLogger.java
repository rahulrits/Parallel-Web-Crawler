// File: src/main/java/com/udacity/webcrawler/util/ProgressLogger.java
package com.udacity.webcrawler.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class to log progress of the crawler.
 */
public final class ProgressLogger {
    private final AtomicInteger pagesVisited = new AtomicInteger(0);
    private final AtomicLong startTime = new AtomicLong(System.currentTimeMillis());

    public void pageVisited(String url) {
        int count = pagesVisited.incrementAndGet();
        if (count % 10 == 0) { // log every 10 pages
            long elapsed = System.currentTimeMillis() - startTime.get();
            System.out.println("Visited " + count + " pages. Latest: " + url + ". Time elapsed: " + (elapsed/1000) + "s");
        }
    }

    public int getPagesVisited() { return pagesVisited.get(); }
}
