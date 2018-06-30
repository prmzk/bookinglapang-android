package com.example.keviniswara.bookinglapang.admin.home.presenter

import com.example.keviniswara.bookinglapang.admin.home.AdminHomeSportDetailContract

class AdminHomeSportDetailPresenter : AdminHomeSportDetailContract.Presenter {

    private var mView: AdminHomeSportDetailContract.View? = null

    override fun bind(view: AdminHomeSportDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}