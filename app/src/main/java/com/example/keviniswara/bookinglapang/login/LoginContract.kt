package com.example.keviniswara.bookinglapang.login

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface LoginContract {
    interface View  : BaseView<Presenter>{
        fun getEmail(): String
        fun getPassword(): String
    }
    interface Presenter : BasePresenter<View> {
        fun login()
    }
}