/*
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

package assyrianoss.android.assyrianwords.view.util

import android.view.Gravity
import android.widget.Toast
import android.app.Application
import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes
import assyrianoss.android.assyrianwords.R
import com.google.android.material.snackbar.BaseTransientBottomBar

class ErrorToast(application: Application) : Toast(application) {

    companion object {

        fun makeText(context: Context, text: CharSequence, duration: Int): Toast {
            return makeText(context, null, text, duration)
        }

        fun makeText(context: Context, @StringRes resId: Int, duration: Int): Toast {
            return makeText(context, null, context.resources.getText(resId), duration)
        }

        private fun makeText(
            context: Context,
            looper: Looper?,
            text: CharSequence,
            @BaseTransientBottomBar.Duration duration: Int
        ): Toast {
            val result = Toast(context)
            val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflate.inflate(R.layout.toast_error_layout, null)
            val textView = view.findViewById(R.id.errorMessageTextView) as TextView
            textView.text = text
            result.setGravity(Gravity.BOTTOM, 0, 0)
            result.view = view
            result.duration = duration
            return result
        }
    }
}