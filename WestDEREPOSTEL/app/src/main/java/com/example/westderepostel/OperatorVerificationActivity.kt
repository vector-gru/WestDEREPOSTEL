package com.example.westderepostel

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_operator_verification.*


class OperatorVerificationActivity : AppCompatActivity() {

    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator_verification)

        idCheckInOperatorBtn.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        showProgressBar()
        val email = idOperatorEmailEditText.text.toString()
        val accessCode = idOperatorVerificationPassword.text.toString()

        if (email.isEmpty() || accessCode.isEmpty()) {
            hideProgressBar()
            Toast.makeText(this, "Please enter text in email/code", Toast.LENGTH_SHORT).show()
            return

        }
        idOperatorEmailEditText.text?.clear()
        idOperatorVerificationPassword.text?.clear()

        Log.d("LoginActivity", "Email is:$email")
        Log.d("LoginActivity", "The code is: $accessCode")

        //Firebase Authentication to create a user with email and accessCode
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, accessCode)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // if successful
                hideProgressBar()
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", "Successfully Logged in user with uid: ${it.result?.user?.uid}")

                val intent = (Intent(this, OperatorHome::class.java))
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
        dialog = Dialog(this@OperatorVerificationActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

}