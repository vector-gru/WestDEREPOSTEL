package com.example.westderepostel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.westderepostel.databinding.ActivityGeneralAffairsBinding
import com.example.westderepostel.models.User
import com.example.westderepostel.todolistapp.TodoMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_general_affairs.*


class GeneralAffairsActivity : AppCompatActivity(){

    private lateinit var binding : ActivityGeneralAffairsBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var user: User? = null
    private lateinit var uid : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGeneralAffairsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(general_affairs_toolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()) {

            getUserData()
            //getUserProfile()

        }

        chatRoomButton.setOnClickListener {
            startActivity(Intent(this, ChatroomActivity::class.java))
        }

        operatorsButton.setOnClickListener {
            startActivity(Intent(this, OperatorsActivity::class.java))
        }

        schedulesButton.setOnClickListener {
            startActivity(Intent(this, TodoMainActivity::class.java))
        }

        cloudFilesButton.setOnClickListener {
            startActivity(Intent(this, CloudResourcesActivity::class.java))
        }

    }

    private fun getUserData() {

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                //getting user name from database
                user = snapshot.getValue(User::class.java)
                binding.idProfileName.text = user?.username

                //getting user profile image from database
                val uri = user?.profileImageUrl
                Picasso.get().load(uri).into(idProfileImageGenAff)

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

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

