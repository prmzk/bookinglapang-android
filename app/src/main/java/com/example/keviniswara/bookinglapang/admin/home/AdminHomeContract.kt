package com.example.keviniswara.bookinglapang.admin.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminHomeContract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter<View> {

    }
}