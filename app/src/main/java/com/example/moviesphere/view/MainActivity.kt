package com.example.moviesphere.view

import android.content.Intent
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
import com.example.moviesphere.databinding.ActivityMainBinding
import com.example.moviesphere.viewmodel.PersonViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonAdapter { person ->
            val intent = Intent(this, PersonDetailsActivity::class.java).apply {
                putExtra("person_id", person.id)
                putExtra("person_name", person.name)
                putExtra("profile_path", person.profile_path)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
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

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                binding.searchIcon.visibility = if (query.isEmpty()) ImageView.VISIBLE else ImageView.GONE

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