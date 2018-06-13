package com.example.keviniswara.bookinglapang.login.presenter

import com.example.keviniswara.bookinglapang.login.LoginContract

class LoginPresenter : LoginContract.Presenter{



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

        if (email == null || email == "") {

        } else if (password == null || password == "") {

        } else {

        }
    }
}