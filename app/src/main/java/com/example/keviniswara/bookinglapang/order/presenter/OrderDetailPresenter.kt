package com.example.keviniswara.bookinglapang.order.presenter

import com.example.keviniswara.bookinglapang.order.OrderDetailContract

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
        mView!!.setDate(date)
        mView!!.setEndHour(endHour)
        mView!!.setFieldId(fieldId)
        mView!!.setSport(sport)
        mView!!.setStartHour(startHour)

        when (status) {
            "0" -> mView!!.setStatusNotVerified()
            "1" -> mView!!.setStatusNotPaid()
            "2" -> mView!!.setStatusDone()
            "3" -> mView!!.setStatusCancelled()
        }
    }
}