package com.example.keviniswara.bookinglapang.admin.home.presenter

import com.example.keviniswara.bookinglapang.admin.home.AdminHomeFieldDetailContract

class AdminHomeFieldDetailPresenter : AdminHomeFieldDetailContract.Presenter {

    private var mView: AdminHomeFieldDetailContract.View? = null

    override fun bind(view: AdminHomeFieldDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}