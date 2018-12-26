package com.example.keviniswara.bookinglapangtest.login

import android.widget.TextView
import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.google.firebase.auth.FirebaseAuth

interface RegisterContract {
    interface View  : BaseView<Presenter> {
        fun getEmail(): String
        fun getName(): String
        fun getPhoneNumber(): String
        fun getPassword(): String
        fun getPasswordConfirmation(): String
        fun getAuth(): FirebaseAuth
        fun getErrorMessage(): TextView
        fun startMainActivity()
    }
    interface Presenter : BasePresenter<View> {
        fun register()
    }
}