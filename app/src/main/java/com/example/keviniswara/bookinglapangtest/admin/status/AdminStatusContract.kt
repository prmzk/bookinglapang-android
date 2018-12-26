package com.example.keviniswara.bookinglapangtest.admin.status

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Order

interface AdminStatusContract {
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