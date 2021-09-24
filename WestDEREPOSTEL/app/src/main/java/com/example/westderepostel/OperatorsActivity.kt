package com.example.westderepostel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.westderepostel.models.OperatorInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_operators.*

class OperatorsActivity : AppCompatActivity(){

    private lateinit var dbref : DatabaseReference
    private lateinit var operatorRecyclerview : RecyclerView
    private lateinit var operatorArrayList : ArrayList<OperatorInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators)

        setSupportActionBar(idOperatorsToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        idAddOperatorsBtn.setOnClickListener {
            startActivity(Intent(this, AddOperatorActivity::class.java))
        }

        operatorRecyclerview = findViewById(R.id.idOperatorsList)
        operatorRecyclerview.layoutManager = LinearLayoutManager(this)
        operatorRecyclerview.setHasFixedSize(true)

        operatorArrayList = arrayListOf<OperatorInfo>()
        getOperatorData()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getOperatorData() {

        dbref = FirebaseDatabase.getInstance().getReference("operators")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (operatorSnapshot in snapshot.children){


                        val operatorInfo = operatorSnapshot.getValue(OperatorInfo::class.java)
                        operatorArrayList.add(operatorInfo!!)

                    }

                    operatorRecyclerview.adapter = MyOAdapter(operatorArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

}