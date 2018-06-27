package com.example.keviniswara.bookinglapang.admin.profile

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminProfileContract {
    interface View : BaseView<Presenter> {

    }
    interface Presenter : BasePresenter<View> {

    }
}