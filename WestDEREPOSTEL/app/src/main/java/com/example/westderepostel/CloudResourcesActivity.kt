package com.example.westderepostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cloud_resources.*
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.activity_guide.idGuideToolbar

class CloudResourcesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_resources)

        setSupportActionBar(idCloudResourcesToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        idUploadPdfFilesBtn.setOnClickListener {
            startActivity(Intent(this, CloudActivity::class.java))
        }
        idUploadImageFilesBtn.setOnClickListener {
            startActivity(Intent(this, ImageUploadActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}