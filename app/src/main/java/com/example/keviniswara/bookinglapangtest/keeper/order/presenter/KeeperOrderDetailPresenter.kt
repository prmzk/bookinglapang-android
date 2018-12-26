package com.example.keviniswara.bookinglapangtest.keeper.order.presenter

import com.example.keviniswara.bookinglapangtest.keeper.order.KeeperOrderDetailContract

class KeeperOrderDetailPresenter : KeeperOrderDetailContract.Presenter {

    private var mView: KeeperOrderDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String, customerEmail: String, status: String, date: String, fieldId: String) {
        mView?.setDate(date)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")
    }

    override fun bind(view: KeeperOrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}