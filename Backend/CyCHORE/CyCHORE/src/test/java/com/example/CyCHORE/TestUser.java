package com.example.CyCHORE;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.CyCHORE.User.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

//NOTE: all tests fail with WebSocketConfig.java in project. After removing it all tests pass. Without removing it the project running fun with all functionality unaffected.

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

    @Mock
    UserRepository ur;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserObjectTest() {

        User user = new User("test@test.edu", "first_name", "username", "password", 1, 1);

        assertEquals("test@test.edu", user.getEmail());
        assertEquals("first_name", user.getFirst_name());
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(user.getTier()));
    }

//    not able to test due to HTTP request content cannot be mocked. java.lang.NullPointerException at line 48 of this file.
//    @Test
//    public void registerUserTest() throws JSONException, IOException {
//
//        when(isA(MockHttpServletRequest.class).getReader().lines().collect(Collectors.joining())).thenReturn("first_name: Shuang");
//        UserController uc = new UserController(ur);
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addParameter("Blob", "xyz");
//        String s = uc.registerUser(request);
//
//        assertEquals("{\"status\":\"0\"}", s);
//    }

    @Test
    public void testFindTheGreatestFromAllData() throws IOException {
        //Login loginmock = mock(Login.class);

        //when(loginmock.validateLogin()).thenReturn(new int[] {});
//        when(loginmock.getHome()).thenReturn(String.valueOf(new int[]{
//
//        }));
        UserController uc = new UserController(ur);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("Blob", "xyz");
        try {
            when(uc.validateLogin(request)).thenReturn("1");
            assertEquals("1", uc.validateLogin(request));
            MockHttpServletRequest request2 = new MockHttpServletRequest();
            assertEquals(null, uc.validateLogin(request2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //when(loginmock.)
        //assertEquals(24, businessImpl.findTheGreatestFromAllData());
    }

}



//public class TestAccountService {
//
//    @InjectMocks
//    AccountService acctService;
//
//    @Mock
//    AccountRepository repo;
//
//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void getAccountByIdTest() {
//        when(repo.getAccountByID(1)).thenReturn(new Account(1, "jDoe", "123456", "jDoe@gmail.com"));
//
//        Account acct = acctService.getAccountByID(1);
//
//        assertEquals("jDoe", acct.getUserID());
//        assertEquals("123456", acct.getPassword());
//        assertEquals("jDoe@gmail.com", acct.getEmail());
//    }
//
//    @Test
//    public void getAllAccountTest() {
//        List<Account> list = new ArrayList<Account>();
//        Account acctOne = new Account(1, "John", "1234", "john@gmail.com");
//        Account acctTwo = new Account(2, "Alex", "abcd", "alex@yahoo.com");
//        Account acctThree = new Account(3, "Steve", "efgh", "steve@gmail.com");
//
//        list.add(acctOne);
//        list.add(acctTwo);
//        list.add(acctThree);
//
//        when(repo.getAccountList()).thenReturn(list);
//
//        List<Account> acctList = acctService.getAccountList();
//
//        assertEquals(3, acctList.size());
//        verify(repo, times(1)).getAccountList();
//    }
//
//}
