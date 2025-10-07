package com.udacity.webcrawler.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

@JsonDeserialize(builder = CrawlerConfiguration.Builder.class)
public final class CrawlerConfiguration {
    private final List<String> startPages;
    private final Set<String> ignoredUrls;
    private final Set<String> ignoredWords;
    private final int parallelism;
    private final String implementationOverride;
    private final int maxDepth;
    private final int timeoutSeconds;
    private final int popularWordCount;
    private final String profileOutputPath;
    private final String resultPath;

    private CrawlerConfiguration(Builder builder) {
        this.startPages = builder.startPages;
        this.ignoredUrls = builder.ignoredUrls;
        this.ignoredWords = builder.ignoredWords;
        this.parallelism = builder.parallelism;
        this.implementationOverride = builder.implementationOverride;
        this.maxDepth = builder.maxDepth;
        this.timeoutSeconds = builder.timeoutSeconds;
        this.popularWordCount = builder.popularWordCount;
        this.profileOutputPath = builder.profileOutputPath;
        this.resultPath = builder.resultPath;
    }

    public List<String> getStartPages() { return startPages; }
    public Set<String> getIgnoredUrls() { return ignoredUrls; }
    public Set<String> getIgnoredWords() { return ignoredWords; }
    public int getParallelism() { return parallelism; }
    public String getImplementationOverride() { return implementationOverride; }
    public int getMaxDepth() { return maxDepth; }
    public int getTimeoutSeconds() { return timeoutSeconds; }
    public int getPopularWordCount() { return popularWordCount; }
    public String getProfileOutputPath() { return profileOutputPath; }
    public String getResultPath() { return resultPath; }

    @JsonDeserialize(builder = Builder.class)
    public static class Builder {
        @JsonProperty("startPages")
        private List<String> startPages;
        @JsonProperty("ignoredUrls")
        private Set<String> ignoredUrls;
        @JsonProperty("ignoredWords")
        private Set<String> ignoredWords;
        @JsonProperty("parallelism")
        private int parallelism;
        @JsonProperty("implementationOverride")
        private String implementationOverride;
        @JsonProperty("maxDepth")
        private int maxDepth;
        @JsonProperty("timeoutSeconds")
        private int timeoutSeconds;
        @JsonProperty("popularWordCount")
        private int popularWordCount;
        @JsonProperty("profileOutputPath")
        private String profileOutputPath;
        @JsonProperty("resultPath")
        private String resultPath;

        public Builder startPages(List<String> startPages) { this.startPages = startPages; return this; }
        public Builder ignoredUrls(Set<String> ignoredUrls) { this.ignoredUrls = ignoredUrls; return this; }
        public Builder ignoredWords(Set<String> ignoredWords) { this.ignoredWords = ignoredWords; return this; }
        public Builder parallelism(int parallelism) { this.parallelism = parallelism; return this; }
        public Builder implementationOverride(String implementationOverride) { this.implementationOverride = implementationOverride; return this; }
        public Builder maxDepth(int maxDepth) { this.maxDepth = maxDepth; return this; }
        public Builder timeoutSeconds(int timeoutSeconds) { this.timeoutSeconds = timeoutSeconds; return this; }
        public Builder popularWordCount(int popularWordCount) { this.popularWordCount = popularWordCount; return this; }
        public Builder profileOutputPath(String profileOutputPath) { this.profileOutputPath = profileOutputPath; return this; }
        public Builder resultPath(String resultPath) { this.resultPath = resultPath; return this; }

        public CrawlerConfiguration build() { return new CrawlerConfiguration(this); }
    }
}
