package com.example.mohamed.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PassingCorrectRecipeTest {

    @Rule
    public ActivityTestRule <MainActivity>testRule=new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResorce;

    @Before
    public void regesterIdlingResorce()
    {
        mIdlingResorce=testRule.getActivity().getIdlingResorce();
        IdlingRegistry.getInstance().register(mIdlingResorce);
    }

    @Test
    public void checkrecipename()
    {
        onView(withId(R.id.rv_baking_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        String recipeName = "Nutella Pie";
        onView(allOf(instanceOf(TextView.class),withParent(withId(R.id.toolbar)))).check(matches(withText(recipeName)));
    }

    @After
    public void unRegesterIdlingResorce()
    {
        if (mIdlingResorce!=null)
            IdlingRegistry.getInstance().unregister(mIdlingResorce);

    }
}
