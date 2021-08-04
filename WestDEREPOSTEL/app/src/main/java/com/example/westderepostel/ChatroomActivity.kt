package com.example.westderepostel

import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Item
import android.view.ViewGroup
import com.example.westderepostel.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupieViewHolder
//import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chatroom.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatroomActivity : AppCompatActivity(){

    companion object {
        val  TAG = "ChatLog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        setSupportActionBar(chatRoomToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //setupDummyData()
        listenForMessages()

        idMessageSendBtnChatroom.setOnClickListener {
            Log.d(TAG, "Attempt to send a message.....")
            performSendMessage()
        }

        idRecyclerViewChatRoom.adapter = adapter

    }

    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
              val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    chatMessage.let { Log.d(TAG, it.text) }

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatToItem(chatMessage.text))
                    }else {
                        adapter.add(ChatFromItem(chatMessage.text))
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })

    }


    private fun performSendMessage() {
        // How do we actually send a message to firebase...?

        val text = idEditTextChatRoom.text.toString()

        val fromId = FirebaseAuth.getInstance().uid ?: return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, System.currentTimeMillis() / 1000 )
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
            }

    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem("FROM MESSSSSSSSAAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTO MESSSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSSAAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTO MESSSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSSAAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTO MESSSAGE"))


        idRecyclerViewChatRoom.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
class ChatFromItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(groupieViewHolder: GroupieViewHolder, position: Int) {
        groupieViewHolder.itemView.idMessageFrom.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

}

class ChatToItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(groupieViewHolder: GroupieViewHolder, position: Int) {
        groupieViewHolder.itemView.idMessageTo.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}