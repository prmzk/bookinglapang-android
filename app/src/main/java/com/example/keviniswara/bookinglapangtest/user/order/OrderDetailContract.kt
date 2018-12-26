package com.example.keviniswara.bookinglapangtest.user.order

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView

interface OrderDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String)
    }
}