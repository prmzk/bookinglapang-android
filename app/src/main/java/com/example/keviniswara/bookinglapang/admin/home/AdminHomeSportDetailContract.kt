package com.example.keviniswara.bookinglapang.admin.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminHomeSportDetailContract {
    interface View : BaseView<Presenter> {
        fun getStartDay() : String
        fun getStartHour() : String
        fun getEndDay() : String
        fun getEndHour() : String
        fun getPrice() : String
        fun getSport() : String
        fun initDayPicker(title: String, type: Int)
        fun initHourPicker(title: String, type: Int)
    }
    interface Presenter : BasePresenter<View> {
        fun savePrice(fieldId: String)
    }
}