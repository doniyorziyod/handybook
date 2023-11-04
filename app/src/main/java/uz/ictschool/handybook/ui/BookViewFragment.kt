package uz.ictschool.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import farrukh.remotely.adapter.BookAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.handybook.R
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentBookViewBinding
import uz.ictschool.handybook.services.SharedPreference


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Book? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Book

        }
    }

    lateinit var inProgressBook: MutableList<Book>
    lateinit var selectedBooks: MutableList<Book>
    lateinit var mySharedPreferences: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBookViewBinding.inflate(inflater, container, false)
        mySharedPreferences = SharedPreference.newInstance(requireContext())
        selectedBooks = mySharedPreferences.GetSelectedBooks()
        inProgressBook = mySharedPreferences.getInProgressBook()
        binding.pdfViewBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,PdfViewFragment()).commit()
        }
        val api = APIClient.getInstance().create(APIService::class.java)
//        val books = mutableListOf<Book>()
        binding.appCompatImageView.load(param1!!.image)
        binding.textView5.setText(param1!!.name)
        binding.description.setText(param1!!.description)

        api.getAllBooks().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
//                books = response.body()!!.toMutableList()
                var layoutManager = GridLayoutManager(requireContext(),2,
                    LinearLayoutManager.VERTICAL,false)

                val adapter =   BookAdapter(response.body()!!.toMutableList(), object : BookAdapter.ItemClick {
                    override fun OnItemClick(book: Book) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.main, newInstance(book)).addToBackStack("Home")
                            .commit()
                    }

                }, requireContext())

                binding.tavsiyalar.adapter = adapter
                binding.tavsiyalar.layoutManager = layoutManager
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })

        binding.commentBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CommentFragment.newInstance(param1!!)).commit()
        }
        binding.pdfViewBtn.setOnClickListener{
            param1!!.book_in_progress = 1
            if (inProgressBook.isNotEmpty()){
                if (param1 !in inProgressBook){
                    inProgressBook.add(param1!!)
                }
            }else{
                inProgressBook.add(param1!!)
            }

            mySharedPreferences.setInProgressBook(inProgressBook)
            parentFragmentManager.beginTransaction().replace(R.id.main,PdfViewFragment.RetrievePDFFromURL.newInstance(param1!!)).commit()
        }


        val book = mutableListOf<Book>()
        book.add(param1!!)
        binding.audiokitob.setOnClickListener {
            Log.d("TAGBook", "onCreateView: $book")
            parentFragmentManager.beginTransaction().replace(R.id.main, BookAudioFragment()).commit()
            mySharedPreferences.setThisBook(book!!)
        }

        binding.back.setOnClickListener { parentFragmentManager.beginTransaction().replace(R.id.main, HomeFragment()).commit() }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Book) =
            BookViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)

                }
            }
    }


}