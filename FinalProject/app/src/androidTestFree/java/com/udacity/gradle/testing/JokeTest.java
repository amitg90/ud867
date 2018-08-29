package com.udacity.gradle.testing;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;
import com.udacity.gradle.builditbigger.EndPointAsyncTask;
import com.udacity.gradle.builditbigger.OnReceiveClickListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class JokeTest extends ApplicationTestCase<Application> implements OnReceiveClickListener {

    CountDownLatch signal;
    String joke = null;

    public JokeTest() {
        super(Application.class);
    }

//    @Rule
//    public ActivityTestRule<MainActivity> rule  = new ActivityTestRule<>(MainActivity.class);
//
//    Instrumentation.ActivityMonitor monitor = getInstrumentation()
//            .addMonitor(JokeDisplay.class.getName(), null, false);

//    @Test
//    public void testNonEmptyJoke() {
//        // Trigger Async Task
//        onView(withId(R.id.btn))
//                .perform(click());
//
//        // check if async task triggered another activity and the new activity is displaying joke correctly.
//
//        // Make additional check to make sure activity is alive.
//        Activity secondActivity = getInstrumentation()
//                .waitForMonitorWithTimeout(monitor, 5000);
//
//        assertNotNull(secondActivity);
//
//        TextView textView = secondActivity.findViewById(R.id.textView);
//        onView(withId(textView.getId())).check(matches(withText(startsWith("Showing Joke at index"))));
//    }

    @Test
    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            EndPointAsyncTask asyncTask = (EndPointAsyncTask) new EndPointAsyncTask();
            asyncTask.execute(new Pair<OnReceiveClickListener, String>(this, "sayHi"));
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("Joke is null", joke);
            assertFalse("Joke is empty", joke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }

    @Override
    public void onAsyncTaskDone(String str) {
        this.joke = str;
        signal.countDown();
    }
}
