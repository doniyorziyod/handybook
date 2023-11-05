package uz.ictschool.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ictschool.handybook.R
import uz.ictschool.handybook.adapter.CustomBooksList
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentInReadingBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InReadingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentInReadingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInReadingBinding.inflate(inflater, container, false)
        val myShared = SharedPreference.newInstance(requireContext())

        if (myShared.getInProgressBook().isEmpty()){
            binding.InReadingRecycler.visibility = View.GONE
            binding.inReadingNotFound.visibility = View.VISIBLE
        }else{
            binding.InReadingRecycler.adapter = CustomBooksList(myShared.getInProgressBook(), object : CustomBooksList.OnClick{
                override fun onClick(book: Book) {
                    parentFragmentManager.beginTransaction().replace(R.id.main, BookViewFragment.newInstance(book)).addToBackStack("Profile").commit()
                }
            })
            binding.InReadingRecycler.visibility = View.VISIBLE
            binding.inReadingNotFound.visibility = View.GONE
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InReadingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}