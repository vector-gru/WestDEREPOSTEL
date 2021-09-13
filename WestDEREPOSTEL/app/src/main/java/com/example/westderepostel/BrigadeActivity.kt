package com.example.westderepostel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_brigade.*
import kotlinx.android.synthetic.main.activity_network_security.*
import kotlinx.android.synthetic.main.activity_network_security.idNetworkSecurityToolbar

class BrigadeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brigade)

        setSupportActionBar(idBrigadeToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}