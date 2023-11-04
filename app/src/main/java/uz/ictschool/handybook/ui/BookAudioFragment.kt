package uz.ictschool.handybook.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.databinding.FragmentBookAudioBinding
import uz.ictschool.handybook.databinding.FragmentBookViewBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
@Suppress("DEPRECATION")
class BookAudioFragment : Fragment() {

    private var param1: Book? = null
    lateinit var media : MediaPlayer
    lateinit var mySharedPreferences: SharedPreference
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
        media = MediaPlayer()
        val binding = FragmentBookAudioBinding.inflate(inflater, container, false)

        mySharedPreferences = SharedPreference.newInstance(requireContext())
        val book = mySharedPreferences.getThisBook()
        Log.d("AUDIO", "onCreateView: ${book.get(0)}")
        binding.playpause.setOnClickListener {
            media.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                media.setDataSource(book[0].audio.toString())
                media.prepare()
                media.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(requireContext(), "Audio started playing..", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Book) =
            BookViewFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}