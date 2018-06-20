package com.example.keviniswara.bookinglapang.order.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.order.OrderContact
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class OrderPresenter() : OrderContact.Presenter {

    private var mView: OrderContact.View? = null

    override fun bind(view: OrderContact.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveOrderList() {

        var orders: MutableList<Order?>? = mutableListOf()

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val userId = Database.userId

        userRoot.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                mView!!.initListOfOrders(null)
            }

            override fun onDataChange(userData: DataSnapshot?) {

                if (userData!!.child(userId).hasChild("orders")) {
                    for (orderSnapshot in userData.child(userId).child("orders").children) {

                        val order = orderSnapshot.getValue<Order>(Order::class.java)

                        if (order != null) {
                            orders!!.add(order)
                        }
                    }

                    mView!!.initListOfOrders(orders)

                } else {
                    mView!!.initListOfOrders(null)
                }

            }

        })
    }
}