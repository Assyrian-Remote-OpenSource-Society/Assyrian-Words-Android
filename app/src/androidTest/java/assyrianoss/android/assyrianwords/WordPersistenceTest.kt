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
import assyrianoss.android.assyrianwords.model.persistence.entities.Word
import assyrianoss.android.assyrianwords.view.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordPersistenceTest {

    companion object {
        const val INPUT_WORD_ID = 0   // not expected (ID autogeneration)
        const val EXPECTED_ENGLISH_WORD = "hello"
        const val EXPECTED_EASTERN_PHONETIC = "shlama"
        const val EXPECTED_EASTERN_ASSYRIAC = "ܫܲܝܢܵܐ"
        const val EXPECTED_WESTERN_PHONETIC = "shlomo"
        const val EXPECTED_WESTERN_ASSYRIAC = "ܫܲܝܢܵܐ"
        const val EXPECTED_AKKADIAN = "pushbashlamu"
        const val EXPECTED_ARAMAIC = "shlm"
        const val EXPECTED_DEFINITION = "A word used for greeting. Short for \"peace be upon you\""
        const val EXPECTED_TYPE = "Interjection"
        const val EXPECTED_WORD_CATEGORY = "greetings"
        const val EXPECTED_EXAMPLE = "N/A"
    }

    lateinit var wordLiveData: LiveData<Word>

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun Word_Fields_ContainCorrectValues() {
        // store a test word
        activityTestRule.activity.viewModel.insertWord(
            Word(
                INPUT_WORD_ID, EXPECTED_ENGLISH_WORD, EXPECTED_EASTERN_PHONETIC,
                EXPECTED_EASTERN_ASSYRIAC, EXPECTED_WESTERN_PHONETIC, EXPECTED_WESTERN_ASSYRIAC,
                EXPECTED_AKKADIAN, EXPECTED_ARAMAIC, EXPECTED_DEFINITION,
                EXPECTED_TYPE, EXPECTED_WORD_CATEGORY, EXPECTED_EXAMPLE
            )
        )
        // retrieve the test word
        wordLiveData = activityTestRule.activity.viewModel.getWord(EXPECTED_EASTERN_ASSYRIAC)
        // observe test word for results, then test the values
        wordLiveData.observe(activityTestRule.activity, Observer {
            Assert.assertNotNull(it)
            Assert.assertEquals(EXPECTED_ENGLISH_WORD, it.english)
            Assert.assertEquals(EXPECTED_EASTERN_PHONETIC, it.easternPhonetic)
            Assert.assertEquals(EXPECTED_EASTERN_ASSYRIAC, it.easternAssyriac)
            Assert.assertEquals(EXPECTED_WESTERN_PHONETIC, it.westernPhonetic)
            Assert.assertEquals(EXPECTED_WESTERN_ASSYRIAC, it.westernAssyriac)
            Assert.assertEquals(EXPECTED_AKKADIAN, it.akkadian)
            Assert.assertEquals(EXPECTED_ARAMAIC, it.aramaic)
            Assert.assertEquals(EXPECTED_DEFINITION, it.definition)
            Assert.assertEquals(EXPECTED_TYPE, it.type)
            Assert.assertEquals(EXPECTED_WORD_CATEGORY, it.category)
            Assert.assertEquals(EXPECTED_EXAMPLE, it.example)
        })
    }
}