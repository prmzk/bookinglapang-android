package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.user.home.JoinGameDetailContract

class JoinGameDetailPresenter : JoinGameDetailContract.Presenter {

    private var mView: JoinGameDetailContract.View? = null

    override fun bind(view: JoinGameDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}