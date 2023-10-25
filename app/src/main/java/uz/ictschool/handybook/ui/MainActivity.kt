package uz.ictschool.handybook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.ictschool.handybook.R
import uz.ictschool.handybook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.main,SplashFragment()).commit()
    }
}