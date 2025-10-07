package com.udacity.webcrawler.profiler;

import java.io.IOException;
import java.io.Writer;
import java.time.Duration;

/**
 * Handles profiling data for methods annotated with @Profiled.
 */
public interface Profiler {

    /**
     * Records the duration of a method invocation.
     *
     * @param methodName the method's name
     * @param duration   the time taken by the method
     */
    void record(String methodName, Duration duration);

    /**
     * Writes the profiling data to the given writer.
     *
     * @param writer the output writer
     * @throws IOException if writing fails
     */
    void writeData(Writer writer) throws IOException;
}
