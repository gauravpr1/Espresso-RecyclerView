package com.ahmedmolawale.dogceo;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.ahmedmolawale.dogceo.features.dogs.presentation.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.ahmedmolawale.dogceo.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScrollTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void scroll_to_bottom() throws InterruptedException {
        Thread.sleep(5000);

       int count = getRecyclerViewCount(withId(R.id.dog_breed_recycler_view)); // No of items in Recycler View
        Log.d("Booking Recycler Cnt", String.valueOf(count));
        for(int i=0;i<count;i++){
            Thread.sleep(2000);
            onView(withIndex(withId(R.id.dog_breed_recycler_view), 0)).perform(RecyclerViewActions.scrollToPosition(i));
            //Thread.sleep(2000);

        }

    }
    @Test
    public void scroll_to_specific_position() throws InterruptedException {
        Thread.sleep(5000);
        // scrolling to position 20
        onView(withIndex(withId(R.id.dog_breed_recycler_view), 0)).perform(RecyclerViewActions.scrollToPosition(20));
        Thread.sleep(5000);
    }

    @Test
    public void scroll_nclickItem() throws InterruptedException {
        // click and scroll all items of the recycler view
        for(int i=0;i<96;i++){
            Thread.sleep(2000);
            onView(withId(R.id.dog_breed_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withIndex(withId(R.id.dog_breed_recycler_view), 0)).perform(RecyclerViewActions.scrollToPosition(i));
            Thread.sleep(2000);
            Espresso.pressBack();
        }
    }
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
    public static int getRecyclerViewCount(Matcher matcher) {
        final int[] num = new int[1];
        onView(Matchers.allOf(matcher, isEnabled())).check(matches(new TypeSafeMatcher<View>() {
            RecyclerView recyclerView = null;

            @Override
            public boolean matchesSafely(View view) {
                recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                Log.d("Booking Recycler Cnt", adapter.getItemCount() + "");
                num[0] = adapter.getItemCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        return num[0];
    }

}
