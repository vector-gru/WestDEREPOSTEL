package com.example.westderepostel

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.example.westderepostel.models.Operator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class AddOperatorActivity : AppCompatActivity() {

    lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_operator)

        idSaveOperatorBtn.setOnClickListener {
            performOperatorRegister()
        }

        idSelectPhotoAddOperatorBtn.setOnClickListener {
            Log.d("AddOperatorActivity", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }

    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        // Proceed and check that the selected image was....
            Log.d("AddOperatorActivity", "Photo was selected")

        selectedPhotoUri = data?.data

        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

        idSelectPhotoAddOperatorImageView.setImageBitmap(bitmap)

        idSelectPhotoAddOperatorBtn.alpha = 0f

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      idSelectPhotoRegistrationBtn.setBackgroundDrawable(bitmapDrawable)

    }

    private fun performOperatorRegister(){
        val email        = idOperatorEmailEditText.text.toString()
        val accessCode   = idAccessCodeEditText.text.toString()
        val operatorName = idOperatorNameEditText.text.toString()
        val activity     = idActivityEditText.text.toString()
        val location     = idLocationEditText.text.toString()
        val phone        = idOperatorPhoneEditText.text.toString()

        if (email.isEmpty() || accessCode.isEmpty() || operatorName.isEmpty() || activity.isEmpty() || location.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields correctly", Toast.LENGTH_SHORT).show()
            return
        }
        showProgressBar()

        Toast.makeText(this, "Adding new operator in the system!!", Toast.LENGTH_LONG).show()

        Log.d("AddOperatorActivity", "Email is: $email")
        Log.d("AddOperatorActivity", "The code is: $accessCode")

        //Firebase Authentication to create an operator with email and accessCode
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, accessCode)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                hideProgressBar()
                Log.d("AddOperatorActivity", "Successfully added Operator with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Account ${it.result?.user} successfully added.", Toast.LENGTH_LONG).show()

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                hideProgressBar()
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveOperatorToFirebaseDatabase(logoImageUrl: String) {
        val oid          = FirebaseAuth.getInstance().uid ?: ""
        val ref          = FirebaseDatabase.getInstance().getReference("/operators/$oid")
        val operatorName = idOperatorNameEditText.text.toString()
        val activity     = idActivityEditText.text.toString()
        val location     = idLocationEditText.text.toString()
        val accessCode   = idAccessCodeEditText.text.toString()
        val email        = idOperatorEmailEditText.text.toString()
        val phone        = idOperatorPhoneEditText.text.toString()

        val operator = Operator(oid, operatorName, logoImageUrl, activity, location, accessCode, email,phone)


        ref.setValue(operator)
            .addOnSuccessListener {
                idOperatorEmailEditText.text?.clear()
                idAccessCodeEditText.text?.clear()
                idOperatorNameEditText.text?.clear()
                idActivityEditText.text?.clear()
                idLocationEditText.text?.clear()
                idOperatorPhoneEditText.text?.clear()
                Log.d("AddOperatorActivity", "Finally we saved the operator to Firebase Database")

                val intent = (Intent(this, OperatorsActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Log.d("AddOperatorActivity", "Failed to save the operator to the Firebase Database")
            }
    }

    private fun showProgressBar() {
        dialog = Dialog(this@AddOperatorActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/operatorLogos/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("AddOperatorActivity", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("AddOperatorActivity", "File Location: $it")

                    saveOperatorToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                //do some logging here
            }
    }

}