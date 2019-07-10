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

package assyrianoss.android.assyrianwords.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import assyrianoss.android.assyrianwords.R
import assyrianoss.android.assyrianwords.model.persistence.entities.Category
import assyrianoss.android.assyrianwords.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_category.view.*

class CategoryFragment : Fragment() {

    private lateinit var adapter: WordCategoryAdapter
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_category, container, false)
        setupViewModel()
        setupRecyclerViewAdapter()
        setupAdapterOnClickListener()
        setupRecyclerView(view)
        registerObserverOnCategories()
        return view
    }

    private fun setupViewModel() {
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        }
    }

    private fun setupRecyclerViewAdapter() {
        adapter = WordCategoryAdapter(viewModel)
    }

    private fun setupAdapterOnClickListener() {
        adapter.setOnItemClickListener(object :
            WordCategoryAdapter.OnItemClickListener {
            override fun onItemClick(category: Category) {
                val action = CategoryFragmentDirections
                    .actionCategoryFragmentToWordListFragment(category.name)
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private fun setupRecyclerView(view: View) {
        view.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.categoryRecyclerView.hasFixedSize()
        view.categoryRecyclerView.adapter = adapter
    }

    private fun registerObserverOnCategories() {
        viewModel.categories.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }
}