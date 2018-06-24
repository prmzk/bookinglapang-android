package com.example.keviniswara.bookinglapang.status.presenter

import com.example.keviniswara.bookinglapang.status.Payment1Contract

class Payment1Presenter: Payment1Contract.Presenter {

    private var mView: Payment1Contract.View? = null

    override fun bind(view: Payment1Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}