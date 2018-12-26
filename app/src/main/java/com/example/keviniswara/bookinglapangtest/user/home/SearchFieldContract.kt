package com.example.keviniswara.bookinglapangtest.user.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface SearchFieldContract {
    interface View : BaseView<Presenter> {
        fun getFieldName(): String
        fun getSport(): String
        fun getDate(): String
        fun getStartHour(): String
        fun getFinishHour(): String
        fun initDatePicker()
        fun initNumberPicker(title: String, minValue: Int, maxValue: Int, type: Int, array: Array<String>)
        fun initListOfFieldDropdown(listOfField: List<String>)
        fun initListOfSportDropdown(listOfSport: List<String>)
        fun showToastMessage(message: String)
        fun updatePrice(newPrice : String)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfFieldFromFirebase()
        fun retrieveListOfSportFromFirebase(fieldName: String)
        fun addOrderToFirebase()
        fun sendOrderNotificationToFieldKeeper()
        fun getPrice()
    }
}