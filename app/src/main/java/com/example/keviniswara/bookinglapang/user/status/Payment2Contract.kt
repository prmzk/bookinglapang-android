package com.example.keviniswara.bookinglapang.user.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

interface Payment2Contract {
    interface View : BaseView<Presenter> {
        fun setField(fieldName: String)
        fun setSport(sportName: String)
        fun setDate(date: String)
        fun getBankName(): String
        fun initListOfBankDropdown(listOfBank: List<String>)
        fun moveToPayment3(orderDetail: Order, bankName: String)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfBankFromFirebase()
    }
}