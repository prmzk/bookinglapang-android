package com.example.keviniswara.bookinglapangtest.user.profile

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface ProfileContract {
    interface View  : BaseView<Presenter> {
        fun getName() : String
        fun getPhoneNumber() : String
        fun setName(name: String)
        fun setPhoneNumber(phoneNumber: String)
        fun startLoginActivity()
        fun hideKeyboard()
    }
    interface Presenter : BasePresenter<View> {
        fun getProfileFromDatabase()
        fun logout()
        fun save()
    }
}