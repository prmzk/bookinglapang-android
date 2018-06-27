package com.example.keviniswara.bookinglapang.user.order.presenter

import com.example.keviniswara.bookinglapang.user.order.OrderDetailContract

class OrderDetailPresenter : OrderDetailContract.Presenter{

    private var mView: OrderDetailContract.View? = null

    override fun bind(view: OrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun initOrderDetail(sport: String, startHour: String, endHour: String
                                 , customerEmail: String, status: String, date: String
                                 , fieldId: String) {
        mView?.setDate(date)
        mView?.setEndHour(endHour)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        mView?.setStartHour(startHour)
    }
}