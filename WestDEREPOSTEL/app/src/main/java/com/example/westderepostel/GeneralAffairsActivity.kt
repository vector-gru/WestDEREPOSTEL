package com.example.westderepostel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_general_affairs.*


class GeneralAffairsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_affairs)

        setSupportActionBar(general_affairs_toolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        chatRoomButton.setOnClickListener {
            startActivity(Intent(this, ChatroomActivity::class.java))
        }

        operatorsButton.setOnClickListener {
            startActivity(Intent(this, OperatorsActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_general_affairs_menu,menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}