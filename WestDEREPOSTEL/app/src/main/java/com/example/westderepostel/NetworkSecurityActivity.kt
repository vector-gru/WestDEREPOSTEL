package com.example.westderepostel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.westderepostel.cloudsave1.Cloud1MainActivity
import com.example.westderepostel.databinding.ActivityNetworkSecurityBinding
import com.example.westderepostel.models.User
import com.example.westderepostel.operators1.OperatorsActivity1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_general_affairs.*
import kotlinx.android.synthetic.main.activity_network_security.*

class NetworkSecurityActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNetworkSecurityBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var user: User? = null
    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNetworkSecurityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(idNetworkSecurityToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()) {

            getUserData()
            //getUserProfile()

        }

        idChatRoomBtn1.setOnClickListener {
            startActivity(Intent(this, ChatroomActivity::class.java))
        }

        idCloudFilesButton1.setOnClickListener {
            startActivity(Intent(this, Cloud1MainActivity::class.java))
        }

        idOperatorsBtn1.setOnClickListener {
            startActivity(Intent(this, OperatorsActivity1::class.java))
        }

        idSchedulesBtn1.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

    }

    private fun getUserData() {

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //getting user name from database
                user = snapshot.getValue(User::class.java)
                binding.idProfileName1.text = user?.username

                //getting user profile image from database
                val uri = user?.profileImageUrl
                Picasso.get().load(uri).into(idProfileImageNetSec)

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.idNetworkSecurityToolbar,menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}