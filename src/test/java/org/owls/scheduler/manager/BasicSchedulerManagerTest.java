package org.owls.scheduler.manager;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicSchedulerManagerTest {

    @Test
    public void testStart() {}
    @Test
    public void testStop() {}
    @Test
    public void testRegisterSchedule() {}
    @Test
    public void testRemoveSchedule() {
        BasicSchedulerManager sut = new BasicSchedulerManager();
        int scheduleCount = 3;
        for(int i  = 0; i < scheduleCount; i++) {
            sut.registerSchedule(String.format("Schedule%d", i), "* * * * * *", param -> {});
        }
        sut.removeSchedule("Schedule1");
        sut.removeSchedule("Schedule2");
        Set<String> scheduleNames = sut.listSchedule();
        assertThat(scheduleNames.size()).isEqualTo(1);
        assertThat(scheduleNames).containsExactly("Schedule0");
    }
    @Test
    public void listSchedule() {
        BasicSchedulerManager sut = new BasicSchedulerManager();
        int scheduleCount = 3;
        for(int i  = 0; i < scheduleCount; i++) {
            sut.registerSchedule(String.format("Schedule%d", i), "* * * * * *", param -> {});
        }
        assertThat(sut.listSchedule().size()).isEqualTo(scheduleCount);
    }
}
