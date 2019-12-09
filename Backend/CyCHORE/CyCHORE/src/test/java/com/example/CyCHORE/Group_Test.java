package com.example.CyCHORE;

import com.example.CyCHORE.Group.GroupController;
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
            GroupController group_mock;
    @Mock
    HttpServletRequest request;

    //@Autowired
    //BusinessService businessImpl;
    //MADE MODIFICATIONS!!!!!
    @Test
    @RequestMapping(value = "/addTenant", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateaddTenant() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("5");
        try {
            when(group_mock.addTenant(request)).thenReturn("0");
            assertEquals("0", group_mock.addTenant(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, group_mock.addTenant(request2));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    @RequestMapping(value = "/createGroup", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidatecreateGroup() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("7");
        try {
            when(group_mock.createGroup(request)).thenReturn("1");
            assertEquals("1", group_mock.createGroup(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, group_mock.createGroup(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/getAllGroups", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidategetAllGroups() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("8");
        try {
            when(group_mock.getAllGroups(request)).thenReturn("0");
            assertEquals("0", group_mock.getAllGroups(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, group_mock.getAllGroups(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Test
    @RequestMapping(value = "/RemoveTenant", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateRemoveTenant() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.addParameter("8");
        try {
            when(group_mock.RemoveTenantFromGroup(request)).thenReturn("0");
            assertEquals("0", group_mock.RemoveTenantFromGroup(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, group_mock.RemoveTenantFromGroup(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}