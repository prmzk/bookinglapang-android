package com.example.keviniswara.bookinglapangtest.user.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface JoinGameDetailContract {
    interface View : BaseView<Presenter> {
        fun setHostName(hostName: String)
        fun setHostPhoneNumber(phoneNumber: String)
        fun setVisitorName(name: String)
        fun setVisitorPhoneNumber(phoneNumber: String)
        fun showToastMessage(message: String)
        fun hideCancelButton()
        fun setupCancelButton()
        fun goBackTwoTimes()
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveCurrentUserDetail()
        fun checkCurrentFindEnemy(hostEmail: String)
        fun deleteFindEnemy(findEnemyId: String)
    }
}