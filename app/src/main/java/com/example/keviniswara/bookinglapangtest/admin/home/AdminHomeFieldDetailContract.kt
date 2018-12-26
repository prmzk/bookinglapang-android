package com.example.keviniswara.bookinglapangtest.admin.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Price

interface AdminHomeFieldDetailContract {
    interface View : BaseView<Presenter> {
        fun getFieldId(): String
        fun getHandphone(): String
        fun getContactPersonName(): String
        fun getAddress(): String
        fun setFieldId(fieldId: String)
        fun setHandphone(handphone: String)
        fun setContactPersonName(contactPerson: String)
        fun setAddress(address: String)
        fun startSportDetailFragment(fieldId: String)
        fun initListOfSport(sports: MutableList<Price?>?)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveSportList(fieldId: String)
    }
}