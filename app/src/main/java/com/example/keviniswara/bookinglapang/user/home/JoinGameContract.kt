package com.example.keviniswara.bookinglapang.user.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.FindEnemy

interface JoinGameContract {
    interface View : BaseView<Presenter> {
        fun initListOfJoinGame(joinGameList: MutableList<FindEnemy?>?)
        fun initDatePicker()
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveJoinGameList(date: String, sport: String)
    }
}