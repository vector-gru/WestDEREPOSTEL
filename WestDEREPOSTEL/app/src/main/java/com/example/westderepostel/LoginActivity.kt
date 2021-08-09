package com.example.westderepostel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity: AppCompatActivity() {

    lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        btnRegLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }

        btnLoginValidate.setOnClickListener{
            performLogin()
            //showProgressBar()
            //Toast.makeText(this, "Please wait for a while as we check your credentials", Toast.LENGTH_LONG).show()
        }


    }

    private fun performLogin() {
        showProgressBar()
        val email = idEmailLogin.text.toString()
        val code = idCodeLogin.text.toString()

        if (email.isEmpty() || code.isEmpty()) {
            hideProgressBar()
            Toast.makeText(this, "Please enter text in email/code", Toast.LENGTH_SHORT).show()
            return

        }
        idEmailLogin.text?.clear()
        idCodeLogin.text?.clear()

        Log.d("LoginActivity", "Email is:" + email)
        Log.d("LoginActivity", "The code is: $code")

        //Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, code)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                hideProgressBar()
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", "Successfully Logged in user with uid: ${it.result?.user?.uid}")

                val intent = (Intent(this, ServicesActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                hideProgressBar()
                Log.d("LoginActivity", "User Login Failed: ${it.message}")
                Toast.makeText(
                    this,
                    "Failed to login, please try again: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun showProgressBar(){
        dialog = Dialog(this@LoginActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

}