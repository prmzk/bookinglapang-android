package com.example.keviniswara.bookinglapangtest.user.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.FindEnemy

interface JoinGameContract {
    interface View : BaseView<Presenter> {
        fun initListOfJoinGame(joinGameList: MutableList<FindEnemy?>?)
        fun initDatePicker()
        fun initListOfSportDropdown(listOfSport: List<String>)
        fun showToastMessage(message: String)
        fun moveToDetail(findEnemy: FindEnemy)
        fun getSport(): String
        fun getDate(): String
        fun clearOrderList()
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveJoinGameList(date: String, sport: String)
        fun retrieveListOfSportFromFirebase()
    }
}