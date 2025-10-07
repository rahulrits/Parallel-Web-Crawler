package com.udacity.webcrawler.config;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

class ConfigurationLoaderTest {
    @Test
    void loadConfig() throws Exception {
        String json = """
            {
                "startPages":["http://example.com"],
                "ignoredUrls":["exclude.com"],
                "ignoredWords":["the", "a"],
                "parallelism":4,
                "implementationOverride":"parallel",
                "maxDepth":5,
                "timeoutSeconds":10,
                "popularWordCount":100,
                "profileOutputPath":"/tmp/profile.json",
                "resultPath":"/tmp/result.json"
            }
            """;

        // Create a temporary configuration file
        Path tmp = Files.createTempFile("config", ".json");
        Files.writeString(tmp, json);

        ConfigurationLoader loader = new ConfigurationLoader(tmp);
        CrawlerConfiguration config = loader.load();

        // FULL VERIFICATION OF ALL FIELDS
        assertThat(config.getStartPages()).containsExactly("http://example.com");
        assertThat(config.getIgnoredUrls()).containsExactly("exclude.com");
        assertThat(config.getIgnoredWords()).containsExactly("the", "a");
        assertThat(config.getParallelism()).isEqualTo(4);
        assertThat(config.getImplementationOverride()).isEqualTo("parallel");

        // Assert critical runtime fields
        assertThat(config.getMaxDepth()).isEqualTo(5);
        assertThat(config.getTimeoutSeconds()).isEqualTo(10);
        assertThat(config.getPopularWordCount()).isEqualTo(100);

        // Assert file paths
        assertThat(config.getProfileOutputPath()).isEqualTo("/tmp/profile.json");
        assertThat(config.getResultPath()).isEqualTo("/tmp/result.json");
    }
}