package com.example.keviniswara.bookinglapang.user.profile.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.user.profile.EditPasswordContract
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

    override fun passwordAuthentication() {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var email = p0.child("email").value.toString()
                var password = mView?.getPassword()
                var newPassword = mView?.getNewPassword()
                var newPasswordConfirm = mView?.getNewPasswordConfirm()

                mView?.hideKeyboard()

                if(password !=null && password != ""){
                    val mAuth = FirebaseAuth.getInstance()
                    val user = mAuth.currentUser

                    val addOnCompleteListener: Any = mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener({ task ->
                                if(task.isSuccessful){
                                    if (newPassword != null) {
                                        if(newPassword == newPasswordConfirm){
                                            user!!.updatePassword(newPassword)
                                                    .addOnCompleteListener({ task -> mView?.moveBack()})
                                        }
                                    }
                                }
                            })
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("PROFILE", "Could not retrieve user data from firebase")
            }
        })
    }

//    override fun save() {
//        usersReference.child("name").setValue(mView!!.getName())
//        usersReference.child("phoneNumber").setValue(mView!!.getPhoneNumber())
//        mView!!.hideKeyboard()
//    }
}