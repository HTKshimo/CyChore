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
import java.math.BigDecimal;
//import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Listings_Test {

    @MockBean
    //DataService dataServiceMock;
            ListingsController list_mock;
    @Mock
    HttpServletRequest request;

    //@Autowired
    //BusinessService businessImpl;
    //MADE MODIFICATIONS!!!!!
    @Test
    @RequestMapping(value = "/getListingforUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(7));
        try {
            when(list_mock.getListingforUser(request)).thenReturn("0");
            assertEquals("0", list_mock.getListingforUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals("0", list_mock.getListingforUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getListingforGroup", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest2() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //group_id
        request.addParameter(String.valueOf(2));
        try {
            when(list_mock.getListingforGroup(request)).thenReturn("1");
            assertEquals("1", list_mock.getListingforGroup(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //group_id
            request2.addParameter(String.valueOf(2));
            assertEquals("0", list_mock.getListingforGroup(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getAllListings", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest3() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("8");
        try {
            when(list_mock.getAllListings(request)).thenReturn("0");
            assertEquals("0", list_mock.getAllListings(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals("0", list_mock.getAllListings(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    @RequestMapping(value = "/createListing", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateListingsTest4() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //address
        request.addParameter("ADDRESS");
        //group-id
        request.addParameter(String.valueOf(3));
        //user id
        request.addParameter(String.valueOf(2));
        //price
        request.addParameter(String.valueOf(350));
        //descrption
        request.addParameter("wiyfgqiv");
        try {
            when(list_mock.createList(request)).thenReturn("0");
            assertEquals("0", list_mock.createList(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //address
            request2.addParameter("dyufgyuewv");
            //group-id
            request2.addParameter(String.valueOf(7));
            //user id
            request2.addParameter(String.valueOf(4));
            //price
            request2.addParameter(String.valueOf(750));
            //descrption
            request2.addParameter("efuyergfuirh3iugf");
            assertEquals("0", list_mock.createList(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}