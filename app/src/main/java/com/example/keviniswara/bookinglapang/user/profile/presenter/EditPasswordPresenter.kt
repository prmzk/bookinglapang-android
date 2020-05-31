package com.example.keviniswara.bookinglapang.user.profile.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.user.profile.EditPasswordContract
import com.example.keviniswara.bookinglapang.user.profile.EditProfileContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditPasswordPresenter : EditPasswordContract.Presenter {

    private var mView: EditPasswordContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val mAuth =  FirebaseAuth.getInstance()

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun bind(view: EditPasswordContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

//    override fun getProfileFromDatabase() {
//        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//                val name = p0!!.child("name").value.toString()
//                val phoneNumber = p0.child("phoneNumber").value.toString()
//                mView!!.setName(name)
//                mView!!.setPhoneNumber(phoneNumber)
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//                Log.d("PROFILE", "Could not retrieve user data from firebase")
//            }
//        })
//    }

//    override fun save() {
//        usersReference.child("name").setValue(mView!!.getName())
//        usersReference.child("phoneNumber").setValue(mView!!.getPhoneNumber())
//        mView!!.hideKeyboard()
//    }
}