package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.LoginContract
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


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
                            Log.d("LOGIN ERROR", task.exception.toString())
                            mView?.setErrorMessage("Login gagal.")
                        }
                    })
        }
    }
}