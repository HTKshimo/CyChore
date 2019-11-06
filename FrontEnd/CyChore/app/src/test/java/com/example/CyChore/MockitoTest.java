package com.example.CyChore;

import com.example.CyChore.data.TaskCollection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MockitoTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        assertEquals(1, 1);
    }

    @Test
    public void testTasks() {
        TaskCollection mockedTasks = mock(TaskCollection.class);
        mockedTasks.addItem(new TaskCollection.TaskItem(10002,"kill bug",1569623441258L,1));
        verify(mockedTasks).addItem(new TaskCollection.TaskItem(10002,"kill bug",1569623441258L,1));
        assertEquals("Overdue!", mockedTasks.ITEMS.get(0).dueTime);
    }

    @Test
    public void testJoinGroup()
    {
        JoinGroup mockedGroup = mock(JoinGroup.class);
        mockedGroup.setJoinGroup(10);
        verify(mockedGroup).setJoinGroup(10);
        //assertEquals("10", mockedGroup.newID);
    }
}
