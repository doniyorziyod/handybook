package uz.ictschool.handybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import uz.ictschool.handybook.R
import uz.ictschool.handybook.adapter.SavedAdapter
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentSavedBooksBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SavedBooksFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSavedBooksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBooksBinding.inflate(inflater, container, false)
        var myshared = SharedPreference.newInstance(requireContext())

        binding.savedRecycler.adapter = SavedAdapter(requireContext(), object : SavedAdapter.OnClicked{
            override fun onClicked(book: Book) {
                parentFragmentManager.beginTransaction().replace(R.id.main, BookViewFragment.newInstance(book)).commit()
            }
        })
        binding.savedRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.savedBackToHome.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, DefaultFragment()).commit()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}