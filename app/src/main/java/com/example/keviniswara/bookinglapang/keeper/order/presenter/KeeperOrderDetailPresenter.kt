package com.example.keviniswara.bookinglapang.keeper.order.presenter

import com.example.keviniswara.bookinglapang.keeper.order.KeeperOrderDetailContract
import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class KeeperOrderDetailPresenter : KeeperOrderDetailContract.Presenter {

    private var mView: KeeperOrderDetailContract.View? = null

    override fun initOrderDetail(orderId:String, sport: String, startHour: String, endHour: String, customerEmail: String, status: String, date: String, fieldId: String) {
        mView?.setDate(date)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")

        val transRoot: DatabaseReference = Database.database.getReference("/transactions");

        transRoot.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.setCustNum("Error, transaction not found")
                mView?.setCustName("Error, transaction not found")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChild(orderId)){
                    val transData = p0.child(orderId).getValue(Transaction::class.java)

                    if (transData != null) {
                        mView?.setCustName(transData.name)
                        mView?.setCustNum(transData.phoneNumber)
                        mView?.setPrice(transData.payment)
                    }
                }else{
                    mView?.setCustName("Error, transaction not found")
                    mView?.setCustNum("Error, transaction not found")
                }
            }

        })
    }

    override fun bind(view: KeeperOrderDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}