package com.example.keviniswara.bookinglapangtest.user.status.presenter

import com.example.keviniswara.bookinglapangtest.user.status.ActiveOrderDetailContract

class ActiveOrderDetailPresenter : ActiveOrderDetailContract.Presenter {

    private var mView: ActiveOrderDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String,
                                 customerEmail: String, status: String, date: String,
                                 fieldId: String) {

        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")
        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
        mView?.setDate(date)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)

        when (status) {
            "0" -> mView!!.setStatusNotVerified()
            "1" -> mView!!.setStatusNotPaid()
            "3" -> mView!!.setStatusCancelled()
        }
    }

    override fun bind(view: ActiveOrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}