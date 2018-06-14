package com.example.keviniswara.bookinglapang.login

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.google.firebase.auth.FirebaseAuth

interface RegisterContract {
    interface View  : BaseView<Presenter> {
        fun getEmail(): String
        fun getName(): String
        fun getPhoneNumber(): String
        fun getPassword(): String
        fun getPasswordConfirmation(): String
        fun getAuth(): FirebaseAuth
    }
    interface Presenter : BasePresenter<View> {
        fun register()
    }
}