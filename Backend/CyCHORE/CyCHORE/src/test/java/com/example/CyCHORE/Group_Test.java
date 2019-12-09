package com.example.CyCHORE;

import com.example.CyCHORE.Listings.ListingsController;
import com.example.CyCHORE.Listings.ListingsController.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.json.JSONException;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Group_Test {

    @MockBean
    //DataService dataServiceMock;
            ListingsController list_mock;
    @Mock
    HttpServletRequest request;

    //@Autowired
    //BusinessService businessImpl;
    //MADE MODIFICATIONS!!!!!
    @Test
    @RequestMapping(value = "/getAllGroups", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("5");
        try {
            when(list_mock.getListingforUser(request)).thenReturn("0");
            assertEquals("0", list_mock.getListingforUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, list_mock.getListingforUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    @RequestMapping(value = "/getListingforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest2() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("7");
        try {
            when(list_mock.getListingforUser(request)).thenReturn("1");
            assertEquals("1", list_mock.getListingforUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, list_mock.getListingforUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getListingforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest3() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("8");
        try {
            when(list_mock.getListingforUser(request)).thenReturn("0");
            assertEquals("0", list_mock.getListingforUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, list_mock.getListingforUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}