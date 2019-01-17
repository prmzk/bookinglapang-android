package com.example.keviniswara.bookinglapang.user.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

class StatusContract {
    interface View  : BaseView<Presenter> {
        fun initListOfOrders(orders: MutableList<Order?>?)
        fun moveToDetail(orderDetail: Order)
        fun clearOrderList()
        fun makeToast(text: String)
        fun refresh()
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveOrderList()
    }
}