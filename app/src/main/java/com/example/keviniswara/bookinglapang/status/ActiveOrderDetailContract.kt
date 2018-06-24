package com.example.keviniswara.bookinglapang.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface ActiveOrderDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
        fun setStatusNotVerified()
        fun setStatusNotPaid()
        fun setStatusCancelled()
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String)
    }
}