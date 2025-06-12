package com.example.job_sec_app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.job_sec_app.databinding.FragmentApplyBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.UUID


class ApplyFragment : Fragment() {
    private var selectedResumeUri: Uri? = null
    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedResumeUri = uri
                binding.resumeTextView.text = "Resume selected: ${uri.lastPathSegment}"
            } else {
                Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show()
            }
        }
    private lateinit var storageRef: StorageReference
    private var _binding: FragmentApplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding only once
        _binding = FragmentApplyBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Firebase storage reference
        storageRef = FirebaseStorage.getInstance().reference

        // Set up the resume file picker
        binding.selectResumeButton.setOnClickListener {
            filePickerLauncher.launch("application/pdf")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val location = binding.locationEditText.text.toString()
            val skills = binding.skillsEditText.text.toString()
            val interest = binding.interestEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedResumeUri == null) {
                Toast.makeText(requireContext(), "Please select a resume", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputStream = requireContext().contentResolver.openInputStream(selectedResumeUri!!)
            if (inputStream == null) {
                Toast.makeText(requireContext(), "File not accessible", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            inputStream.close()


            val fileName = "resumes/${UUID.randomUUID()}.pdf"
            val fileRef = storageRef.child(fileName)

            fileRef.putFile(selectedResumeUri!!)
                .addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        sendApplicationToBackend()
                        Log.d("ResumeUpload", "Selected URI: $selectedResumeUri")
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    sendApplicationToBackend()
                }

            Toast.makeText(requireContext(), "Application Submitted!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun sendApplicationToBackend() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val location = binding.locationEditText.text.toString()
        val skills = binding.skillsEditText.text.toString()
        val interest = binding.interestEditText.text.toString()

        val contentResolver = requireContext().contentResolver
        val inputStream = selectedResumeUri?.let { contentResolver.openInputStream(it) }
        val fileBytes = inputStream?.readBytes()
        inputStream?.close()

        if (fileBytes == null) {
            Toast.makeText(requireContext(), "Error reading resume file", Toast.LENGTH_SHORT).show()
            return
        }

        val requestFile = MultipartBody.Part.createFormData(
            "resume",
            "resume.pdf",
            okhttp3.RequestBody.create(
                "application/pdf".toMediaType(),
                fileBytes
            )
        )

        val bodyMap = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "location" to location,
            "skills" to skills,
            "interest" to interest
        ).mapValues {
            RequestBody.create("text/plain".toMediaType(), it.value)
        }

        lifecycleScope.launch {
            try {
                RetrofitInstance.api.submitApplication(
                    name = bodyMap["name"]!!,
                    email = bodyMap["email"]!!,
                    phone = bodyMap["phone"]!!,
                    location = bodyMap["location"]!!,
                    skills = bodyMap["skills"]!!,
                    interest = bodyMap["interest"]!!,
                    resume = requestFile
                )
                Toast.makeText(requireContext(), "Application Submitted Successfully", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("Apply", "Error submitting application", e)
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


//    private fun sendApplicationToBackend(resumeUrl: String) {
//        val appData = ApplicationRequest(
//            name = binding.nameEditText.text.toString(),
//            email = binding.emailEditText.text.toString(),
//            phone = binding.phoneEditText.text.toString(),
//            location = binding.locationEditText.text.toString(),
//            skills = binding.skillsEditText.text.toString(),
//            interest = binding.interestEditText.text.toString(),
//            resumeUrl = resumeUrl
//        )
//
//        lifecycleScope.launch {
//            try {
//                RetrofitInstance.api.submitApplication(appData)
//                Log.e("Done", "No problem, submitted")
//                Toast.makeText(requireContext(), "Application Submitted!", Toast.LENGTH_LONG).show()
//            } catch (e: Exception) {
//                Log.e("Apply", "Submission failed", e) // or e.message
//                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}