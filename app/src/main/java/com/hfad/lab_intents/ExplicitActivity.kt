package com.hfad.lab_intents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.lab_intents.viewmodels.TextViewModel

class ExplicitActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var acceptButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit)
        editText= findViewById(R.id.editText_multiLineText1)
        acceptButton=findViewById(R.id.button_accept)
        cancelButton=findViewById(R.id.button_cancel)


        acceptButton.setOnClickListener {
            val currentTextValue = editText.text.toString()
            Intent().let{intent ->
                intent.putExtra("textValue",currentTextValue)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }

        cancelButton.setOnClickListener {
            Intent().let{intent ->
                setResult(Activity.RESULT_CANCELED,intent)
                finish()
            }
        }









    }
}