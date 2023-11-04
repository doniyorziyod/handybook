package uz.ictschool.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import uz.ictschool.handybook.R
import uz.ictschool.handybook.databinding.FragmentDefaultBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DefaultFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentDefaultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultBinding.inflate(inflater, container, false)
        val mysgared = SharedPreference.newInstance(requireContext())

//        val a = mysgared.getNavBar()
//        if (a[0]=="Saved"){
//            parentFragmentManager.beginTransaction().add(R.id.main_frame, SavedBooksFragment()).commit()
//            binding.navigationView.show(1)
//        }
//        if (a[0]=="Home"){
//            parentFragmentManager.beginTransaction().add(R.id.main_frame, HomeFragment()).commit()
//            binding.navigationView.show(0)
//        }
//        if (a[0]=="Profile"){
//            parentFragmentManager.beginTransaction().add(R.id.main_frame, ProfileFragment()).commit()
//            binding.navigationView.show(2)
//        }
//        if (a.isEmpty())
//            parentFragmentManager.beginTransaction().add(R.id.main_frame, HomeFragment()).commit()
//        binding.navigationView.show(0)

        parentFragmentManager.beginTransaction().add(R.id.main_frame, HomeFragment()).commit()
        binding.navigationView.show(0)

        binding.navigationView.add(MeowBottomNavigation.Model(0, R.drawable.home_ic2))
        binding.navigationView.add(MeowBottomNavigation.Model(1, R.drawable.saved_ic2))
        binding.navigationView.add(MeowBottomNavigation.Model(2, R.drawable.person_ic2))

        binding.navigationView.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    parentFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
                }
                1 ->{
                    parentFragmentManager.beginTransaction().replace(R.id.main_frame, SavedBooksFragment()).commit()
                }
                2 ->{
                    parentFragmentManager.beginTransaction().replace(R.id.main_frame, ProfileFragment()).commit()
                }
            }
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DefaultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}