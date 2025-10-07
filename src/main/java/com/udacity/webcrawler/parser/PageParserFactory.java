package com.udacity.webcrawler.parser;

import java.util.Set;

/**
 * Factory to create PageParser instances with given configuration.
 */
public final class PageParserFactory {
    private final int timeoutSeconds;
    private final Set<String> ignoredWords;

    public PageParserFactory(int timeoutSeconds, Set<String> ignoredWords) {
        this.timeoutSeconds = timeoutSeconds;
        this.ignoredWords = ignoredWords;
    }

    public PageParser createParser() {
        return new PageParserImpl(timeoutSeconds, ignoredWords);
    }
}
