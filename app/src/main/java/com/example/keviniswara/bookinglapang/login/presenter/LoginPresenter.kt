package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.LoginContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId


class LoginPresenter() : LoginContract.Presenter {

    private var mView: LoginContract.View? = null

    override fun bind(view: LoginContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun login() {

        var email = mView?.getEmail()
        var password = mView?.getPassword()

        mView?.hideKeyboard()

        if (email == null || email == "") {
            mView?.setErrorMessage("Email tidak boleh kosong.")
        } else if (password == null || password == "") {
            mView?.setErrorMessage("Password tidak boleh kosong.")
        } else {
            val mAuth = FirebaseAuth.getInstance()

            val addOnCompleteListener: Any = mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener({ task ->
                        if (task.isSuccessful) {

                            val tokenId = FirebaseInstanceId.getInstance().token
                            Database.updateTokenId(tokenId!!)

                            val database: FirebaseDatabase = FirebaseDatabase.getInstance()

                            val usersReference: DatabaseReference = database.getReference("").child("users")
                                    .child(FirebaseAuth.getInstance().currentUser!!.uid)

                            usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(p0: DataSnapshot) {
                                    val status = p0?.child("status")?.getValue(Int::class.java)

                                    if (status != null) {
                                        mView?.moveTo(status)
                                    } else {
                                        mView?.moveTo(-1)
                                    }

                                }

                                override fun onCancelled(p0: DatabaseError) {
                                    mView?.moveTo(-1)
                                }
                            })
                        } else {
                            when {
                                task.exception is FirebaseAuthInvalidCredentialsException -> invalidPasswordErrorMessage()
                                task.exception is FirebaseAuthInvalidUserException -> emailNotRegisteredErrorMessage()
                                else -> mView?.setErrorMessage("Login gagal.")
                            }
                            Log.d("LOGIN ERROR", task.exception.toString())
                        }
                    })
        }
    }

    private fun invalidPasswordErrorMessage() {
        mView?.setErrorMessage("Email dan password tidak cocok.")
    }

    private fun emailNotRegisteredErrorMessage() {
        mView?.setErrorMessage("Email belum terdaftar.")
    }
}