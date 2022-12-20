package com.hfad.lab_intents

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.hfad.lab_intents.viewmodels.TextViewModel

class ImplicitActivity : AppCompatActivity() {
    private lateinit var textOfAllMainLaunchableActivitiesInOS:TextView

    private lateinit var textViewModel:TextViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit)
        textOfAllMainLaunchableActivitiesInOS=findViewById(R.id.launchable_activities_in_os)

        textViewModel=ViewModelProvider(this)[TextViewModel::class.java]

        if(textViewModel.getTextValue().isEmpty()){
            val intent = Intent().apply {
                setAction(Intent.ACTION_MAIN)
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val pkgAppsList : MutableList<ResolveInfo?> = packageManager.queryIntentActivities(intent,0)

            val textValue=pkgAppsList.map { resolveInfo ->
                resolveInfo?.activityInfo.toString()
            }.fold(""){ acc, currentInfo ->
                acc + "\n${currentInfo}"
            }
            textViewModel.setTextValue(textValue)
        }

        textOfAllMainLaunchableActivitiesInOS.text=textViewModel.getTextValue()


    }
}