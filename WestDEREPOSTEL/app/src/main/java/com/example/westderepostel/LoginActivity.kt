package com.example.westderepostel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }

        btnLoginValidate.setOnClickListener{

            val email = idEmailLogin.text.toString()
            val code = idCodeLogin.text.toString()

            Log.d("LoginActivity", "Email is:" + email)
            Log.d("LoginActivity", "The code is: $code")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, code)
//                .addOnCompleteListener()
//                .add

            //startActivity(Intent(this, ServicesActivity::class.java))
        }


    }

}