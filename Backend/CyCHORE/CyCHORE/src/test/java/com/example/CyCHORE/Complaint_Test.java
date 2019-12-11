package com.example.CyCHORE;

import com.example.CyCHORE.Complaint.Complaint;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.example.CyCHORE.Complaint.ComplaintController.*;
import com.example.CyCHORE.Complaint.ComplaintRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class Complaint_Test {
//
//    @MockBean
//    ComplaintController complaint_mock;

    @Mock
    ComplaintRepository cr;

    @Mock
    HttpServletRequest request;

    @Test
    public void fileNewRequestTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("uid", "1");
        ((MockHttpServletRequest) request).addParameter("type", "1");
        try {
            Complaint r = mock(Complaint.class);
            when(cr.save(r)).thenReturn(null);
            JSONObject expected = new JSONObject();
            expected.put("status", "0");
            assertEquals(expected.toString(), fileNewComplaint(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void changeRequestStatusTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("cid", "1");
        try {
            int r = 1;
            when(cr.existsById(r)).thenReturn(true);
            JSONObject expected = new JSONObject();
            expected.put("status", "0");
            assertEquals(expected.toString(), approveComplaint(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeRequestStatusTest2() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("cid", "1");

        try {
            int r = 1;
            when(cr.existsById(r)).thenReturn(false);
            JSONObject expected = new JSONObject();
            expected.put("status", "1");
            assertEquals(expected.toString(), approveComplaint(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ValidateComplaintsTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("1");
        try {
            when(getComplaint(request)).thenReturn("1");
            assertEquals("1", getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void ValidateComplaintsTest2() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("2");
        try {
            when(getComplaint(request)).thenReturn("0");
            assertEquals("0", getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void ValidateComplaintsTest3() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("3");
        try {
            when(getComplaint(request)).thenReturn("0");
            assertEquals("0", getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
