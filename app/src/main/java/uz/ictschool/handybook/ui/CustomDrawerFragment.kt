package uz.ictschool.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.ictschool.handybook.R
import uz.ictschool.handybook.databinding.FragmentBookAudioBinding
import uz.ictschool.handybook.databinding.FragmentCustomDrawerBinding
import uz.ictschool.handybook.services.SharedPreference

class CustomDrawerFragment : Fragment() {
    lateinit var binding : FragmentCustomDrawerBinding
    lateinit var mySharedPreferences: SharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDrawerBinding.inflate(inflater, container, false)
        mySharedPreferences = SharedPreference.newInstance(requireContext())

        binding.profilePicture.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, ProfileFragment()).commit()
        }
        binding.drawerUsername.text = mySharedPreferences.getLoginData()[0].fullname
        binding.drawerEmail.text = mySharedPreferences.getLoginData()[0].username
        Log.d("TAGCUSTOM", "onCreateView: ${mySharedPreferences.getLoginData()[0].fullname}")
        return binding.root
    }
}