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

package assyrianoss.android.assyrianwords.view.list

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
import kotlinx.android.synthetic.main.fragment_wordlist.view.*

class WordListFragment : Fragment() {

    private lateinit var adapter: WordListAdapter
    private lateinit var category: String
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_wordlist, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        setupVariables()
        setupViewModel()
        setupRecyclerViewAdapter()
        setupAdapterOnClickListener()
        setupRecyclerView(view)
        queryWordList()
        registerObserverOnWords()
    }

    private fun setupVariables() {
        val args: WordListFragmentArgs by navArgs()
        category = args.category
    }

    private fun setupViewModel() {
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        }
    }

    private fun setupRecyclerViewAdapter() {
        adapter = WordListAdapter(viewModel)
    }

    private fun setupAdapterOnClickListener() {
        adapter.setOnItemClickListener(object :
            WordListAdapter.OnItemClickListener {
            override fun onItemClick(wordId: Int) {
                val action = WordListFragmentDirections
                    .actionWordListFragmentToWordDetailFragment(wordId)
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private fun setupRecyclerView(view: View) {
        view.wordsRecyclerView.layoutManager = LinearLayoutManager(context)
        view.wordsRecyclerView.hasFixedSize()
        view.wordsRecyclerView.adapter = adapter
    }

    private fun queryWordList() {
        viewModel.getNewWords(category.decapitalize())
    }

    private fun registerObserverOnWords() {
        viewModel.queriedWords.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }
}