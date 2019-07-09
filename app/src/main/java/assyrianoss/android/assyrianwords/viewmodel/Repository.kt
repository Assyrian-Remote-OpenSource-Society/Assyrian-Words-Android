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

package assyrianoss.android.assyrianwords.viewmodel

import androidx.lifecycle.LiveData
import assyrianoss.android.assyrianwords.model.persistence.entities.Category
import assyrianoss.android.assyrianwords.model.persistence.entities.Word

interface Repository {
    fun fetchAllKnownDataSets()
    fun insertCategory(category: Category)
    fun updateCategory(category: Category)
    fun deleteCategory(category: Category)
    fun deleteAllCategories()
    fun readAllCategories(): LiveData<List<Category>>
    fun readCategory(id: Int): LiveData<Category>
    fun insertWord(word: Word)
    fun insertWords(words: List<Word>)
    fun updateWord(word: Word)
    fun deleteWord(word: Word)
    fun deleteAllWords()
    fun getListForQueriedWords(): LiveData<List<Word>>
    fun readWord(wordId: Int): LiveData<Word>
    fun readWord(easternAssyriac: String): LiveData<Word>
    fun readWords(category: String): LiveData<List<Word>>
    fun readEntireWordsTable(): LiveData<List<Word>>
}