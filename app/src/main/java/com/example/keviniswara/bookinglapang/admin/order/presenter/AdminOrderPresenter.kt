package com.example.keviniswara.bookinglapang.admin.order.presenter

import com.example.keviniswara.bookinglapang.admin.order.AdminOrderContract

class AdminOrderPresenter : AdminOrderContract.Presenter {

    private var mView: AdminOrderContract.View? = null

    override fun bind(view: AdminOrderContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}