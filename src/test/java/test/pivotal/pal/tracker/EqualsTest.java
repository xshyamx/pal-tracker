package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsTest {
    @Test
    public void testEquals() {
        TimeEntry t1 = new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8);
        TimeEntry t2 = new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8);
        assertThat(t1).isEqualTo(t2);

    }
}
