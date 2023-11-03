package uz.ictschool.handybook.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import uz.ictschool.handybook.R
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.databinding.FragmentSignInBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignInFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSignInBinding
    private var api =APIClient.getInstance().create(APIService::class.java)
    private lateinit var helper: Helper



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater,container,false)
        helper =Helper.getInstance(requireContext())


//
//        setLoginButton()
//        setBackButton()
        return binding.root
    }


//    private fun setBackButton() {
//        binding.continueBtn.setOnClickListener {
//            findNavController().popBackStack()
//        }
//    }

//    private fun setLoginButton() {
//        binding.loginLoginMb.setOnClickListener {
//            val signIn = SignIn(
//                binding.loginUsernameEditAcet.text.toString().trim(),
//                binding.loginPasswordEditAcet.text.toString().trim()
//            )
//            if (signIn.password == "" || signIn.username == "") return@setOnClickListener
//            if (!validate(signIn)) return@setOnClickListener
//            api.login(signIn).enqueue(object: Callback<User> {
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (!response.isSuccessful) {
//                        binding.loginPasswordEditAcet.setText("")
//                        Toast.makeText(requireContext(), "Noto'g'ri username yoki parol", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//                    val user: User = response.body()!!
//                    if (binding.loginRememberMeAcchb.isChecked) shared.setRememberMe(user.username) else shared.setRememberMe("")
//                    shared.setUser(user)
//                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
//                }
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    Log.d("TAG", "$t")
//                }
//
//            })
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}