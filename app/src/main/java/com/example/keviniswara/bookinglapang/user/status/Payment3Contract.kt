package com.example.keviniswara.bookinglapang.user.status

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Bank

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
        fun addBankToTransaction(bank: Bank, orderId: String)
        fun getPayment(orderId: String)
    }
}