package com.example.keviniswara.bookinglapang.admin.home.presenter

import com.example.keviniswara.bookinglapang.admin.home.AdminHomeContract

class AdminHomePresenter: AdminHomeContract.Presenter {

    private var mView: AdminHomeContract.View? = null

    override fun bind(view: AdminHomeContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}