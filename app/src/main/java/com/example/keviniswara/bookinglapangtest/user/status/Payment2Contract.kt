package com.example.keviniswara.bookinglapangtest.user.status

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Order

interface Payment2Contract {
    interface View : BaseView<Presenter> {
        fun setField(fieldName: String)
        fun setSport(sportName: String)
        fun setDate(date: String)
        fun getBankName(): String
        fun setEndTime(endTime: String)
        fun setBeginTime(beginTime: String)
        fun initListOfBankDropdown(listOfBank: List<String>)
        fun moveToPayment3(orderDetail: Order, bankName: String)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfBankFromFirebase()
    }
}