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

package assyrianoss.android.assyrianwords.model.network

import assyrianoss.android.assyrianwords.model.persistence.AppDatabase
import assyrianoss.android.assyrianwords.model.persistence.daos.CategoryDao
import assyrianoss.android.assyrianwords.model.persistence.daos.WordDao
import assyrianoss.android.assyrianwords.model.persistence.entities.Word
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubFetcher(val baseUrl: String, val group: String, val repo: String,
                    val branch: String, val filePaths: List<String>) {

    private lateinit var retrofit: Retrofit

    init {
        buildRetrofitObject(baseUrl)
    }

    private fun buildRetrofitObject(url: String) {
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun fetchAllKnown(wordDao: WordDao, categoryDao: CategoryDao) {
        for (path in filePaths) {
            fetch(path, wordDao, categoryDao)
        }
    }

    fun fetch(filePath: String, wordDao: WordDao, categoryDao: CategoryDao) {
        GlobalScope.launch {
            val service = retrofit.create(GithubService::class.java)
            val content = service.getFile(group, repo, branch, filePath)
            val words: List<Word>? = content.execute().body()
            words?.let {
                AppDatabase.store(it, wordDao, categoryDao)
            }
        }
    }
}