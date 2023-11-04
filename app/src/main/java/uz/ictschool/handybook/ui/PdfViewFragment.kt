package uz.ictschool.handybook.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentPdfViewBinding
import uz.ictschool.handybook.services.SharedPreference
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PdfViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PdfViewFragment : Fragment() {
//    lateinit var pdfView: PdfDocument


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
    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mySharedPreferences = SharedPreference.newInstance(requireContext())
//        selectedBooks = mySharedPreferences.GetSelectedBooks()
        finishedBooks = mySharedPreferences.getFinishedBook()
        binding = FragmentPdfViewBinding.inflate(inflater, container, false)

        Log.d("TAG", "onCreateView: ")
        if (RetrievePDFFromURL(binding.idPDFView).execute("http://b1.culture.ru/c/98010/idiot.pdf") == null){
//            val assetManager = requireContext().assets
//            var inputStream = assetManager.open("error.pdf").toString()
//            val pdfView:PDFView = binding.idPDFView
//            pdfView.fromAsset(inputStream).load()
            Toast.makeText(requireContext(), "your pdf is corrupted", Toast.LENGTH_SHORT).show()
            binding.finished.visibility = View.INVISIBLE
        }

       else {
            binding.finished.visibility = View.VISIBLE
            RetrievePDFFromURL(binding.idPDFView).execute("http://b1.culture.ru/c/98010/idiot.pdf")
        }
        // on below line we are calling our async
        // task to load our pdf file from url.
        // we are also passing our pdf view to
        // it along with pdf view url.

        binding.finished.setOnClickListener {
            param1!!.book_in_progress = 2
            finishedBooks.add(param1!!)
            mySharedPreferences.setFinishedBook(finishedBooks)
            parentFragmentManager.beginTransaction().replace(R.id.main,HomeFragment()).commit()
        }



        return binding.root
    }
    class RetrievePDFFromURL(pdfView: PDFView) :
        AsyncTask<String, Void, InputStream>() {

        var mypdfView: PDFView = pdfView

        override fun doInBackground(vararg params: String?): InputStream? {
            // on below line we are creating a variable for our input stream.
            var inputStream: InputStream? = null
            try {
                val url = URL(params.get(0))


                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection


                if (urlConnection.responseCode == 200) {

                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }

            catch (e: Exception) {

                e.printStackTrace()
                return null;
            }

            return inputStream;
        }

        override fun onPostExecute(result: InputStream?) {

            mypdfView.fromStream(result).load()

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
}