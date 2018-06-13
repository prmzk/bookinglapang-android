package com.example.keviniswara.bookinglapang.login

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface RegisterContract {
    interface View  : BaseView<Presenter> {
        fun getEmail(): String
        fun getName(): String
        fun getPhoneNumber(): String
        fun getPassword(): String
        fun getPasswordConfirmation(): String
    }
    interface Presenter : BasePresenter<View> {
        fun register()
    }
}