package com.example.keviniswara.bookinglapang.admin.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminHomeFieldDetailContract {
    interface View : BaseView<Presenter> {
        fun getFieldId() : String
        fun getHandphone() : String
        fun getContactPersonName() : String
        fun getAddress() : String
        fun setFieldId(fieldId: String)
        fun setHandphone(handphone: String)
        fun setContactPersonName(contactPerson : String)
        fun setAddress(address: String)
        fun startSportDetailFragment()
    }
    interface Presenter : BasePresenter<View> {

    }
}