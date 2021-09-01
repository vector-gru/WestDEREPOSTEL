package com.example.westderepostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.westderepostel.cloudsave.CloudMainActivity
//import com.example.westderepostel.javaTodo.activity.MainActivity2
import kotlinx.android.synthetic.main.activity_operator_home.*


class OperatorHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator_home)

        setSupportActionBar(idOperatorHomeToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        /* schedulesButton.setOnClickListener {
            startActivity(Intent(this, CloudMainActivity::class.java))
        }*/



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}