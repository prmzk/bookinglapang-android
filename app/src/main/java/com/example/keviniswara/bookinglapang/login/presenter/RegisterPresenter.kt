package com.example.keviniswara.bookinglapang.login.presenter

import android.text.TextUtils
import android.util.Log
import com.example.keviniswara.bookinglapang.login.RegisterContract
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterPresenter : RegisterContract.Presenter {

    private var mView: RegisterContract.View? = null

    override fun register() {
        val email = mView?.getEmail()
//        var name = mView.getName()
//        var phoneNumber = mView.getPhoneNumber()
        val password = mView?.getPassword()
//        var confirmPassword = mView.getPasswordConfirmation()


        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            val emailverified = email.toString()
            val passwordverified = password.toString()
            mView!!.getAuth().createUserWithEmailAndPassword(emailverified, passwordverified).addOnCompleteListener(OnCompleteListener<AuthResult> {
                task ->
                if (task.isSuccessful) {
                    //Add data to database
                } else {
                    Log.d("REGISTER", "Gagal")
                }

            })
        }



    }

    override fun bind(view: RegisterContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}