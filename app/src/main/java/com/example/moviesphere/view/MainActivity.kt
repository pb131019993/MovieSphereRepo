package com.example.moviesphere.view

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        adapter = PersonAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = this@MainActivity.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && !isLoading) {
                        loadMore()
                    }
                }
            })
        }

        viewModel.popularPeople.observe(this) { people ->
            adapter.submitList(people)
            isLoading = false
        }

        viewModel.loadPopularPeople(currentPage)
    }

    private fun loadMore() {
        isLoading = true
        currentPage++
        viewModel.loadPopularPeople(currentPage)
    }
}
