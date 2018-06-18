package com.example.keviniswara.bookinglapang.order

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

interface OrderContact {
    interface View  : BaseView<Presenter> {
        fun initListOfOrders(orders: MutableList<Order?>?)
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveOrderList()
    }
}