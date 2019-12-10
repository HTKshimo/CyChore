package com.example.CyCHORE;

import com.example.CyCHORE.Chatroom.Chatroom;
import com.example.CyCHORE.Chatroom.ChatroomRepository;
import com.example.CyCHORE.Chatroom.UserChatroom;
import com.example.CyCHORE.Chatroom.UserChatroomRepository;
import com.example.CyCHORE.Complaint.Complaint;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.example.CyCHORE.Chatroom.UserChatroomController.*;
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
import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserChatroom_Test {

    @Mock
    HttpServletRequest request;

    @Mock
    UserChatroomRepository ucr_mock;

    @Mock
    ChatroomRepository chr;

    @Test
    public void createChatroomWithUsersTest() throws IOException {

        request = new MockHttpServletRequest();
        ((MockHttpServletRequest) request).addParameter("user_ids", "[1]");
        try {
            Chatroom r = mock(Chatroom.class);
            when(chr.save(r)).thenReturn(null);
            when(r.getId()).thenReturn(1);
            JSONObject expected = new JSONObject();
            expected.put("status", "0");
            expected.put("cr_id", "1");
            assertEquals(expected.toString(), createChatroomWithUsers(request));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getChatroomUsersTest(){
        when(ucr_mock.findAll()).thenReturn(null);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, getChatroomUsers(1));

    }

    @Test
    public void getUserChatroomsTest(){
        when(ucr_mock.findAll()).thenReturn(null);
        HashSet<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(5);
        assertEquals(expected, getUserChatrooms(1));

    }

}
