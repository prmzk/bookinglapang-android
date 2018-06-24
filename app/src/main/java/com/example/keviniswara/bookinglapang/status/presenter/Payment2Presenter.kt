package com.example.keviniswara.bookinglapang.status.presenter

import com.example.keviniswara.bookinglapang.status.Payment2Contract

class Payment2Presenter: Payment2Contract.Presenter {

    private var mView: Payment2Contract.View? = null

    override fun bind(view: Payment2Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}