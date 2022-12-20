package com.hfad.lab_intents

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.hfad.lab_intents.viewmodels.TextViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var btnGoToExplicitActivity: Button
    private lateinit var textReturnByExplicitActivity: TextView
    private lateinit var btnGoToImplicitActivityInnerActivity: Button
    private lateinit var btnGoToImplicitActivitySelectingPhoto:Button
    private lateinit var btnGoToImplicitActivitySharing:Button

    private lateinit var textViewModel: TextViewModel
    var resultLauncherTextValue = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data:Intent? = result.data
            textViewModel.setTextValue(data?.getStringExtra("textValue")?:"")
        }
    }
    var resultLauncherPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode==Activity.RESULT_OK){
            val data:Intent? = result.data
            val uri = data?.data

            uri?.let {
                val intent = Intent(Intent.ACTION_VIEW,uri)
                val share = Intent.createChooser(intent, null)
                startActivity(share)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGoToExplicitActivity =findViewById(R.id.button_explicit_intent)
        textReturnByExplicitActivity=findViewById(R.id.text_view1)
        btnGoToImplicitActivityInnerActivity=findViewById(R.id.button_implicit_intent_inner_activity)
        btnGoToImplicitActivitySelectingPhoto=findViewById(R.id.button_implicit_intent_outer_activity1)
        btnGoToImplicitActivitySharing=findViewById(R.id.button_implicit_intent_outer_activity2)

        textViewModel = ViewModelProvider(this)[TextViewModel::class.java]
        textReturnByExplicitActivity.setText(textViewModel.getTextValue())

        btnGoToExplicitActivity.setOnClickListener {
            val intent =Intent(this,ExplicitActivity::class.java)
            resultLauncherTextValue.launch(intent)
        }

        btnGoToImplicitActivityInnerActivity.setOnClickListener {
            Intent().apply {
               action="mk.ukim.finki.mpip.IMPLICIT_ACTION"
                type ="text/plain"
            }.let{ intent ->
                startActivity(Intent.createChooser(intent,"Choose an app for your intent"))
            }
        }

        btnGoToImplicitActivitySharing.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type ="text/plain"
                putExtra(Intent.EXTRA_TITLE, "MPiP Send Title")
                putExtra(Intent.EXTRA_TEXT, "Content send from MainActivity")
            }
            val share = Intent.createChooser(intent, null)
            startActivity(share)
        }

        btnGoToImplicitActivitySelectingPhoto.setOnClickListener {
            val intent = Intent().apply{
                action=Intent.ACTION_GET_CONTENT
                type="image/*"
            }
            resultLauncherPhoto.launch(Intent.createChooser(intent, null))
        }

        textViewModel.getText().observe(this){
            textReturnByExplicitActivity.text=textViewModel.getTextValue()
        }

    }
}