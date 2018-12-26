package com.example.keviniswara.bookinglapangtest.user.status

import com.example.keviniswara.bookinglapangtest.BasePresenter
import com.example.keviniswara.bookinglapangtest.BaseView
import com.example.keviniswara.bookinglapangtest.model.Bank

interface Payment3Contract {
    interface View : BaseView<Presenter> {
        fun setName(name: String)
        fun setBillsNumber(billsNumber: String)
        fun setTotal(total: String)
        fun startHomeFragment()
        fun showToastMessage(message: String)
    }
    interface Presenter : BasePresenter<View> {
        fun getBankDetail(bankName: String, orderId: String)
        fun countTotalPayment(orderId: String, fieldName: String, sport: String, startHour: String, endHour: String, date: String)
        fun addBankToTransaction(bank: Bank, orderId: String)
    }
}