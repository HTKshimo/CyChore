package com.example.CyCHORE;

import com.example.CyCHORE.Chatroom.*;
import com.example.CyCHORE.Group.GroupController;
import com.example.CyCHORE.Task.Task;
import com.example.CyCHORE.Task.TaskRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.CyCHORE.Chatroom.MessageController.addMessage;
import static com.example.CyCHORE.Chatroom.MessageController.getChatroomMessages;
import static com.example.CyCHORE.Chatroom.UserChatroomController.getChatroomUsers;
import static com.example.CyCHORE.Chatroom.ChatroomController.setLastUpdatedTimestamp;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupTest {

    @Mock
    TaskRepository tr;

    @MockBean
    GroupController gc;
    @Mock
    HttpServletRequest request;

    @Mock
    UserChatroomRepository ucr;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

        @Test
        @RequestMapping(value = "/getFinishedTasksInGroup", method = POST, produces ="application/json;charset=UTF-8")
        @ResponseBody
        public void ValidategetFinishedTasksInGroup() throws IOException {

            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addParameter("5");
            try {
                when(gc.getFinishedTasksInGroup(request)).thenReturn("0");
                assertEquals("0", gc.getFinishedTasksInGroup(request));
                MockHttpServletRequest request2 = new MockHttpServletRequest();
                assertEquals(null, gc.getFinishedTasksInGroup(request2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    @Test
    @RequestMapping(value = "/getGroupTaskPool", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidategetGroupTaskPool() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("5");
        try {
            when(gc.getGroupTaskPool(request)).thenReturn("0");
            assertEquals("0", gc.getGroupTaskPool(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, gc.getGroupTaskPool(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}