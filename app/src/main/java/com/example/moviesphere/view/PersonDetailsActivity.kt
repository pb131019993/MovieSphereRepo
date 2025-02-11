package com.example.moviesphere.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesphere.adapters.PersonImageAdapter
import com.example.moviesphere.beans.response.PersonImage
import com.example.moviesphere.databinding.ActivityPersonDetailsBinding
import com.example.moviesphere.viewmodel.PersonViewModel

class PersonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonDetailsBinding
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var imageAdapter: PersonImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Person Details")

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

// Initialize RecyclerView for Images
        imageAdapter = PersonImageAdapter(emptyList())
        binding.imageGrid.apply {
            layoutManager = GridLayoutManager(this@PersonDetailsActivity, 2) // 2 columns
            adapter = imageAdapter
        }

        // Get Person ID from Intent
        val personId = intent.getIntExtra("person_id", -1)
        if (personId != -1) {
            viewModel.loadPersonDetails(personId)
        }

        // Observe Data from ViewModel
        viewModel.personDetails.observe(this) { details ->
            binding.name.text = details.name
            binding.biography.text = details.biography

            // Load Profile Image
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${details.profile_path}")
                .into(binding.profileImage)

            // Update RecyclerView with Images
            imageAdapter.updateImages(details.images.map { PersonImage(it) })
        }
    }
}