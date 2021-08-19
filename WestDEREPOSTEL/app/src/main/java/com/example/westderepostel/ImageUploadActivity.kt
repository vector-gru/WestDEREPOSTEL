package com.example.westderepostel

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.westderepostel.databinding.ActivityImageUploadBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_cloud_resources.*
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class ImageUploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageUploadBinding
    lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageUploadBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(idCloudResourcesToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        /*idUploadPdfFilesBtn.setOnClickListener {
            startActivity(Intent(this, ImageUploadActivity::class.java))
        }*/

        binding.idSelectImageBtn.setOnClickListener {
            selectImage()
        }

        binding.idUploadImageBtn.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File ...")
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("imageUploads/$fileName")

        storageReference.putFile(ImageUri).
                addOnSuccessListener {

                    binding.idFirebaseImage.setImageURI(null)
                    Toast.makeText(this@ImageUploadActivity, "Successfully uploaded",Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing)progressDialog.dismiss()

                }.addOnFailureListener{
                    if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@ImageUploadActivity, "Upload failed",Toast.LENGTH_SHORT).show()
        }

    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {

            ImageUri = data?.data!!
            binding.idFirebaseImage.setImageURI(ImageUri)

        }
    }

}