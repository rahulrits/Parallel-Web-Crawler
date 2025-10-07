package com.udacity.webcrawler.result;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrawlResultWriter {
    private final Path resultPath; // FIX: Add Path field
    private CrawlResult crawlResult;

    // FIX: Add constructor to accept the output Path
    public CrawlResultWriter(Path resultPath) {
        this.resultPath = resultPath;
    }

    public void setCrawlResult(CrawlResult crawlResult) {
        this.crawlResult = crawlResult;
    }

    public void write() throws IOException {
        if (crawlResult == null) {
            System.err.println("Crawl result is null. Skipping write operation.");
            return;
        }

        // FIX: Implement logic to write to file or System.out using try-with-resources
        if (resultPath != null && !resultPath.toString().isEmpty()) {
            // Write to file path if one is provided
            try (Writer writer = Files.newBufferedWriter(resultPath)) {
                writer.write(crawlResult.toJson());
            }
        } else {
            // Write to standard output if no file path is provided
            System.out.println(crawlResult.toJson());
        }
    }
}