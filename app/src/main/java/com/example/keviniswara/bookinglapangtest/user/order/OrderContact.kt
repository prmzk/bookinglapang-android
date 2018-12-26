package com.example.keviniswara.bookinglapangtest.user.order

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Order

interface OrderContact {
    interface View  : BaseView<Presenter> {
        fun initListOfOrders(orders: MutableList<Order?>?)
        fun moveToDetail(orderDetail: Order)
        fun updateStartLabel()
        fun updateEndLabel()
        fun initDatePicker(code: String)
        fun clearOrderList()
        fun makeToast(text: String)
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveOrderList(start: String, end: String)
    }
}