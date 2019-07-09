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

package assyrianoss.android.assyrianwords.model.persistence.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "word_table",
    indices = [Index("easternPhonetic", unique = true)]
)
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @field:SerializedName("english") val english: String,
    @field:SerializedName("eastern phonetic") val easternPhonetic: String,
    @field:SerializedName("eastern assyriac") val easternAssyriac: String,
    @field:SerializedName("western phonetic") val westernPhonetic: String,
    @field:SerializedName("western assyriac") val westernAssyriac: String,
    @field:SerializedName("akkadian") val akkadian: String,
    @field:SerializedName("aramaic") val aramaic: String,
    @field:SerializedName("definition") val definition: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("category") val category: String,
    @field:SerializedName("example") val example: String
)