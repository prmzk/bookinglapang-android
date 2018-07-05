package com.example.keviniswara.bookinglapang.admin.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Field

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
        fun startSportDetailFragment()
        fun initListOfSport(sports: MutableList<Field.PriceTimeDayRange?>?)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveSportList(sport: String)
    }
}