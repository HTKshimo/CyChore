package com.example.CyCHORE;


import com.example.CyCHORE.User.UserController.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.example.CyCHORE.User.UserController;
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
public class Login_Test{
    @MockBean
    //DataService dataServiceMock;
            UserController loginmock;
    @Mock
    HttpServletRequest request;

    //@Autowired
    //BusinessService businessImpl;
    @Test
    @RequestMapping(value = "/login", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void testFindTheGreatestFromAllData() throws IOException {
        //Login loginmock = mock(Login.class);

        //when(loginmock.validateLogin()).thenReturn(new int[] {});
//        when(loginmock.getHome()).thenReturn(String.valueOf(new int[]{
//
//        }));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("Blob", "xyz");
        try {
            when(loginmock.validateLogin(request)).thenReturn("1");
            assertEquals("1", loginmock.validateLogin(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, loginmock.validateLogin(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //when(loginmock.)
        //assertEquals(24, businessImpl.findTheGreatestFromAllData());
    }
    @Test
    public void testFindTheGreatestFromAllData_ForOneValue() {
//        when(loginmock.validateLogin()).thenReturn(new int[] {
//        });
        //assertEquals(15, businessImpl.findTheGreatestFromAllData());
    }
    @Test
    public void testFindTheGreatestFromAllData_NoValues() {
        //when(loginmock.validateLogin()).thenReturn(new int[] {});
        //assertEquals(Integer.MIN_VALUE, businessImpl.findTheGreatestFromAllData());
    }
}