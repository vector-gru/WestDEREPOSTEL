package com.example.westderepostel

//import com.example.westderepostel.javaTodo.activity.MainActivity2
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.westderepostel.databinding.ActivityOperatorHomeBinding
import com.example.westderepostel.models.Operator
import com.example.westderepostel.todolistapp.TodoMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_general_affairs.*
import kotlinx.android.synthetic.main.activity_operator_home.*


class OperatorHome : AppCompatActivity() {

    private lateinit var binding : ActivityOperatorHomeBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var operator: Operator? = null
    private lateinit var oid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOperatorHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(idOperatorHomeToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        oid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("operators")
        if (oid.isNotEmpty()) {

            getUserData()
            //getUserProfile()

        }

        /* schedulesButton.setOnClickListener {
            startActivity(Intent(this, CloudMainActivity::class.java))
        }*/

        idSchedulesBtn.setOnClickListener {
            startActivity(Intent(this, TodoMainActivity::class.java))
        }

        idMessagingBtn.setOnClickListener {
            startActivity(Intent(this, ChatroomActivity::class.java))
        }

        idFloatingBtn.setOnClickListener {
            startActivity(Intent(this, ChatroomActivity::class.java))
        }



    }

    private fun getUserData() {

        databaseReference.child(oid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //getting user name from database
                operator = snapshot.getValue(Operator::class.java)
                binding.idOperatorName.text = operator?.operatorName
                binding.idOperatorActivityTextView.text = operator?.activity

                //getting user profile image from database
                val uri = operator?.logoImageUrl
                Picasso.get().load(uri).into(idLogoImage)

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}