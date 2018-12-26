package com.example.keviniswara.bookinglapangtest.keeper.profile.presenter

import android.util.Log
import com.example.keviniswara.bookinglapangtest.keeper.profile.KeeperProfileContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class KeeperProfilePresenter : KeeperProfileContract.Presenter {

    private var mView: KeeperProfileContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val mAuth =  FirebaseAuth.getInstance()

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun getProfileFromDatabase() {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val keeperName = p0?.child("name")?.value.toString()
                val fieldName = p0?.child("field")?.value.toString()
                val phoneNumber = p0?.child("phoneNumber")?.value.toString()
                mView?.setKeeperName(keeperName)
                mView?.setFieldName(fieldName)
                mView?.setPhoneNumber(phoneNumber)
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
            mView?.startLoginActivity()
        })
    }

    override fun save() {
        usersReference.child("name").setValue(mView?.getKeeperName())
        usersReference.child("field").setValue(mView?.getFieldName())
        usersReference.child("phoneNumber").setValue(mView?.getPhoneNumber())
        mView?.hideKeyboard()
    }

    override fun bind(view: KeeperProfileContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}