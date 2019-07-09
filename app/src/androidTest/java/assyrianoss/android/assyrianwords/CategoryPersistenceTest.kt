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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import assyrianoss.android.assyrianwords.model.persistence.entities.Category
import assyrianoss.android.assyrianwords.view.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryPersistenceTest {

    companion object {
        const val EXPECTED_CATEGORY_ID = 999
        const val EXPECTED_CATEGORY_NAME = "Greetings"
    }

    lateinit var categoryLiveData: LiveData<Category>

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun Category_Fields_ContainCorrectValues() {
        // store a test category
        activityTestRule.activity.viewModel.insertCategory(
            Category(EXPECTED_CATEGORY_ID, EXPECTED_CATEGORY_NAME)
        )
        // retrieve the category
        categoryLiveData = activityTestRule.activity.viewModel.getCategory(EXPECTED_CATEGORY_ID)
        // observe test word for results, then test the values
        categoryLiveData.observe(activityTestRule.activity, Observer {
            Assert.assertNotNull(it)
            Assert.assertEquals(EXPECTED_CATEGORY_ID, it.id)
            Assert.assertEquals(EXPECTED_CATEGORY_NAME, it.name)
        })
    }
}