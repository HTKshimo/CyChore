import android.content.Context;

import com.example.demo01.R;
import com.example.demo01.ui.ClassUnderTest;

/*import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;



How to use Mockito: https://dzone.com/articles/how-to-use-mockito-in-android
@RunWith(AndroidJUnit4.class)
@LargeTest*/
public class ExampleUnitTest
{
    private static final String TEST_STRING = "HELLO WORLD!";
    //As we don't have access to Context in our JUnit test classes, we need to mock it

    Context mMockContext;

   // public void readStringFromContext() {
        //Returns the TEST_STRING when getString(R.string.hello_world) is called
        //when(mMockContext.getString(R.string.text_hello_word)).thenReturn(TEST_STRING);
        //Creates an object of the ClassUnderTest with the mock context
        ClassUnderTest objectUnderTest = new ClassUnderTest(mMockContext);
        //Stores the return value of getHelloWorldString() in result
        //String result = objectUnderTest.getHelloWorldString();
        //Asserts that result is the value of TEST_STRING
       // assertThat(result, is(TEST_STRING));

}