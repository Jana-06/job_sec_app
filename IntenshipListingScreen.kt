package com.example.job_sec_app

import InternshipViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.job_sec_app.databinding.FragmentInternshipListBinding
import com.example.job_sec_app.databinding.ItemInternshipBinding

class InternshipListFragment : Fragment() {

    private lateinit var binding: FragmentInternshipListBinding
    private val viewModel: InternshipViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInternshipListBinding.inflate(inflater, container, false)

        // Adapter with item click listener that navigates to details fragment
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

        viewModel.internships.observe(viewLifecycleOwner) { internships ->
            adapter.submitList(internships)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchInternships()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.fetchInternships()
        return binding.root
    }
}


//class InternshipAdapter : RecyclerView.Adapter<InternshipAdapter.InternshipViewHolder>() {
class InternshipAdapter(
    private val onItemClick: (Internship) -> Unit,
    private val onApplyClick: (Internship) -> Unit
) : RecyclerView.Adapter<InternshipAdapter.ViewHolder>() {

    private var items = emptyList<Internship>()

    fun submitList(newItems: List<Internship>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemInternshipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Internship) {
            binding.titleTextView.text = item.title
            binding.companyTextView.text = item.company
            binding.descriptionTextView.text = item.description
            binding.stipendTextView.text = item.stipend

            // Handle click inside ViewHolder
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            binding.applyButton.setOnClickListener {
                onApplyClick(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInternshipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
