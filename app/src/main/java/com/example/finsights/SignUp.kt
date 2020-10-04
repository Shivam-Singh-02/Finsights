package com.example.finsights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.amplifyframework.core.Amplify
import com.amplifyframework.predictions.models.LanguageType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.collections.Map

class SignUp : AppCompatActivity() {
    var language = "HINDI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val textUsed = arrayOf(
            signup,
            smsAck,
            onboard
        )
        val hintUsed = arrayOf(
            phoneNo,
            password,
            email,
            age
        )
        changeLanguageEditText(hintUsed)
        changeLanguageTextView(textUsed)
    }
    fun changeLanguageEditText(textList: Array<EditText>) {

        textList.forEach{
            if(it.hint!=null){
                Amplify.Predictions.translateText(
                    it.hint.toString(),
                    LanguageType.ENGLISH,
                    LanguageType.HINDI,
                    { result -> it.hint = result.translatedText },
                    { error -> Log.e("MyAmplifyApp", "Translation failed", error) }
                )
            }
        }

    }

    fun changeLanguageTextView(textList: Array<TextView>) {

        textList.forEach{
            if(it.text!=null){
                Amplify.Predictions.translateText(
                    it.text.toString(),
                    LanguageType.ENGLISH,
                    LanguageType.JAPANESE,
                    { result -> it.text = result.translatedText },
                    { error -> Log.e("MyAmplifyApp", "Translation failed", error) }
                )
            }
        }

    }
}