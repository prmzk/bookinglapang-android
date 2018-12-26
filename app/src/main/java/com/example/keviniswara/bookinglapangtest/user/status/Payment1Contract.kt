package com.example.keviniswara.bookinglapangtest.user.status

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Order

interface Payment1Contract {
    interface View : BaseView<Presenter> {
        fun getName(): String
        fun getPhoneNumber(): String
        fun moveToPayment2(orderDetail: Order)
    }
    interface Presenter : BasePresenter<View> {
        fun addTransactionToFirebase(orderId: String)
    }
}