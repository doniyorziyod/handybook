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
import uz.ictschool.handybook.R
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.data.CommentData
import uz.ictschool.handybook.data.CommentDataOrigin
import uz.ictschool.handybook.databinding.FragmentRatingBinding
import uz.ictschool.handybook.services.SharedPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Book? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Book

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRatingBinding.inflate(inflater,container,false)

//        binding.ratingBar.rating
        val shared = SharedPreference.newInstance(requireContext())
        val user = shared.getLoginData()
        val api = APIClient.getInstance().create(APIService::class.java)

        binding.jonatish.setOnClickListener {
//            Log.d("TAG", "onCreateView: ${user.get(0).id}")
            var commentData = CommentDataOrigin(book_id = param1!!.id, reyting = binding.ratingBar.rating.toInt(), text = binding.commentsss.text.toString(), user_id = user.get(0).id)


            api.giveCommentToTheBook(commentData).enqueue(object :Callback<CommentData>{
                override fun onResponse(call: Call<CommentData>, response: Response<CommentData>) {
                    Log.d("TAG6", "onResponse: ${response.body()}")
                    Toast.makeText(requireContext(), "sent", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction().replace(R.id.main,CommentFragment()).commit()
                }

                override fun onFailure(call: Call<CommentData>, t: Throwable) {
                    Log.d("TAG", "onFailure: $t")
                }

            })
        }

        binding.textView7.setText(param1!!.name+" romani sizga qanchalik manzur keldi?")


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RatingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Book) =
            RatingFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)

                }
            }
    }
}