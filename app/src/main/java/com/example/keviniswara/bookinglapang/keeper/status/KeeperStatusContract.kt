package com.example.keviniswara.bookinglapang.keeper.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

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