package com.example.keviniswara.bookinglapang.user.order.presenter

import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.user.order.OrderDetailContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderDetailPresenter : OrderDetailContract.Presenter{

    private var mView: OrderDetailContract.View? = null

    override fun bind(view: OrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun initOrderDetail(sport: String, startHour: String, endHour: String
                                 , customerEmail: String, status: String, date: String
                                 , fieldId: String, orderId: String) {

        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")
        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
        mView?.setDate(date)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)

        Database.database.getReference("transactions").child(orderId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snap: DataSnapshot) {
                val transaction = snap.getValue<Transaction>(Transaction::class.java)

                if (transaction != null) {
                    mView?.setPrice(transaction.payment)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}