package uz.ictschool.handybook.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.handybook.R
import uz.ictschool.handybook.adapter.CustomBooksList
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentProfileBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    lateinit var mySharedPreferences: SharedPreferences
    private val api = APIClient.getInstance().create(APIService::class.java)
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profileSaqlanganKitoblarRecycler.adapter = CustomBooksList(requireContext())


        binding.profileBackToHome.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, DefaultFragment()).addToBackStack("Profile").commit()
        }

        binding.inReadingView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, InReadingFragment()).addToBackStack("Profile").commit()
        }
        binding.outReadingView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, OutReadingFragment()).addToBackStack("Profile").commit()
        }
        binding.savedView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, SavedBooksFragment()).addToBackStack("Profile").commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}