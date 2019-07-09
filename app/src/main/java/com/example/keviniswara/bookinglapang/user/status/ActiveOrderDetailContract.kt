package com.example.keviniswara.bookinglapang.user.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Order

interface ActiveOrderDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
        fun setFeedback(feedback: String)
        fun setStatusNotVerified()
        fun setStatusNotPaid()
        fun setStatusCancelled()
        fun setRequest(request: String)
        fun initButton(status: String)
        fun moveToPayment(orderDetail: Order)
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String, feedback: String, request: String)
    }
}