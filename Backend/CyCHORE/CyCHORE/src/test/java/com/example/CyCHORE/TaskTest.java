package com.example.CyCHORE;

import com.example.CyCHORE.Listings.ListingsController;
import com.example.CyCHORE.Task.TaskController;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

    @MockBean
    TaskController tc;

    @Mock
    HttpServletRequest request;

    @Test
    @RequestMapping(value = "/getTaskList", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void getTaskListTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("5");
        try {
            when(tc.getTaskList(request)).thenReturn("0");
            assertEquals("0", tc.getTaskList(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, tc.getTaskList(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    @RequestMapping(value = "/getTaskListHistory", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void getTaskListHistoryTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("7");
        try {
            when(tc.getTaskListHistory(request)).thenReturn("1");
            assertEquals("1", tc.getTaskListHistory(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, tc.getTaskListHistory(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    @RequestMapping(value = "/createTask", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void createTaskTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("8");
        try {
            when(tc.createTask(request)).thenReturn("0");
            assertEquals("0", tc.createTask(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, tc.createTask(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    @RequestMapping(value = "/pickup", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void pickTaskTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(6));
        //task_id
        request.addParameter(String.valueOf(3));
        try {
            when(tc.assignPickUpTaskToUser(request)).thenReturn("0");
            assertEquals("0", tc.assignPickUpTaskToUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //uid
            request2.addParameter(String.valueOf(12));
            //task_id
            request2.addParameter(String.valueOf(5));
            assertEquals("0", tc.assignPickUpTaskToUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    @RequestMapping(value = "/getTaskPool", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void getTaskPoolTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(10));
        //group_id
        request.addParameter(String.valueOf(4));
        try {
            when(tc.getTaskPool(request)).thenReturn("0");
            assertEquals("0", tc.getTaskPool(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //uid
            request2.addParameter(String.valueOf(7));
            //group_id
            request2.addParameter(String.valueOf(2));
            assertEquals("0", tc.getTaskPool(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/ChangeTaskStatus", method = POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void ChangeTaskStatusTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(15));
        //task_id
        request.addParameter(String.valueOf(3));
        try {
            when(tc.ChangeTaskStatus(request)).thenReturn("0");
            assertEquals("0", tc.ChangeTaskStatus(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //uid
            request.addParameter(String.valueOf(8));
            //task_id
            request.addParameter(String.valueOf(3));
            assertEquals("0", tc.ChangeTaskStatus(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}