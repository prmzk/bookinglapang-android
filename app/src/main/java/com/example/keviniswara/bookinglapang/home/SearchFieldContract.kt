package com.example.keviniswara.bookinglapang.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface SearchFieldContract {
    interface View: BaseView<Presenter> {
        fun getFieldName(): String
        fun getSport(): String
        fun getDate(): String
        fun getStartHour(): String
        fun getFinishHour(): String
        fun initDatePicker()
    }
    interface Presenter: BasePresenter<View> {

    }
}