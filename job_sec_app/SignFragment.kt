package com.example.job_sec_app

import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.job_sec_app.databinding.FragmentSignupBinding
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (password == confirmPassword && name.isNotEmpty() && email.isNotEmpty()) {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                lifecycleScope.launch {
                    // Changed from viewModelScope to lifecycleScope
                    try {
                        val response = ApiClient.apiService.signUp(User(name, email, password))
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                        if (response.isSuccessful) {
                            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                            Log.i("Signup","SUCCESSFULL!!")
                        } else {
                            // Show error
                            Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                            Log.i("Signup","Failed")
                        }
                    } catch (e: Exception) {
                        // Show error
                        Toast.makeText(requireContext(), "Login", Toast.LENGTH_SHORT).show()
                        Log.i("Network","${e.message}")
                    }
                }
            }
        }

        return binding.root
    }
}