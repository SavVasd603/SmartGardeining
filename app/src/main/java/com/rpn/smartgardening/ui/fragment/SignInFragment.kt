package com.rpn.smartgardening.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.rpn.exchangebook.extensions.afterTextChanged
import com.rpn.smartgardening.R
import com.rpn.smartgardening.databinding.FragmentSignInBinding
import com.rpn.smartgardening.ui.viewmodel.SigninRegisterViewModel
import com.rpn.smartgardening.utils.GeneralUtils.loadingDialog
import org.koin.android.ext.android.inject


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!


    val viewModel by inject<SigninRegisterViewModel>()
    private var loadingDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this).get(SigninRegisterViewModel::class.java)

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkEmailpassword()

        viewModel.userLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            loadingDialog?.dismiss()
            if (it != null) {
                updateUiWithUser(it)
            }
        })

        viewModel.loginFormState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            if (loginState.userEmailError != null) {
                binding.etEmail.error = getString(loginState.userEmailError)
            }
            if (loginState.passwordError != null) {
                binding.etPassowrd.error = getString(loginState.passwordError)
            }
        })


    }

    fun checkEmailpassword() {

        binding.etEmail.afterTextChanged {
            viewModel.loginDataChanged(
                binding.etEmail.text.toString(),
                binding.etPassowrd.text.toString()
            )
        }

        binding.etPassowrd.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    binding.etEmail.text.toString(),
                    binding.etPassowrd.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            binding.etEmail.text.toString(),
                            binding.etPassowrd.text.toString()
                        )
                }
                false
            }

            binding.login.setOnClickListener {
                requireContext().loadingDialog {
                    loadingDialog = it
                    viewModel.login(
                        binding.etEmail.text.toString(),
                        binding.etPassowrd.text.toString()
                    )
                }
            }
        }
    }

    private fun updateUiWithUser(model: FirebaseUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            requireContext(),
            "$welcome ${model.email}",
            Toast.LENGTH_LONG
        ).show()

        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

        /*requireActivity().setResult(Activity.RESULT_OK)

        //Complete and destroy login activity once successful

        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()*/
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }


}