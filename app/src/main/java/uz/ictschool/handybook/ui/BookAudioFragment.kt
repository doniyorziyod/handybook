package uz.ictschool.handybook.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
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
    lateinit var binding: FragmentBookAudioBinding
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
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
        binding = FragmentBookAudioBinding.inflate(inflater, container, false)

        mySharedPreferences = SharedPreference.newInstance(requireContext())
        val book = mySharedPreferences.getThisBook()
        binding.bookImg.load(book[0].image)
        Log.d("AUDIO", "onCreateView: ${book.get(0)}")
        binding.play.setOnClickListener {
            binding.pause.visibility = View.VISIBLE
            binding.play.visibility = View.GONE
            media.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                if (book[0].audio != null) {
                    media.setDataSource(book[0].audio.toString())
                } else {
                    media.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                }
                media.prepare()
                media.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            initializeSeekBar()
            Toast.makeText(requireContext(), "Audio started playing..", Toast.LENGTH_SHORT).show()
        }
        binding.pause.setOnClickListener {
            binding.play.visibility = View.VISIBLE
            binding.pause.visibility = View.GONE
            media.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                if (book[0].audio != null) {
                    media.setDataSource(book[0].audio.toString())
                } else {
                    media.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                }
                media.prepare()
                media.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            initializeSeekBar()
            Toast.makeText(requireContext(), "Audio stopped playing..", Toast.LENGTH_SHORT).show()
        }
        binding.back.setOnClickListener {
            mySharedPreferences.setThisBook(book)
            parentFragmentManager.beginTransaction().replace(R.id.main, BookViewFragment.newInstance(book[0])).commit()
            media.stop()
        }
        binding.ekitob.setOnClickListener {
            mySharedPreferences.setThisBook(book)
            parentFragmentManager.beginTransaction().replace(R.id.main, BookViewFragment.newInstance(book[0])).commit()
            media.stop()
        }
        return binding.root
    }

    private fun initializeSeekBar() {
        binding.seek.max = media.seconds

        runnable = Runnable {
            binding.seek.progress = media.currentSeconds
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }

    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
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