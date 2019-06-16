package com.example.keviniswara.bookinglapang.admin.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface AdminStatusDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
        fun setBank(bank: String)
        fun setRekOwner(name :String)
        fun setPrice(price: Int)
        fun setOrderOwner(name:String)
        fun setFeedback(feedback: String)
        fun makeToast(text: String)
        fun finish()
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String, orderId: String, name: String, feedback: String)
        fun alreadyTransfer(orderId: String)
        fun failed(orderId: String)
        fun sendNotificationToUser(userId: String, type: Int, orderId: String)
    }
}