package com.example.CyChore;

import com.example.CyChore.data.ChatCollection;
import com.example.CyChore.data.IssueCollection;
import com.example.CyChore.data.ProfileCollection;
import com.example.CyChore.data.SubleaseCollection;
import com.example.CyChore.data.TaskCollection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MockitoTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        assertEquals(1, 1);
    }

  /*  @Test
    public void testTasks() {
        TaskCollection mockedTasks = mock(TaskCollection.class);
        mockedTasks.addItem(new TaskCollection.TaskItem(10002,"kill bug",1569623441258L,1));
        verify(mockedTasks).addItem(new TaskCollection.TaskItem(10002,"kill bug",1569623441258L,1));
        assertEquals("Overdue!", mockedTasks.ITEMS.get(0).dueTime);
    }*/

    //Testing Issue Collection
   /* @Test
    public void testIssue()
    {
        IssueCollection mockedIssues = mock(IssueCollection.class);
        mockedIssues.addItem(new IssueCollection.IssueItem(10002,"Mohan","Roommate sucks",1));
        verify(mockedIssues).addItem(new IssueCollection.IssueItem(10002,"Smitha","Roommate did not clean!",1));
        assertEquals("Roommate sucks", mockedIssues.ITEMS.get(0).complain);
        assertEquals("Roommate did not clean!", mockedIssues.ITEMS.get(1).complain);

    }*/

    //Testing Issue Collection
  /*  @Test
    public void testIssue2()
    {
        IssueCollection mockedIssues = mock(IssueCollection.class);
        mockedIssues.addItem(new IssueCollection.IssueItem(10002,"Mohan","Roommate sucks",1));
        verify(mockedIssues).addItem(new IssueCollection.IssueItem(10002,"Smitha","Roommate did not clean!",3));
        assertEquals(1, mockedIssues.ITEMS.get(0).fid);
        assertEquals("Roommate sucks", mockedIssues.ITEMS.get(0).complain);

    }*/

    //Testing Sublease Collection
    @Test
    public void testSublease()
    {
        SubleaseCollection mockedSublease = mock(SubleaseCollection.class);
        mockedSublease.addItem(new SubleaseCollection.subleaseItem("Mohan", "North Hyland", "Very clean and close to campus apartment", 1));
        verify(mockedSublease).addItem(new SubleaseCollection.subleaseItem("Smitha", "Freddy", "Looking for female roommates", 2));
        assertEquals("sublease", mockedSublease.ITEMS.get(0).title);
    }

    //Testing Sublease Collection
   @Test
    public void testSublease2()
    {
        SubleaseCollection mockedSublease = mock(SubleaseCollection.class);
        mockedSublease.addItem(new SubleaseCollection.subleaseItem("Mohan", "North Hyland", "Very clean and close to campus apartment", 1));
        verify(mockedSublease).addItem(new SubleaseCollection.subleaseItem("Smitha", "Freddy", "Looking for female roommates", 2));
        assertEquals("Smitha", mockedSublease.ITEMS.get(1).detail);

    }

    //Testing Chat Collection
  /*  @Test
    public void testChat()
    {
        List<String> chatContent = new ArrayList<String>();
        chatContent.add("Hello");
        ChatCollection mockedChat = mock(ChatCollection.class);
        mockedChat.addItem(new ChatCollection.ChatSelection("Shuang", chatContent));
        verify(mockedChat).addItem(new ChatCollection.ChatSelection("Mohan", chatContent));
        assertEquals("Shuang", mockedChat.ITEMS.get(0).ChatTitle);
    }*/

    //Testing Chat Collection
   /* @Test
    public void testChat2()
    {
        List<String> chatContent = new ArrayList<String>();
        chatContent.add("Hello");
        ChatCollection mockedChat = mock(ChatCollection.class);
        mockedChat.addItem(new ChatCollection.ChatSelection("Shuang", chatContent));
        verify(mockedChat).addItem(new ChatCollection.ChatSelection("Mohan", chatContent));
        assertEquals("Mohan", mockedChat.ITEMS.get(1).ChatTitle);
    }*/

    //Testing Chat Collection
   /* @Test
    public void testChat3()
    {
        List<String> chatContent = new ArrayList<String>();
        chatContent.add("Bye");
        List<String> getChatContent = new ArrayList<String>();
        getChatContent.add("Bye");
        ChatCollection mockedChat = mock(ChatCollection.class);
        mockedChat.addItem(new ChatCollection.ChatSelection("Shuang", chatContent));
        verify(mockedChat).addItem(new ChatCollection.ChatSelection("Mohan", chatContent));
        assertEquals(getChatContent, mockedChat.ITEMS.get(1).ChatContent);
    }*/

    //Testing Profile Collection
   /* @Test
    public void testProfile()
    {
        ProfileCollection mockedProfile = mock(ProfileCollection.class);
        mockedProfile.ITEM_MAP(new ProfileCollection.ProfileSelection("").title);
                //ITEMS(new ArrayList<ProfileCollection.ProfileSelection>()));
                //.ProfileSelection(new ProfileCollection.ProfileSelection("My Account"));
        //verify(mockedProfile).;
        //assertEquals("My Account", mockedProfile.);
                //ITEMS.get(0).complain);
    }*/

    //Testing Chat Collection
  /*  @Test
    public void testChat()
    {
        List<String> givenChatContent=null;
        ChatCollection mockedChats = mock(ChatCollection.class);
        //mockedChats.ChatSelection("chat", new ArrayList<>(givenChatContent));
       // List<String> givenChatContent = null;
        //mockedChats.addItem(new ChatCollection.ChatSelection(1, givenChatContent,mockedChats.ChatSelection("chat", new ArrayList<>(givenChatContent))));
        verify(mockedChats).addItem(new ChatCollection.ChatSelection(10002,"Smitha",));
        assertEquals("10002", mockedChats.ITEMS.get(0).ChatTitle);
    }*/


}
