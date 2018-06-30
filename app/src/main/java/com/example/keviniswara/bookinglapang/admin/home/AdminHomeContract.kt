package com.example.keviniswara.bookinglapang.admin.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Field

interface AdminHomeContract {
    interface View : BaseView<Presenter> {
        fun initListOfFields(fields: MutableList<Field?>?)
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveFieldList()
    }
}