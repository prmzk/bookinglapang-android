package com.example.keviniswara.bookinglapang.admin.profile.presenter

import com.example.keviniswara.bookinglapang.admin.profile.AdminProfileContract

class AdminProfilePresenter: AdminProfileContract.Presenter {
    private var mView: AdminProfileContract.View? = null

    override fun bind(view: AdminProfileContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}