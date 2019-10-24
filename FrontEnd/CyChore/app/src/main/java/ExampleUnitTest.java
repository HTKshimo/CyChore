import android.content.Context;

import com.example.demo01.R;
import com.example.demo01.ui.ClassUnderTest;


/*How to use Mockito: https://dzone.com/articles/how-to-use-mockito-in-android*/

public class ExampleUnitTest
{
    private static final String TEST_STRING = "HELLO WORLD!";
    //As we don't have access to Context in our JUnit test classes, we need to mock it
   // @Mock
   // Context mMockContext;
   // @Test
    public void readStringFromContext() {
        //Returns the TEST_STRING when getString(R.string.hello_world) is called
        //when(mMockContext.getString(R.string.text_hello_word)).thenReturn(TEST_STRING);
        //Creates an object of the ClassUnderTest with the mock context
     //   ClassUnderTest objectUnderTest = new ClassUnderTest(mMockContext);
        //Stores the return value of getHelloWorldString() in result
        //String result = objectUnderTest.getHelloWorldString();
        //Asserts that result is the value of TEST_STRING
       // assertThat(result, is(TEST_STRING));
    }
}