package com.example.CyCHORE;

import com.example.CyCHORE.Request.Request;
import com.example.CyCHORE.Request.RequestController;

import static com.example.CyCHORE.Request.RequestController.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.CyCHORE.Request.RequestRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Request_Test {

    @Mock
    HttpServletRequest request;

    @Mock
    RequestRepository rr;

    @Test
    public void fileNewRequestTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("uid", "1");
        ((MockHttpServletRequest) request).addParameter("type", "1");
        try {
            Request r = mock(Request.class);
            when(rr.save(r)).thenReturn(null);
            JSONObject expected = new JSONObject();
            expected.put("status", "0");
            assertEquals(expected.toString(), fileNewRequest(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void fileNewRequestTest2() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("ud", "1");
        ((MockHttpServletRequest) request).addParameter("type", "1");
        try {
            Request r = mock(Request.class);
            when(rr.save(r)).thenReturn(null);
            JSONObject expected = new JSONObject();
            expected.put("status", "1");
            assertEquals(expected.toString(), fileNewRequest(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getRequestTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("ud", "1");
        ((MockHttpServletRequest) request).addParameter("type", "1");
        ((MockHttpServletRequest) request).addParameter("request_status", "5");
        try {
            Request r = mock(Request.class);
            ArrayList<Request> l = new ArrayList<>();
            l.add(r);
            when(rr.findAll()).thenReturn(l);
            JSONObject expected = new JSONObject();
            Map<Long, JSONObject> treeMap = new TreeMap<>();
            expected.put("status", "0");
            expected.put("num_requests", 0);
            expected.put("requests", treeMap.values());
            assertEquals(expected.toString(), getRequest(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void changeRequestStatusTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("rid", "1");
        ((MockHttpServletRequest) request).addParameter("uid", "1");
        ((MockHttpServletRequest) request).addParameter("toStatus", "1");
        ((MockHttpServletRequest) request).addParameter("response", "hi");

        try {
            int r = 1;
            when(rr.existsById(r)).thenReturn(true);
            JSONObject expected = new JSONObject();
            expected.put("status", "0");
            assertEquals(expected.toString(), changeRequestStatus(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeRequestStatusTest2() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("rid", "1");
        ((MockHttpServletRequest) request).addParameter("uid", "1");
        ((MockHttpServletRequest) request).addParameter("toStatus", "1");
        ((MockHttpServletRequest) request).addParameter("response", "hi");

        try {
            int r = 1;
            when(rr.existsById(r)).thenReturn(false);
            JSONObject expected = new JSONObject();
            expected.put("status", "1");
            assertEquals(expected.toString(), changeRequestStatus(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

