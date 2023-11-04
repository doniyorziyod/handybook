package uz.ictschool.handybook.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import okhttp3.internal.wait
import uz.ictschool.handybook.R
import uz.ictschool.handybook.databinding.FragmentSplashBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)
        var shared = SharedPreference.newInstance(requireContext())
        var shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        val replenish = AnimationUtils.loadAnimation(requireContext(), R.anim.replenish)
        val dissapear = AnimationUtils.loadAnimation(requireContext(), R.anim.dissappear)


//            binding.imageView.startAnimation(dissapear)
            binding.splashLottie.startAnimation(dissapear)

            dissapear.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    binding.imageView.visibility = View.GONE
                    binding.splashLottie.visibility = View.GONE

                    binding.splashSecond.visibility = View.VISIBLE
//                    binding.splashSecond.startAnimation(shake)
                    binding.splashSecond.startAnimation(replenish)


                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

            })

        binding.login.setOnClickListener {
            if (shared.getLoginData().isNotEmpty()){
                parentFragmentManager.beginTransaction().replace(R.id.main,DefaultFragment()).commit()
            }else{
                parentFragmentManager.beginTransaction().replace(R.id.main,SignInFragment()).commit()
            }

        }
        binding.register.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,SignUpFragment()).commit()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}