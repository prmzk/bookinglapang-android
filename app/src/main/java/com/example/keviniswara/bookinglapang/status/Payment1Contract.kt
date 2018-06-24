package com.example.keviniswara.bookinglapang.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface Payment1Contract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter<View> {

    }
}