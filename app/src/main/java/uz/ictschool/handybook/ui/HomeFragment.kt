package uz.ictschool.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import farrukh.remotely.adapter.CategoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.CategoryData
import uz.ictschool.handybook.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "TAG"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.imageView2.setOnClickListener {
//            parentFragmentManager.beginTransaction().replace(R.id.main, ProfileFragment()).commit()
//        }
        val api = APIClient.getInstance().create(APIService::class.java)
        var categories = mutableListOf<CategoryData>()

        api.getAllCategory().enqueue(object : Callback<List<CategoryData>> {
            override fun onResponse(
                call: Call<List<CategoryData>>,
                response: Response<List<CategoryData>>
            ) {
                for (i in 0 until response.body()!!.size) {
                    categories.add(response.body()!!.get(i))
                }
                if (categories.isNotEmpty()) {
                    var adapter =
                        CategoryAdapter(
                            categories,
                            requireContext(),
                            object : CategoryAdapter.ItemClick {
                                override fun OnItemClick(category: String) {

                                }

                            })

                    var manager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    binding.category.layoutManager = manager
                    binding.category.adapter = adapter
                }


            }

            override fun onFailure(call: Call<List<CategoryData>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}