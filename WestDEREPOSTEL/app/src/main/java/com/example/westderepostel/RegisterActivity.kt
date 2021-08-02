package com.example.westderepostel

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Firebase Authentication to create a user with email and password

        //auth = Firebase.auth

        btnLogRegister.setOnClickListener {
            onBackPressed()
        }

        idRequestBtn.setOnClickListener{
            performRegister()

        }

        idSelectPhotoRegistrationBtn.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
            // Proceed and check what the selected image was....
                Log.d("RegisterActivity", "Photo was selected")

        selectedPhotoUri = data?.data

        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

        idSelectPhotoRegistrationImageView.setImageBitmap(bitmap)

        idSelectPhotoRegistrationBtn.alpha = 0f

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      idSelectPhotoRegistrationBtn.setBackgroundDrawable(bitmapDrawable)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }

    private fun performRegister(){
        val email = idEmailRegister.text.toString()
        val code = idCodeRegister.text.toString()

        if (email.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/code", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity", "Email is:" + email)
        Log.d("RegisterActivity", "The code is: $code")

        //Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, code)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("RegisterActivity", "Successfully created user with uid: ${it.result?.user?.uid}")

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
       val ref =FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                //do some logging here
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, idUsernameRegisterText.text.toString(), profileImageUrl)


        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to save the user to the Firebase Database")
            }
    }

}

class User(val uid: String, val username: String, val profileImageUrl: String)