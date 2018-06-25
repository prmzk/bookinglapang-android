package com.example.keviniswara.bookinglapang.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

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