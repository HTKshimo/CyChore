package com.example.CyCHORE.WebSocket;

//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
///**
// *
// * @author Vamsi Krishna Calpakkam
// *
// */
//@ServerEndpoint("/websocket/{username}")
//@Component
//public class WebSocketServer {
//
//    // Store all socket session and their corresponding username.
//    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
//    private static Map<String, Session> usernameSessionMap = new HashMap<>();
//
//    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
//
//    @OnOpen
//    public void onOpen(
//            Session session,
//            @PathParam("username") String username) throws IOException
//    {
//        logger.info("Entered into Open");
//
//        sessionUsernameMap.put(session, username);
//        usernameSessionMap.put(username, session);
//
//        String message="User:" + username + " has Joined the Chat";
//        broadcast(message);
//
//    }
//
//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException
//    {
//        // Handle new messages
//        logger.info("Entered into Message: Got Message:"+message);
//        String username = sessionUsernameMap.get(session);
//
//        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
//        {
//            String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
//            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
//            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
//        }
//        else // Message to whole chat
//        {
//            broadcast(username + ": " + message);
//        }
//    }
//
//    @OnClose
//    public void onClose(Session session) throws IOException
//    {
//        logger.info("Entered into Close");
//
//        String username = sessionUsernameMap.get(session);
//        sessionUsernameMap.remove(session);
//        usernameSessionMap.remove(username);
//
//        String message= username + " disconnected";
//        broadcast(message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable)
//    {
//        // Do error handling here
//        logger.info("Entered into Error");
//    }
//
//    private void sendMessageToPArticularUser(String username, String message)
//    {
//        try {
//            usernameSessionMap.get(username).getBasicRemote().sendText(message);
//        } catch (IOException e) {
//            logger.info("Exception: " + e.getMessage().toString());
//            e.printStackTrace();
//        }
//    }
//
//    private static void broadcast(String message)
//            throws IOException
//    {
//        sessionUsernameMap.forEach((session, username) -> {
//            synchronized (session) {
//                try {
//                    session.getBasicRemote().sendText(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.example.CyCHORE.Chatroom.Chatroom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.example.CyCHORE.Chatroom.UserChatroomController.*;
import static com.example.CyCHORE.User.UserController.*;

/**
 *
 * @author Vamsi Krishna Calpakkam
 *
 */
@ServerEndpoint("/websocket/{room_id}/{user_id}")
@Component
public class WebSocketServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, Integer> sessionUsernameMap = new HashMap<>();
    private static Map<Integer, ArrayList<Session>> usernameSessionMap = new HashMap<>();
    private static Map<Integer, ArrayList<Session>> roomSessionMap = new HashMap<>();
    private static Map<Session, Integer> sessionRoomMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("room_id") int room_id, @PathParam("user_id") int user_id) throws IOException
    {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, user_id);
        if (usernameSessionMap.containsKey(user_id)) {
            usernameSessionMap.get(user_id).add(session);
        }else{
            usernameSessionMap.put(user_id, new ArrayList<>());
        }
        if (roomSessionMap.containsKey(room_id)) {
            roomSessionMap.get(room_id).add(session);
        }else{
            roomSessionMap.put(room_id, new ArrayList<>());
        }
        sessionRoomMap.put(session, room_id);
        String username = getUsername(user_id);
        String roomname = getChatroomName(room_id);
        String message="User: " + username + " has Joined room " + roomname;
        broadcastToRoom(room_id, message);

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        int user_id = sessionUsernameMap.get(session);
        String username = getUsername(user_id);
        int room_id = sessionRoomMap.get(session);

//        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
//        {
//            String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
//            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
//            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
//        }
//        else // Message to whole chat
//        {
        broadcastToRoom(room_id, username + ": " + message);
//        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");

        int user_id = sessionUsernameMap.get(session);
        String username = getUsername(user_id);
        int room_id = sessionRoomMap.get(session);
        sessionUsernameMap.remove(session);
        sessionRoomMap.remove(session);
        usernameSessionMap.get(user_id).remove(session);
        roomSessionMap.get(room_id).remove(session);

        String message = username + " disconnected";
        broadcastToRoom(room_id, message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

//    private void sendMessageToPArticularUser(String username, String message)
//    {
//        try {
//            usernameSessionMap.get(username).getBasicRemote().sendText(message);
//        } catch (IOException e) {
//            logger.info("Exception: " + e.getMessage().toString());
//            e.printStackTrace();
//        }
//    }

    private static void broadcast(String message)
            throws IOException
    {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void broadcastToRoom(Integer room, String message){
        sessionRoomMap.forEach((session, r) -> {
//            System.out.println("room, session: " + room + ", " + session);
            synchronized (session) {
                try {
                    if(r == room){
//                        System.out.println("room number match, session:" +session);
                        session.getBasicRemote().sendText(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}