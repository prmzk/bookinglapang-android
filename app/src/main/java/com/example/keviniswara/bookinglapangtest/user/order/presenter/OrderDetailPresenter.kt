package com.example.keviniswara.bookinglapangtest.user.order.presenter

import com.example.keviniswara.bookinglapangtest.user.order.OrderDetailContract

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

        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")
        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
        mView?.setDate(date)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
    }
}