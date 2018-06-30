package com.example.keviniswara.bookinglapang.admin.order.presenter

import com.example.keviniswara.bookinglapang.admin.order.AdminOrderDetailContract

class AdminOrderDetailPresenter : AdminOrderDetailContract.Presenter {

    private var mView: AdminOrderDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String, customerEmail: String,
                                 status: String, date: String, fieldId: String) {
        mView?.setDate(date)
        mView?.setEndHour(endHour)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        mView?.setStartHour(startHour)
    }

    override fun bind(view: AdminOrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}