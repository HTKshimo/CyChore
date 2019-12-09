package com.example.CyCHORE;
import com.example.CyCHORE.User.UserController;
import com.example.CyCHORE.User.UserController.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.example.CyCHORE.Listings.ListingsController;
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
public class User_Test {

    @MockBean
    //DataService dataServiceMock;
            UserController user_mock;
    @Mock
    HttpServletRequest request;

    //@Autowired
    //BusinessService businessImpl;
    //MADE MODIFICATIONS!!!!!
    //TESTS CHANGE USERNAME
    @Test
    @RequestMapping(value = "/ChangeUsername", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateChangeUsername() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //change the parameters!!!!!!!!!
        //request.addParameter(String.valueOf(1));
        //uid
        request.addParameter("5");
        //name
        request.addParameter("CLOWNFISH");
        try {
            when(user_mock.ChangeUsername(request)).thenReturn("0");
            assertEquals("0", user_mock.ChangeUsername(request));
//            MockHttpServletRequest request = new MockHttpServletRequest();
//            assertEquals("0", user_mock.ChangeUsername(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //when(loginmock.)
        //assertEquals(24, businessImpl.findTheGreatestFromAllData());
    }

    //TESTS CHANGE PASSWORD
    @Test
    @RequestMapping(value = "/ChangePassword", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateChangePassword() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter("7");
        //password
        request.addParameter("DOOBEEDOO");
        try {
            when(user_mock.ChangePassword(request)).thenReturn("1");
            assertEquals("1", user_mock.ChangePassword(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, user_mock.ChangePassword(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //when(loginmock.)
        //assertEquals(24, businessImpl.findTheGreatestFromAllData());
    }
    //TESTS CHANGE PASSWORD
    @Test
    @RequestMapping(value = "/ChangePassword", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void ValidateChangePassword2() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter("10");
        //password
        request.addParameter("LOLOLOLOL");
        try {
            when(user_mock.ChangePassword(request)).thenReturn("0");
            assertEquals("0", user_mock.ChangePassword(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, user_mock.ChangePassword(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //when(loginmock.)
        //assertEquals(24, businessImpl.findTheGreatestFromAllData());
    }

    @Test
    @RequestMapping(value = "/JoinGroup", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void JoinGroupTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(1));
        //gid
        request.addParameter(String.valueOf(3));
        try {
            when(user_mock.JoinGroup(request)).thenReturn("0");
            assertEquals("0", user_mock.JoinGroup(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //uid
            request.addParameter(String.valueOf(14));
            //gid
            request.addParameter(String.valueOf(2));
            assertEquals("0", user_mock.JoinGroup(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    @RequestMapping(value = "/GroupInfo", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void GroupInfoTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //uid
        request.addParameter(String.valueOf(4));

        try {
            when(user_mock.GroupInfo(request)).thenReturn("0");
            assertEquals("0", user_mock.GroupInfo(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //uid
            request2.addParameter(String.valueOf(10));
            assertEquals("0", user_mock.GroupInfo(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    @RequestMapping(value = "/registerUser", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void RegiesterUserTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //name
        request.addParameter("Bob");
        //email
        request.addParameter("trash@email.com");
        //tier
        request.addParameter(String.valueOf(1));
        try {
            when(user_mock.registerUser(request)).thenReturn("0");
            assertEquals("0", user_mock.registerUser(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //name
            request2.addParameter("Sally");
            //email
            request2.addParameter("blob@email.com");
            //tier
            request2.addParameter(String.valueOf(1));
            assertEquals("0", user_mock.registerUser(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    @RequestMapping(value = "/login", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void LoginTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        //email
        request.addParameter("seablue@email.com");
        //password
        request.addParameter("dwvuvwriuv");
        try {
            when(user_mock.validateLogin(request)).thenReturn("0");
            assertEquals("0", user_mock.validateLogin(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            //email
            request2.addParameter("wdfuy@email.com");
            //password
            request2.addParameter("wiufhwriuhg");
            assertEquals(null, user_mock.validateLogin(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
