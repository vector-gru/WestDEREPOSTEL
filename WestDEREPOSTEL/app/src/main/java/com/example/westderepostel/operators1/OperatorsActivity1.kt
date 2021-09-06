package com.example.westderepostel.operators1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.westderepostel.MyOAdapter
import com.example.westderepostel.R
import com.example.westderepostel.models.OperatorInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_operators1.*

class OperatorsActivity1 : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var operatorRecyclerview : RecyclerView
    private lateinit var operatorArrayList : ArrayList<OperatorInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators1)

        setSupportActionBar(idOperators1Toolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        idAddOperatorsBtn1.setOnClickListener {
            startActivity(Intent(this, AddOperatorActivity1::class.java))
        }

        operatorRecyclerview = findViewById(R.id.idOperatorsList1)
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

        dbref = FirebaseDatabase.getInstance().getReference("netSecOperators")

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
                TODO("Not yet implemented")
            }


        })

    }

}