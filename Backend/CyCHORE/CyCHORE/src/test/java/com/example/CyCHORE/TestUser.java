package com.example.CyCHORE;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.CyCHORE.User.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

//    @Mock
//    UserController uc;

    @Mock
    UserRepository ur;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserObjectTest() {

        User user = new User("test@test.edu", "Mike", 1);

        assertEquals("test@test.edu", user.getUser());
        assertEquals("Mike", user.getFirst_name());
        assertEquals(java.util.Optional.of(1), java.util.Optional.of(user.getTier()));
    }

    @Test
    public void registerUserTest() throws JSONException {

        when(ur.save(isA(User.class))).thenReturn(new User("test@test.edu", "Sarah", 1));

        UserController uc = new UserController(ur);
        String s = uc.registerUser("test@test.edu", "Sarah", 1);

        assertEquals("{\"status\":\"0\"}", s);
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
