package com.example.keviniswara.bookinglapang.user.status.presenter

import com.example.keviniswara.bookinglapang.user.status.ActiveOrderDetailContract

class ActiveOrderDetailPresenter : ActiveOrderDetailContract.Presenter {

    private var mView: ActiveOrderDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String,
                                 customerEmail: String, status: String, date: String,
                                 fieldId: String) {
        mView?.setDate(date)
        mView?.setEndHour(endHour)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        mView?.setStartHour(startHour)

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