package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.LoginContract
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


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
                            mView?.startMainActivity()
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
        mView!!.setErrorMessage("Email dan password tidak cocok.")
    }

    private fun emailNotRegisteredErrorMessage() {
        mView!!.setErrorMessage("Email belum terdaftar.")
    }
}