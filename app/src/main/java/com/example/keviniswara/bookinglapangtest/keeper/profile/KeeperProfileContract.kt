package com.example.keviniswara.bookinglapangtest.keeper.profile

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface KeeperProfileContract {
    interface View  : BaseView<Presenter> {
        fun getKeeperName() : String
        fun getFieldName() : String
        fun getPhoneNumber() : String
        fun setKeeperName(name: String)
        fun setFieldName(name: String)
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