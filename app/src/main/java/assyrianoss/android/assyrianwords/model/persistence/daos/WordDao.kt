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

package assyrianoss.android.assyrianwords.model.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import assyrianoss.android.assyrianwords.model.persistence.entities.Word

@Dao
interface WordDao {

    // INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIfNotExists(word: Word): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(words: List<Word>)

    // UPDATE

    @Update
    fun update(word: Word)

    // DELETE

    @Delete
    fun delete(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    // READ

    @Query(
        """
            SELECT * FROM word_table
            WHERE id=:wordId
            ORDER BY easternAssyriac ASC"""
    )
    fun readById(wordId: Int): LiveData<Word>

    @Query(
        """
            SELECT * FROM word_table
            WHERE easternAssyriac=:easternAssyriac
            ORDER BY easternAssyriac ASC"""
    )
    fun read(easternAssyriac: String): LiveData<Word>

    @Query(
        """
            SELECT * FROM word_table
            WHERE category=:category
            ORDER BY easternAssyriac ASC"""
    )
    fun readAll(category: String): LiveData<List<Word>>

    @Query("SELECT * FROM word_table")
    fun readAll(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table")
    fun readAllFromTable(): LiveData<MutableList<Word>>
}