package com.udacity.webcrawler.profiler;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import java.io.StringWriter;
import java.io.Writer;
import java.time.Duration;
import com.udacity.webcrawler.crawler.Profiled;

class ProfilerImplTest {

    // Test to ensure multiple calls to the same method are correctly aggregated
    @Test
    void testRecordAndWriteData_aggregation() throws Exception {
        ProfilerImpl profiler = new ProfilerImpl();

        // Record several durations for the same method
        Duration duration1 = Duration.ofMillis(100);
        Duration duration2 = Duration.ofMillis(200);

        profiler.record("methodA", duration1);
        profiler.record("methodA", duration2);

        // Record duration for a second method
        profiler.record("methodB", Duration.ofMillis(50));

        Writer writer = new StringWriter();
        profiler.writeData(writer);

        String output = writer.toString();

        // Expected total for methodA is 300 ms (100 + 200)
        assertThat(output).contains("methodA: 300 ms");

        // Expected total for methodB is 50 ms
        assertThat(output).contains("methodB: 50 ms");
    }

    // A simplified test based on the original structure (without proxy logic)
    @Test
    void testSimpleRecordAndWrite() throws Exception {
        ProfilerImpl profiler = new ProfilerImpl();

        profiler.record("testMethod", Duration.ofMillis(1234));

        Writer writer = new StringWriter();
        profiler.writeData(writer);

        String output = writer.toString();

        assertThat(output).contains("testMethod: 1234 ms");
    }
}