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
import assyrianoss.android.assyrianwords.model.persistence.entities.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIfNotExists(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(categories: List<Category>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("DELETE FROM category_table")
    fun deleteAll()

    @Query(
        """
        SELECT * FROM category_table
        WHERE id=:categoryId"""
    )
    fun readById(categoryId: Int): LiveData<Category>

    @Query("SELECT * FROM category_table ORDER BY name ASC")
    fun readAllFromTable(): LiveData<List<Category>>

    @Query("SELECT * FROM category_table WHERE id=:id")
    fun readCategoryById(id: Int): LiveData<Category>

    @Query("SELECT COUNT(*) FROM category_table")
    fun count(): Int
}