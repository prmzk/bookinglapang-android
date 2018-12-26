package com.example.keviniswara.bookinglapangtest.admin.home

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Field

interface AdminHomeContract {
    interface View : BaseView<Presenter> {
        fun initListOfFields(fields: MutableList<Field?>?)
        fun startFieldDetailFragment()
        fun moveToDetail(fieldDetail: Field)
    }
    interface Presenter : BasePresenter<View> {
        fun retrieveFieldList()
    }
}