package com.example.westderepostel

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import coil.api.load
import com.example.westderepostel.databinding.ActivityGeneralAffairsBinding
import com.example.westderepostel.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_general_affairs.*
import kotlinx.android.synthetic.main.activity_general_affairs.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import java.io.File
import java.util.*


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
                TODO("Not yet implemented")
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

