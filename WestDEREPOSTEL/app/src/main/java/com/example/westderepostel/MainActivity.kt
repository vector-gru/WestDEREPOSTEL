package com.example.westderepostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        contentMainServicesBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        content_main_guide_btn.setOnClickListener {
            startActivity(Intent(this, GuideActivity::class.java))
        }

        content_main_oplogin_btn.setOnClickListener {
            startActivity(Intent(this, OperatorVerificationActivity::class.java))
        }

        idWelcomeText.setOnClickListener {

            idWelcomeText.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()

            logoHomeCircular.animate().apply {
                duration = 2000
                rotationXBy(360f)
            }.start()

        }

        logoHomeCircular.setOnClickListener {

            logoHomeCircular.animate().apply {
                duration = 2000
                rotationYBy(360f)
            }.start()

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home,menu)
        return true
    }

}