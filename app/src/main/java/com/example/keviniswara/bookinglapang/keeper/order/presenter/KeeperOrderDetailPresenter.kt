package com.example.keviniswara.bookinglapang.keeper.order.presenter

import com.example.keviniswara.bookinglapang.keeper.order.KeeperOrderDetailContract

class KeeperOrderDetailPresenter : KeeperOrderDetailContract.Presenter {

    private var mView: KeeperOrderDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String, customerEmail: String, status: String, date: String, fieldId: String) {
        mView?.setDate(date)
        mView?.setEndHour(endHour)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        mView?.setStartHour(startHour)
    }

    override fun bind(view: KeeperOrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}