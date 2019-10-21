package com.example.CyCHORE;

import com.example.CyCHORE.*;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
//import org.springframework.test.context.junit4.SpringRunner;

//public class Login_Test {
//}



@SpringBootTest
public class Login_Test{
    @MockBean
    //DataService dataServiceMock;
    Login loginmock;
    @Autowired
    //BusinessService businessImpl;
    @Test
    @RequestMapping(value = "/login", method = POST, produces ="application/json;charset=UTF-8")
    @ResponseBody
    public void testFindTheGreatestFromAllData() throws IOException {
        Login loginmock = mock(Login.class);
        //when(loginmock.validateLogin()).thenReturn(new int[] {});
        when(loginmock.getHome()).thenReturn(String.valueOf(new int[]{
            0,2,3
        }));
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