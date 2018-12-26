package com.example.keviniswara.bookinglapangtest.user.profile.presenter

import android.util.Log
import com.example.keviniswara.bookinglapangtest.user.profile.ProfileContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilePresenter : ProfileContract.Presenter {

    private var mView: ProfileContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val mAuth =  FirebaseAuth.getInstance()

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun bind(view: ProfileContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun getProfileFromDatabase() {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val name = p0!!.child("name").value.toString()
                val phoneNumber = p0.child("phoneNumber").value.toString()
                mView!!.setName(name)
                mView!!.setPhoneNumber(phoneNumber)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("PROFILE", "Could not retrieve user data from firebase")
            }
        })
    }

    override fun logout() {
        val tokenUpdate = HashMap<String, Any>()
        tokenUpdate["tokenId"] = ""

        usersReference.updateChildren(tokenUpdate).addOnCompleteListener({
            mAuth.signOut()
            mView!!.startLoginActivity()
        })
    }

    override fun save() {
        usersReference.child("name").setValue(mView!!.getName())
        usersReference.child("phoneNumber").setValue(mView!!.getPhoneNumber())
        mView!!.hideKeyboard()
    }
}