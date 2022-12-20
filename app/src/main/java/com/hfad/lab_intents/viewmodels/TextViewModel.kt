package com.hfad.lab_intents.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel:ViewModel() {
    private val _text:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    fun getTextValue():String{
        return _text.value?:""
    }
    fun setTextValue(text:String){
        this._text.value=text
    }

    fun getText(): MutableLiveData<String> {
        return this._text
    }
}