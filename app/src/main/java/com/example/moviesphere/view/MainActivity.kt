package com.example.moviesphere.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesphere.R
import com.example.moviesphere.adapters.PersonAdapter
import com.example.moviesphere.viewmodel.PersonViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var adapter: PersonAdapter
    private var isLoading = false
    private var currentPage = 1
    private var searchQuery: String? = null
    private var searchPage = 1
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchBar = findViewById<EditText>(R.id.search_bar)
        val searchIcon = findViewById<ImageView>(R.id.search_icon)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        adapter = PersonAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = this@MainActivity.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && !isLoading) {
                        if (isSearching) {
                            searchPage++
                            searchQuery?.let { viewModel.searchPeople(it, searchPage) }
                        } else {
                            loadMore()
                        }
                    }
                }
            })
        }

        viewModel.popularPeople.observe(this) { people ->
            if (!isSearching) {
                adapter.submitList(people)
                isLoading = false
            }
        }

        viewModel.searchResults.observe(this) { results ->
            if (isSearching) {
                adapter.submitList(results)
                isLoading = false
            }
        }

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                searchIcon.visibility = if (query.isEmpty()) ImageView.VISIBLE else ImageView.GONE

                if (query.isNotEmpty()) {
                    isSearching = true
                    searchQuery = query
                    searchPage = 1
                    viewModel.searchPeople(query, searchPage)
                } else {
                    isSearching = false
                    searchQuery = null
                    adapter.submitList(viewModel.popularPeople.value ?: emptyList())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewModel.loadPopularPeople(currentPage)
    }

    private fun loadMore() {
        isLoading = true
        currentPage++
        viewModel.loadPopularPeople(currentPage)
    }
}