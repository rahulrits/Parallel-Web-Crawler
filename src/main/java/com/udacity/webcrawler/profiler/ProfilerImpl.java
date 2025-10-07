package com.udacity.webcrawler.profiler;

import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProfilerImpl implements Profiler {

    private final Map<String, Duration> profilingData = new ConcurrentHashMap<>();

    @Override
    public void record(String methodName, Duration duration) {
        profilingData.merge(methodName, duration, Duration::plus);
    }

    @Override
    public void writeData(Writer writer) throws IOException {
        for (Map.Entry<String, Duration> entry : profilingData.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue().toMillis() + " ms\n");
        }
    }
}
