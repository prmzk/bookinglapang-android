package com.example.keviniswara.bookinglapang.user.profile

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface EditProfileContract {
    interface View  : BaseView<Presenter> {
        fun getName() : String
        fun getPhoneNumber() : String
        fun setName(name: String)
        fun setPhoneNumber(phoneNumber: String)
        fun hideKeyboard()
    }
    interface Presenter : BasePresenter<View> {
        fun getProfileFromDatabase()
        fun save()
    }
}