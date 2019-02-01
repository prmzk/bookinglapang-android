package com.example.keviniswara.bookinglapang.keeper.order

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface KeeperOrderDetailContract {
    interface View : BaseView<Presenter> {
        fun setFieldId(fieldId: String)
        fun setSport(sport: String)
        fun setStartHour(startHour: String)
        fun setEndHour(endHour: String)
        fun setDate(date: String)
        fun setCustNum(number:String)
        fun setCustName(name:String)
    }
    interface Presenter : BasePresenter<View> {
        fun initOrderDetail(orderId:String, sport: String, startHour: String, endHour: String
                            , customerEmail: String, status: String, date: String
                            , fieldId: String)
    }
}