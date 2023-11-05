package uz.ictschool.handybook.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentPdfViewBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@Suppress("DEPRECATION")
class PdfViewFragment : Fragment() {
    private var param1: Book? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Book
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentPdfViewBinding

    lateinit var finishedBooks: MutableList<Book>

    lateinit var mySharedPreferences: SharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPdfViewBinding.inflate(inflater, container, false)
        mySharedPreferences = SharedPreference.newInstance(requireContext())
        finishedBooks = mySharedPreferences.getFinishedBook()
        binding.finished.setOnClickListener {
            if (param1 !in finishedBooks || finishedBooks.isEmpty()){
                finishedBooks.add(param1!!)
                mySharedPreferences.setFinishedBook(finishedBooks)
                Log.d("Finished Books", "onCreateView: $finishedBooks")
            }
            parentFragmentManager.beginTransaction().replace(R.id.main, DefaultFragment()).commit()
        }
        openPDF("https://dagrs.berkeley.edu/sites/default/files/2020-01/sample.pdf")

        return binding.root
    }

    private fun openPDF(url: String) {
        binding.idPDFView.fromUri(Uri.parse(url)).defaultPage(0).enableSwipe(true).load()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Book) =
            PdfViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}