package com.example.keviniswara.bookinglapangtest.admin.status.presenter

import com.example.keviniswara.bookinglapangtest.admin.status.AdminStatusContract
import com.example.keviniswara.bookinglapangtest.model.Order
import com.example.keviniswara.bookinglapangtest.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminStatusPresenter : AdminStatusContract.Presenter {

    private var mView: AdminStatusContract.View? = null

    override fun bind(view: AdminStatusContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveOrderList() {

        Database.addServerDate()

        var orders: MutableList<Order?>? = mutableListOf()

        val timeRoot: DatabaseReference = Database.database.getReference("server_time")

        val orderRoot: DatabaseReference = Database.database.getReference("orders")

        timeRoot.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mView?.makeToast("Failed to get server time")
            }

            override fun onDataChange(dateSnapshot: DataSnapshot) {
                val dateInMillis: Long = dateSnapshot?.getValue() as Long

                orderRoot.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                        mView?.initListOfOrders(null)
                    }

                    override fun onDataChange(orderData: DataSnapshot) {

                        mView?.clearOrderList()

                        if (orderData != null) {
                            for (orderSnapshot in orderData.children) {

                                val order = orderSnapshot.getValue<Order>(Order::class.java)

                                if (order != null && order.status == 1 &&  order.deadline >= dateInMillis) {
                                    orders?.add(order)
                                }
                            }
                            mView?.initListOfOrders(orders)
                        } else {
                            mView?.initListOfOrders(null)
                        }
                    }
                })
            }
        })
    }
}