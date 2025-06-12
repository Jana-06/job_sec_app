package com.example.job_sec_app

import InternshipViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.job_sec_app.databinding.FragmentInternshipDetailBinding

class InternshipDetailFragment : Fragment() {

    private var _binding: FragmentInternshipDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InternshipViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInternshipDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val internshipId = arguments?.getString("internshipId") ?: return

        viewModel.getInternshipById(internshipId)

        viewModel.selectedInternship.observe(viewLifecycleOwner) { internship ->
            binding.titleTextView.text = internship.title
            binding.descriptionTextView.text = internship.description
            binding.durationTextView.text = internship.duration

            Glide.with(this)
                .load(internship.imageUrl)
                .into(binding.imageView)
        }
        binding.ApplyButton.setOnClickListener {
            findNavController().navigate(R.id.action_internshipDetailFragment_to_applyFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
