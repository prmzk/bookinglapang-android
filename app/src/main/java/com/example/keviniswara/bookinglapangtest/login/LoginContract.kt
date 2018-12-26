package com.example.keviniswara.bookinglapangtest.login

import android.app.Activity
import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface LoginContract {
    interface View  : BaseView<Presenter>{
        fun getEmail(): String
        fun getPassword(): String
        fun getActivity(): Activity
        fun setErrorMessage(text: String)
        fun moveTo(status: Int)
        fun hideKeyboard()
    }
    interface Presenter : BasePresenter<View> {
        fun login()
    }
}