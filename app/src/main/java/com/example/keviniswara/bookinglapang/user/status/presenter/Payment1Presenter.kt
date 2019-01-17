package com.example.keviniswara.bookinglapang.user.status.presenter

import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.user.status.Payment1Contract
import com.example.keviniswara.bookinglapang.utils.Database

class Payment1Presenter: Payment1Contract.Presenter {

    private var mView: Payment1Contract.View? = null

    override fun bind(view: Payment1Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun addTransactionToFirebase(orderId: String) {
        val name = mView?.getName() ?: ""
        val phoneNumber = mView?.getPhoneNumber() ?: ""
        val transaction =  Transaction(name, phoneNumber, null, 0)
        Database.addTransaction(orderId, transaction)
    }
}