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

package assyrianoss.android.assyrianwords.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import assyrianoss.android.assyrianwords.R
import assyrianoss.android.assyrianwords.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_word_detail.view.*

class WordDetailFragment() : Fragment() {

    private var wordId: Int = 0

    private lateinit var adapter: WordDetailAdapter
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_word_detail, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        setupVariables()
        setupViewModel()
        setupUi(view)
        setupRecyclerViewAdapter()
        setupRecyclerView(view)
        queryWordById()
        registerObserverOnWords()
    }

    private fun setupVariables() {
        val args: WordDetailFragmentArgs by navArgs()
        wordId = args.wordId
    }

    private fun setupUi(view: View) {
        setupToolbarBackButtonClickListener(view)
        setupToolbarBookmarkImage(view)
        setupToolbarBookmarkClickListener(view)
        setupToolbarText(view)
    }

    private fun setupToolbarBackButtonClickListener(view: View) {
        view.detailToolbarBackButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun setupToolbarBookmarkImage(view: View) {
        toggleBookmarkImage(view)
    }

    private fun setupToolbarBookmarkClickListener(view: View) {
        view.detailBookmarkButton.setOnClickListener {
            toggleBookmarkImage(view)
        }
    }

    private fun setupToolbarText(view: View) {
        viewModel.getWord(wordId).observe(this, Observer {
            it?.let {word ->
                setupToolbarAssyriacText(view, word.easternAssyriac)
                setupToolbarPhoneticText(view, word.easternPhonetic)
                setupToolbarAudioButton(view, false) // TODO: pass value from word
            }
        })
    }

    private fun setupToolbarAssyriacText(view: View, text: String) {
        view.headerAssyriacTextView.text = text
    }

    private fun setupToolbarPhoneticText(view: View, text: String) {
        view.headerPhoneticTextView.text = text
    }

    private fun setupToolbarAudioButton(view: View, hasAudio: Boolean) {
        // TODO: build function
    }

    private fun toggleBookmarkImage(view: View) {
        if (viewModel.isWordBookmarked(wordId)) {
            view.detailBookmarkButton.setImageResource(R.drawable.ic_bookmark_white_24dp)
            viewModel.unbookmarkWord(wordId)
        } else {
            view.detailBookmarkButton.setImageResource(R.drawable.ic_bookmark_gray_opaque_24dp)
            viewModel.bookmarkWord(wordId)
        }
    }

    private fun setupViewModel() {
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        }
    }

    private fun setupRecyclerViewAdapter() {
        adapter = WordDetailAdapter(viewModel)
    }

    private fun setupRecyclerView(view: View) {
        view.detailRecyclerView.layoutManager = LinearLayoutManager(context)
        view.detailRecyclerView.hasFixedSize()
        view.detailRecyclerView.adapter = adapter
    }

    private fun queryWordById() {
        viewModel.getWord(wordId)
    }

    private fun registerObserverOnWords() {
        viewModel.queriedWord.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }
}