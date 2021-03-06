package com.example.finsights

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.predictions.models.LanguageType
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val  requestReadSms : Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Amplify.Predictions.translateText(
            "Bastard",
            LanguageType.ENGLISH,
            LanguageType.HINDI,
            { result -> viewSms.text = result.translatedText },
            { error -> Log.e("MyAmplifyApp", "Translation failed", error) }
        )

        changeLanguage(arrayOf("Awesome","Stupid","Idiot"))

        button_read.setOnClickListener{
            GlobalScope.launch { // launch a new coroutine in background and continue
                setSmsMessage()
                println("World!")
            }
            println("Hello,") // main thread continues here immediately
        }
    }

    private suspend fun setSmsMessage() {
        val sms = SmsAnalysis()
        var filteredSmsData:ArrayList<ExpenseData>
        val smsList =   ArrayList<SmsData>()
        var count : Int = 0;

        val cursor = contentResolver.query(
            Uri.parse("content://sms/inbox"),
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                val nameId = cursor.getColumnIndex("address")
                val messageId = cursor.getColumnIndex("body")
                val dateId = cursor.getColumnIndex("date")

                do {
                    val dateString = cursor.getString(dateId)
                    smsList.add(
                        SmsData(
                            cursor.getString(nameId),
                            Date(dateString.toLong()).toString(), cursor.getString(messageId)
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()

            filteredSmsData = sms.getData(smsList)
            Log.d("FILTERED DATA",filteredSmsData.toString())
            viewSms.setText(filteredSmsData[0].storeName)
        }
        Log.d("Size------------------------------", smsList.size.toString())

    }

    fun changeLanguage(textList: Array<String>) {

        textList.forEach{
            Amplify.Predictions.translateText(
                it,
                LanguageType.ENGLISH,
                LanguageType.HINDI,
                { result -> viewSms.text = result.translatedText },
                { error -> Log.e("MyAmplifyApp", "Translation failed", error) }
            )
        }

    }


}