package com.example.keviniswara.bookinglapang.home.presenter

import com.example.keviniswara.bookinglapang.home.SearchFieldContract

class SearchFieldPresenter: SearchFieldContract.Presenter {

    private var mView: SearchFieldContract.View? = null

    override fun bind(view: SearchFieldContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

}