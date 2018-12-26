package com.example.keviniswara.bookinglapangtest.user.status.presenter

import com.example.keviniswara.bookinglapangtest.model.Transaction
import com.example.keviniswara.bookinglapangtest.user.status.Payment1Contract
import com.example.keviniswara.bookinglapangtest.utils.Database

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