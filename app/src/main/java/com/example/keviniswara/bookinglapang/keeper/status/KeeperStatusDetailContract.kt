package com.example.keviniswara.bookinglapang.keeper.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface KeeperStatusDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
        fun setUserName(name: String)
        fun setUserPhoneNumber(phoneNumber: String)
        fun makeToast(text: String)
        fun finish()
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String)
        fun setField(orderId: String, type: Int)
        fun sendNotificationToUser(email: String, type: Int)
    }
}