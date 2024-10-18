package com.zyablik.kotlinpr5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.zyablik.kotlinpr5.databinding.ActivityMainBinding
import com.zyablik.kotlinpr5.model.quote.QuoteDatabase
import com.zyablik.kotlinpr5.retrofit.api.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MyVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vm = ViewModelProvider(this).get(MyVM::class.java)
        vm.quote.observe(this) {
            binding.quotTtext.text = vm.quote.value
        }
        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val quoteMainApi = retrofit.create(MainApi::class.java)

        val db = Room.databaseBuilder(applicationContext, QuoteDatabase::class.java, "quotes_db")
            .build()
        val dao = db.quoteDao()

        binding.button.setOnClickListener() {
            val randId = Random.nextInt(1, 30)

            val result: Deferred<String> = CoroutineScope(Dispatchers.IO).async {
                val quote = quoteMainApi.getQuoteById(randId)
                dao.insertQuote(quote)
                dao.getQbyId(randId).quote
            }

            CoroutineScope(Dispatchers.IO).launch {
                val str = result.await()
                runOnUiThread {
                    vm.updateQuote(str)
                }
            }
        }
    }
}