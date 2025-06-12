package com.example.job_sec_app
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.job_sec_app.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                findNavController().navigate(R.id.action_loginFragment_to_internshipListFragment)
//                lifecycleScope.launch {
//                    // Changed from viewModelScope to lifecycleScope
//                    try {
//                        val response = ApiClient.apiService.login(
//                            mapOf("email" to email, "password" to password)
//                        )
//
//                        if (response.isSuccessful) {
//                            findNavController().navigate(R.id.action_loginFragment_to_internshipListFragment)
//                        } else {
//                            // Show error
//                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//                            findNavController().navigate(R.id.action_loginFragment_to_internshipListFragment)
//                        }
//                    } catch (e: Exception) {
//                        // Show error
//                        Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
//                        Log.i("Network","$e.message")
//                    }
//                }
            }
        }

        binding.signupText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return binding.root
    }
}