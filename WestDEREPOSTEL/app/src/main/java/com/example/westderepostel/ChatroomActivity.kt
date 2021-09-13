package com.example.westderepostel

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Item
import android.view.ViewGroup
import com.example.westderepostel.models.ChatMessage
import com.example.westderepostel.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
//import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chatroom.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatroomActivity : AppCompatActivity(){

    companion object {
        val  TAG = "ChatLog"
        var currentUser: User? = null

    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        setSupportActionBar(chatRoomToolbar)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        fetchCurrentUser()

        //setupDummyData()
        listenForMessages()

        idMessageSendBtnChatroom.setOnClickListener {
            Log.d(TAG, "Attempt to send a message.....")
            performSendMessage()
        }

        idRecyclerViewChatRoom.adapter = adapter

    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot){
                currentUser = snapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                    if (chatMessage != null) {
                        Log.d(TAG, chatMessage.text)

                       if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                          // val toUser = intent.getParcelableExtra<com.example.westderepostel.models.User>(UserInfo)
                           //adapter.add(ChatFromItem(chatMessage.text, currentUser))
                           adapter.add(ChatToItem(chatMessage.text))
                       }else {
                           val currentUser = currentUser ?: return
                           adapter.add(ChatFromItem(chatMessage.text, currentUser))
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

        val chatMessage = ChatMessage(reference.key!!, text, fromId,  System.currentTimeMillis() / 1000 )
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
                idEditTextChatRoom.text.clear()
                idRecyclerViewChatRoom.scrollToPosition(adapter.itemCount -1)
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
//Load received messages into the chat room
class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(groupieViewHolder: GroupieViewHolder, position: Int) {
        groupieViewHolder.itemView.idMessageFrom.text = text

        val name = user.username
        groupieViewHolder.itemView.idSenderName.text = name


        val uri = user.profileImageUrl
        val targetImageView = groupieViewHolder.itemView.idUserImageFrom
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

}

//Load user chat in chatroom
class ChatToItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(groupieViewHolder: GroupieViewHolder, position: Int) {
        groupieViewHolder.itemView.idMessageTo.text = text

        //load our user image into the imageView of the chatmessage bubble..
        /*val uri = user.profileImageUrl
        val targetImageView = groupieViewHolder.itemView.idUserImageTo
        Picasso.get().load(uri).into(targetImageView)*/
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}