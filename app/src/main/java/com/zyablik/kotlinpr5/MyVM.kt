package com.zyablik.kotlinpr5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyVM: ViewModel() {
    private val _quote = MutableLiveData<String>()
    val quote: LiveData<String> = _quote

    fun updateQuote(newDQuote: String){
        _quote.value = newDQuote
    }
}