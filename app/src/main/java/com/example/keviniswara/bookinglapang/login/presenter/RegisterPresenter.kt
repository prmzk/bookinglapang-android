package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.RegisterContract

class RegisterPresenter : RegisterContract.Presenter {

    private var mView: RegisterContract.View? = null

    override fun register() {
        var email = mView?.getEmail()
        var name = mView?.getName()
        var phoneNumber = mView?.getPhoneNumber()
        var password = mView?.getPassword()
        var confirmPassword = mView?.getPasswordConfirmation()

        // Register
        Log.d("Register", email + name + phoneNumber + password + confirmPassword)
    }

    override fun bind(view: RegisterContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}