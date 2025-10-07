package com.udacity.webcrawler.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class ConfigurationLoader {

    private final Path configPath;
    private final InputStream inputStream;

    // Constructor for Path
    public ConfigurationLoader(Path configPath) {
        this.configPath = configPath;
        this.inputStream = null;
    }

    // Constructor for InputStream
    public ConfigurationLoader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.configPath = null;
    }

    public CrawlerConfiguration load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if (configPath != null) {
            // try-with-resources ensures automatic closing
            try (InputStream is = java.nio.file.Files.newInputStream(configPath)) {
                return mapper.readValue(is, CrawlerConfiguration.class);
            }
        } else if (inputStream != null) {
            try (InputStream is = inputStream) {
                return mapper.readValue(is, CrawlerConfiguration.class);
            }
        } else {
            throw new IllegalStateException("No configuration source provided.");
        }
    }
}
