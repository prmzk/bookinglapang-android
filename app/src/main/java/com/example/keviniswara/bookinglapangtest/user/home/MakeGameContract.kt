package com.example.keviniswara.bookinglapangtest.user.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface MakeGameContract {
    interface View : BaseView<Presenter> {
        fun goBack()
        fun getFieldName(): String
        fun getSport(): String
        fun getDate(): String
        fun getTime(): String
        fun initDatePicker()
        fun initNumberPicker(title: String, minValue: Int, maxValue: Int, array: Array<String>)
        fun initListOfFieldDropdown(listOfField: List<String>)
        fun initListOfSportDropdown(listOfSport: List<String>)
        fun showToastMessage(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfFieldFromFirebase(sportName: String)
        fun retrieveListOfSportFromFirebase()
        fun addFindEnemyToFirebase()
    }
}