package com.example.job_sec_app

import InternshipViewModel
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job_sec_app.databinding.DetailsPageBinding
import kotlin.getValue
class DetailsPage : Fragment() {
    private var _binding: DetailsPageBinding? = null
    private val binding get() = _binding!!

    // Initialize ViewModel properly using viewModels delegate
    private val viewModel: InternshipViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsPageBinding.inflate(inflater, container, false)
        return binding.root.apply {
            // Temporary debug background
            setBackgroundColor(Color.WHITE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Clear any keyboard focus
        view.clearFocus()

        // Setup RecyclerView with test data
        setupRecyclerView()

        // Configure swipe refresh


        // Initial data load
        loadData()
    }

    private fun setupRecyclerView() {

    }

    private fun loadData() {

        }



        // Temporary test data - replace with real data loading
//        val testItems = listOf(
//            Internship("Backend Engineer", "Amazon", "Develop APIs", "$1800/month","",),
//            Internship("Web Developer","Renu Sharma","Build Web apps","$200","",)
//        )
//
//        (binding.recyclerView.adapter as InternshipAdapter).submitList(testItems)
//        binding.swipeRefreshLayout.isRefreshing = false
    }