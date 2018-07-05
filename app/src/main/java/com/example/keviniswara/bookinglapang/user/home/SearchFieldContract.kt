package com.example.keviniswara.bookinglapang.user.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface SearchFieldContract {
    interface View : BaseView<Presenter> {
        fun getFieldName(): String
        fun getSport(): String
        fun getDate(): String
        fun getStartHour(): String
        fun getFinishHour(): String
        fun initDatePicker()
        fun initNumberPicker(title: String, minValue: Int, maxValue: Int, type: Int)
        fun initListOfFieldDropdown(listOfField: List<String>)
        fun initListOfSportDropdown(listOfSport: List<String>)
        fun showToastMessage(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfFieldFromFirebase()
        fun retrieveListOfSportFromFirebase(fieldName: String)
        fun addOrderToFirebase()
        fun sendOrderNotificationToFieldKeeper()
    }
}