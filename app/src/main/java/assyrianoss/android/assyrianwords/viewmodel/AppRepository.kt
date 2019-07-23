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

import android.app.Application
import androidx.lifecycle.LiveData
import assyrianoss.android.assyrianwords.model.network.GithubFetcher
import assyrianoss.android.assyrianwords.model.persistence.AppDatabase
import assyrianoss.android.assyrianwords.model.persistence.daos.CategoryDao
import assyrianoss.android.assyrianwords.model.persistence.daos.WordDao
import assyrianoss.android.assyrianwords.model.persistence.entities.Category
import assyrianoss.android.assyrianwords.model.persistence.entities.Word
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AppRepository(
    application: Application, override val coroutineContext: CoroutineContext
) : Repository,
    CoroutineScope {

    companion object {
        private const val BASE_URL: String = "https://raw.githubusercontent.com/"
        private const val GROUP: String = "Assyrian-Remote-OpenSource-Society"
        private const val REPO: String = "Assyrian-Wordlists"
        private const val BRANCH: String = "master"

        private val filePaths: List<String> = mutableListOf(
            "json/conjunctions.json"
        )
    }

    private var db: AppDatabase = AppDatabase.getInstance(application)
    private var categoryDao: CategoryDao = db.categoryDao()
    private var wordDao: WordDao = db.wordDao()
    private var githubFetcher: GithubFetcher = GithubFetcher(
        BASE_URL, GROUP, REPO,
        BRANCH, filePaths, application.applicationContext,
        coroutineContext
    )
    private lateinit var categories: LiveData<List<Category>>
    private lateinit var allWords: LiveData<List<Word>>
    private lateinit var queriedWords: LiveData<List<Word>>
    private lateinit var queriedWord: LiveData<Word>

    /**
     * Fetch data from all the known paths over the network.
     */
    override fun fetchAllKnownDataSets() {
        githubFetcher.fetchAllKnown(wordDao, categoryDao)
    }

    /**
     * Insert a category name in the database.
     */
    override fun insertCategory(category: Category) {
        launch(Dispatchers.Default) {
            categoryDao.insert(category)
        }
    }

    /**
     * Update a category name in the database.
     */
    override fun updateCategory(category: Category) {
        launch(Dispatchers.Default) {
            categoryDao.update(category)
        }
    }

    /**
     * Delete a category name from the database.
     */
    override fun deleteCategory(category: Category) {
        launch(Dispatchers.Default) {
            categoryDao.delete(category)
        }
    }

    /**
     * Delete all category names from the database.
     */
    override fun deleteAllCategories() {
        launch(Dispatchers.Default) {
            categoryDao.deleteAll()
        }
    }

    /**
     * Read all the category names from the database.
     */
    override fun readAllCategories(): LiveData<List<Category>> = runBlocking {
        val task = GlobalScope.launch {
            categories = categoryDao.readAllFromTable()
        }
        task.join()
        return@runBlocking categories
    }

    /**
     * Read the word of a specific word ID from the database.
     */
    override fun readCategory(id: Int): LiveData<Category> = runBlocking {
        lateinit var category: LiveData<Category>
        val task = GlobalScope.launch {
            category = categoryDao.readCategoryById(id)
        }
        task.join()
        return@runBlocking category
    }

    /**
     * Insert a word into the database.
     */
    override fun insertWord(word: Word) {
        launch(Dispatchers.Default) {
            wordDao.insert(word)
        }
    }

    /**
     * Insert a list of words into the database.
     */
    override fun insertWords(words: List<Word>) {
        launch(Dispatchers.Default) {
            wordDao.insertList(words)
        }
    }

    /**
     * Update a specific word in the database.
     */
    override fun updateWord(word: Word) {
        launch(Dispatchers.Default) {
            wordDao.update(word)
        }
    }

    /**
     * Delete a specific word in the database.
     */
    override fun deleteWord(word: Word) {
        launch(Dispatchers.Default) {
            wordDao.delete(word)
        }
    }

    /**
     * Delete all words in the database.
     */
    override fun deleteAllWords() {
        launch(Dispatchers.Default) {
            wordDao.deleteAll()
        }
    }

    /**
     * Read the word from the database by a row ID.
     */
    override fun readWord(wordId: Int): LiveData<Word> = runBlocking {
        val task = GlobalScope.launch {
            queriedWord = wordDao.readById(wordId)
        }
        task.join()
        return@runBlocking queriedWord
    }

    /**
     * Read the word from the database by the Eastern Assyriac spelling.
     */
    override fun readWord(easternAssyriac: String): LiveData<Word> = runBlocking {
        val task = GlobalScope.launch {
            queriedWord = wordDao.read(easternAssyriac)
        }
        task.join()
        return@runBlocking queriedWord
    }

    /**
     * Read the words of a specific category from the database.
     */
    override fun readWords(category: String): LiveData<List<Word>> = runBlocking {
        val task = GlobalScope.launch {
            queriedWords = wordDao.readAll(category)
        }
        task.join()
        return@runBlocking queriedWords
    }

    /**
     * Return the LiveData<List<Word>> object that holds the results
     * for word category queries.
     */
    override fun getListForQueriedWords(): LiveData<List<Word>> {
        return queriedWords
    }

    /**
     * Read all words from table in database.
     */
    override fun readEntireWordsTable() = runBlocking {
        val task = GlobalScope.launch {
            allWords = wordDao.readAll()
        }
        task.join()
        return@runBlocking allWords
    }
}