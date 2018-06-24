package com.example.keviniswara.bookinglapang.status.presenter

import com.example.keviniswara.bookinglapang.status.Payment3Contract

class Payment3Presenter: Payment3Contract.Presenter {

    private var mView: Payment3Contract.View? = null

    override fun bind(view: Payment3Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

}