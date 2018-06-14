package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.RegisterContract

class RegisterPresenter : RegisterContract.Presenter {

    private var mView: RegisterContract.View? = null

    override fun register() {
        val email = mView?.getEmail()
        val name = mView?.getName()
        val phoneNumber = mView?.getPhoneNumber()
        val password = mView?.getPassword()
        val confirmPassword = mView?.getPasswordConfirmation()

        when {
            email.isNullOrEmpty() -> nullErrorMessage("Email")
            name.isNullOrEmpty() -> nullErrorMessage("Nama")
            phoneNumber.isNullOrEmpty() -> nullErrorMessage("No. telepon")
            password.isNullOrEmpty() -> nullErrorMessage("Password")
            confirmPassword.isNullOrEmpty() -> nullErrorMessage("Confirm password")
            password != confirmPassword -> confirmPasswordNotMatchErrorMessage()
        }

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !name.isNullOrEmpty() &&
                !phoneNumber.isNullOrEmpty() && !confirmPassword.isNullOrEmpty() &&
                password == confirmPassword) {
            mView!!.getAuth().createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener({ task ->
                        if (task.isSuccessful) {
                            //Add data to database
                        } else {
                            Log.d("REGISTER", "Gagal")
                        }

                    })
        }
    }

    private fun confirmPasswordNotMatchErrorMessage() {
        mView!!.getErrorMessage().text = "Password dan confirm password tidak cocok"
    }

    private fun nullErrorMessage(content: String) {
        mView!!.getErrorMessage().text = content + " wajib diisi"
    }

    override fun bind(view: RegisterContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}