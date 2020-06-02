package com.example.keviniswara.bookinglapang.user.profile

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface EditPasswordContract {
    interface View  : BaseView<Presenter> {
//        fun getName() : String
//        fun getPhoneNumber() : String
//        fun setName(name: String)
          fun getPassword(): String
          fun getNewPassword(): String
          fun getNewPasswordConfirm(): String
          fun moveBack()
//        fun setPhoneNumber(phoneNumber: String)
          fun hideKeyboard()
    }
    interface Presenter : BasePresenter<View> {
          fun passwordAuthentication()
//        fun getProfileFromDatabase()
//        fun save()
    }
}