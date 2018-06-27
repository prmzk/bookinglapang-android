package com.example.keviniswara.bookinglapang.admin.order

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminOrderContract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter<View> {

    }
}