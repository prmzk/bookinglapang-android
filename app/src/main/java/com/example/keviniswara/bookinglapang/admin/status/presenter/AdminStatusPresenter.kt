package com.example.keviniswara.bookinglapang.admin.status.presenter

import com.example.keviniswara.bookinglapang.admin.status.AdminStatusContract

class AdminStatusPresenter : AdminStatusContract.Presenter {
    private var mView: AdminStatusContract.View? = null

    override fun bind(view: AdminStatusContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

}