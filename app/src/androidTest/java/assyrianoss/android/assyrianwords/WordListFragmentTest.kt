/**
 * Copyright 2019 Assyrian Remote Open Source Society
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package assyrianoss.android.assyrianwords

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import assyrianoss.android.assyrianwords.view.MainActivity
import assyrianoss.android.assyrianwords.view.category.WordCategoryAdapter
import assyrianoss.android.assyrianwords.view.list.WordListAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Note: Some tests might fail due to slow emulator.
 */
@RunWith(AndroidJUnit4::class)
class WordListFragmentTest {

    companion object {
        const val CATEGORY_LIST_CLICK_SLEEP_TIME: Long = 1000
        const val WORD_LIST_CLICK_SLEEP_TIME: Long = 1000
    }

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        /**
         * Flaky factor: might fail due to slow emulator.
         */
        Espresso.onView(withId(R.id.categoryRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<WordCategoryAdapter.ViewHolder>(0, ViewActions.click())
            )
        Thread.sleep(CATEGORY_LIST_CLICK_SLEEP_TIME)
    }

    @Test
    fun parentView_isDisplayed() {
        Espresso.onView(withId(R.id.wordListConstraintLayout))
            .check(matches((isDisplayed())))
    }

    @Test
    fun recyclerView_isDisplayed() {
        Espresso.onView(withId(R.id.wordsRecyclerView))
            .check(matches((isDisplayed())))
    }

    @Test
    fun openWordDetailFragment_clickOnWordListItem_shouldShowWordDetailFragment() {
        /**
         * Flaky factor: might fail due to slow emulator.
         */
        Espresso.onView(withId(R.id.wordsRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<WordListAdapter.ViewHolder>(0, ViewActions.click())
            )
        Thread.sleep(WORD_LIST_CLICK_SLEEP_TIME)
        Espresso.onView(withId(R.id.wordDetailConstraintLayout))
            .check(matches((isDisplayed())))
    }
}