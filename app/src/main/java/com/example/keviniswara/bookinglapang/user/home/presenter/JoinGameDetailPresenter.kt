package com.example.keviniswara.bookinglapang.user.home.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.user.home.JoinGameDetailContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class JoinGameDetailPresenter : JoinGameDetailContract.Presenter {

    private var mView: JoinGameDetailContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun bind(view: JoinGameDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun initJoinGameDetail(name: String, phoneNumber: String) {
        mView?.setHostName(name)
        mView?.setHostPhoneNumber(phoneNumber)
    }

    override fun retrieveCurrentUserDetail() {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                val name = p0!!.child("name").value.toString()
                val phoneNumber = p0.child("phoneNumber").value.toString()
                mView!!.setVisitorName(name)
                mView!!.setVisitorPhoneNumber(phoneNumber)
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.d("JOIN GAME DETAIL", "Could not retrieve user data from firebase")
            }
        })
    }
}