package com.example.keviniswara.bookinglapangtest.keeper.status

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Order

interface KeeperStatusContract {
    interface View  : BaseView<Presenter> {
        fun initListOfOrders(orders: MutableList<Order?>?)
        fun moveToDetail(orderDetail: Order)
        fun clearOrderList()
        fun makeToast(text: String)
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveOrderList()
    }
}