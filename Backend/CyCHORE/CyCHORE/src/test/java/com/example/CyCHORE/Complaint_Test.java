package com.example.CyCHORE;

import com.example.CyCHORE.Complaint.ComplaintController;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Complaint_Test {

    @MockBean

    ComplaintController complaint_mock;
    @Mock
    HttpServletRequest request;

    @Test
    @RequestMapping(value = "/getComplaintListforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateComplaintsTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("1");
        try {
            when(complaint_mock.getComplaint(request)).thenReturn("1");
            assertEquals("1", complaint_mock.getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, complaint_mock.getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getComplaintListforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateComplaintsTest2() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("2");
        try {
            when(complaint_mock.getComplaint(request)).thenReturn("0");
            assertEquals("0", complaint_mock.getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, complaint_mock.getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getComplaintListforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateComplaintsTest3() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("3");
        try {
            when(complaint_mock.getComplaint(request)).thenReturn("0");
            assertEquals("0", complaint_mock.getComplaint(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, complaint_mock.getComplaint(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}