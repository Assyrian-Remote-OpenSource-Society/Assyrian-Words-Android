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

package assyrianoss.android.assyrianwords.model.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import assyrianoss.android.assyrianwords.model.persistence.daos.CategoryDao
import assyrianoss.android.assyrianwords.model.persistence.daos.WordDao
import assyrianoss.android.assyrianwords.model.persistence.entities.Category
import assyrianoss.android.assyrianwords.model.persistence.entities.Word
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(
    entities = [Category::class, Word::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            buildInstance(context)
            return instance!!
        }

        // setup functions

        private fun buildInstance(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "word_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build()
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDb(instance) // TODO: invoke this
            }
        }

        /**
         * Populate DB with basic wordlists using dataset(s) that go with
         * the app installation.
         */
        private fun populateDb(db: AppDatabase?) {
            preloadCategories(db)
            preloadWords(db)
        }

        private fun preloadCategories(db: AppDatabase?) = runBlocking {
            val categoryDao = db?.categoryDao()
            val categories = mutableListOf<Category>()
            // TODO: get categories from app internals...
            GlobalScope.launch {
                categoryDao?.insertList(categories)
            }
        }

        private fun preloadWords(db: AppDatabase?) = runBlocking {
            val wordDao = db?.wordDao()
            val words = mutableListOf<Word>()
            // TODO: get words from app internals...
            GlobalScope.launch {
                wordDao?.insertList(words)
            }
        }

        // helper functions

        /**
         * Store words in the database using the Word DAO. Take the category from the
         * first word in the list and see if it needs to be added to the category list
         * in the database (all words in the list should be of the same category).
         */
        fun store(wordList: List<Word>, wordDao: WordDao, categoryDao: CategoryDao) {
            if (wordList.isNotEmpty()) {
                wordDao.insertList(wordList)
                insertCategory(wordList[0], categoryDao)
            }
        }

        /**
         * Store category in the database using the category DAO. If the word already
         * exists, then it won't be added, because the category names are set to be
         * unique in the table.
         */
        fun insertCategory(word: Word, categoryDao: CategoryDao) {
            val category = Category(0, word.category.capitalize())
            categoryDao.insert(category)
        }
    }

    abstract fun categoryDao(): CategoryDao
    abstract fun wordDao(): WordDao
}