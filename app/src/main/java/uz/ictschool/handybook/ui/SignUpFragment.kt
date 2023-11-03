package uz.ictschool.handybook.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.handybook.R
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.User
import uz.ictschool.handybook.data.UserToken
import uz.ictschool.handybook.databinding.FragmentSignUpBinding
import uz.ictschool.handybook.services.SharedPreference
import java.util.regex.Pattern

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var mySharedPreferences: SharedPreference
    private val api = APIClient.getInstance().create(APIService::class.java)
    lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        mySharedPreferences = SharedPreference.newInstance(requireContext())
        binding.signupSignupMb.setOnClickListener {
            val data = checkRegistrationInfo(binding.signupIsm.text.toString(), binding.signupUsernameEditAcet.text.toString(),
                binding.signupEmailEditAcet.text.toString(), binding.signupPasswordEditAcet.text.toString(), binding.signupReenterPasswordEditAcet.text.toString())
            if (data != null){
                api.register(data).enqueue(object : Callback<UserToken>{
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
//                        binding.signupUsernameEditAcet.error = "fgsdfgsdfgds"
                        if (response.code() == 422){
                            binding.signupUsernameEditAcet.error = "Bunday username band"
                        }
                        if (response.isSuccessful && response.code() == 200){
                            val a = mutableListOf<UserToken>()
                            a.add(response.body()!!)
                            Log.d("TAGKOT", "onResponse: ${response.body()!!.id}")
                            mySharedPreferences.setLoginData(a)
                            Log.d("TAG123", "onResponse: ${mySharedPreferences.getLoginData()}")
                            parentFragmentManager.beginTransaction().replace(R.id.main, DefaultFragment()).commit()
                        }
                        Log.d("TAG", "onResponse: ${response.body()}")
                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.d("Register TAG", "onFailure: $t")
                    }

                })
            }
        }
        return binding.root
    }

    private fun checkRegistrationInfo(name: String, username: String, email: String, password: String, repassword:String ): User?{
        if (name.isEmpty()){
            binding.signupIsm.error = "Ismingiz va Familiyangizni kiriting"
            return null
        }
        if (username.isEmpty()){
            binding.signupUsernameEditAcet.error = "Username kiriting"
            return null
        }
        if (email.isEmpty()){
            binding.signupEmailEditAcet.error = "Email kiriting"
            return null
        }
        if (password.isEmpty()){
            binding.signupPasswordEditAcet.error = "Parol kiriting"
            return null
        }
        if (repassword.isEmpty()){
            binding.signupReenterPasswordEditAcet.error = "Parolni qayta kiriting"
            return null
        }

        if (name.length<6){
            binding.signupIsm.error = "Ismingiz va Familiyangizni to`liq kiriting"
            return null
        }
        if (username.length<3){
            binding.signupUsernameEditAcet.error = "Username 3ta belgidan kam"
            return null
        }
        if (password.length<8){
            binding.signupPasswordEditAcet.error = "Parol 8ta belgidan kam"
            return null
        }
        if (repassword != password){
            binding.signupReenterPasswordEditAcet.error = "Parol bir xil emas"
            return null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.signupEmailEditAcet.error = "Email kiriting"
            return null
        }

        return User(username, email, password, name)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}