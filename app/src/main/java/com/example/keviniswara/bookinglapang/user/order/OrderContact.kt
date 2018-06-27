package com.example.keviniswara.bookinglapang.user.order

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

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