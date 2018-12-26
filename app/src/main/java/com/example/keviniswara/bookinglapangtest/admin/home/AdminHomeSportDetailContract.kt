package com.example.keviniswara.bookinglapangtest.admin.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface AdminHomeSportDetailContract {
    interface View : BaseView<Presenter> {
        fun getStartDay() : String
        fun getStartHour() : String
        fun getEndDay() : String
        fun getEndHour() : String
        fun getPrice() : String
        fun getSport() : String
        fun initDayPicker(title: String, type: Int)
        fun initHourPicker(title: String, type: Int, array: Array<String>)
    }
    interface Presenter : BasePresenter<View> {
        fun savePrice(fieldId: String)
    }
}