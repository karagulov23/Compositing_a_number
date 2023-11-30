package orlo.karagulov.compositing_a_number.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import orlo.karagulov.compositing_a_number.R
import orlo.karagulov.compositing_a_number.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}