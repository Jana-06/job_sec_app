package com.example.job_sec_app

import InternshipViewModel
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job_sec_app.databinding.FragmentInternshipListBinding
import kotlin.getValue
class InternshipListFragments : Fragment() {
    private var _binding: FragmentInternshipListBinding? = null
    private val binding get() = _binding!!

    // Initialize ViewModel properly using viewModels delegate
    private val viewModel: InternshipViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInternshipListBinding.inflate(inflater, container, false)
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
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        // Initial data load
        loadData()
    }

    private fun setupRecyclerView() {
        val adapter = InternshipAdapter(
            onItemClick = { selectedInternship ->
                val bundle = Bundle().apply {
                    putString("internshipId", selectedInternship._id)
                }
                findNavController().navigate(
                    R.id.action_internshipListFragment_to_internshipDetailFragment,
                    bundle
                )
            },
            onApplyClick = { selectedInternship ->
                val bundle = Bundle().apply {
                    putString("internshipId", selectedInternship._id)
                }
                findNavController().navigate(
                    R.id.action_internshipListFragment_to_ApplyFragment,
                    bundle
                )
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }


    private fun loadData() {
        Log.i("SEE THE DATA BASE","${viewModel._internships}")

        binding.searchEditText.addTextChangedListener { text ->
            val query = text.toString()
            viewModel.searchInternships(query)
        }


        viewModel.internships.observe(viewLifecycleOwner) { internships ->
            (binding.recyclerView.adapter as InternshipAdapter).submitList(internships)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Log.e("InternshipList", "Error: $it")
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        viewModel.fetchInternships()

        // Temporary test data - replace with real data loading
//        val testItems = listOf(
//            Internship("Backend Engineer", "Amazon", "Develop APIs", "$1800/month","",),
//            Internship("Web Developer","Renu Sharma","Build Web apps","$200","",)
//        )
//
//        (binding.recyclerView.adapter as InternshipAdapter).submitList(testItems)
//        binding.swipeRefreshLayout.isRefreshing = false
    }
    override fun onDestroyView() {
        // Properly clean up views
        binding.recyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}