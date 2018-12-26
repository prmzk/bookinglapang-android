package com.example.keviniswara.bookinglapangtest.user.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapangtest.model.Order
import com.example.keviniswara.bookinglapangtest.user.status.StatusContract
import com.example.keviniswara.bookinglapangtest.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class StatusPresenter : StatusContract.Presenter{

    private var mView: StatusContract.View? = null

    override fun bind(view: StatusContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveOrderList() {

        Database.addServerDate()

        var orders: MutableList<Order?>? = mutableListOf()

        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val timeRoot: DatabaseReference = Database.database.getReference("server_time")

        val userRoot: DatabaseReference = Database.database.getReference("users")

        timeRoot.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Status Presenter", "failed to get server time")
            }

            override fun onDataChange(dateSnapshot: DataSnapshot) {

                val dateInMillis: Long = dateSnapshot?.value as Long

                userRoot.addValueEventListener(object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        mView?.initListOfOrders(null)
                    }

                    override fun onDataChange(userData: DataSnapshot) {

                        mView?.clearOrderList()

                        if (userData!!.child(userId).hasChild("orders")) {
                            for (orderSnapshot in userData.child(userId).child("orders").children) {

                                val order = orderSnapshot.getValue<Order>(Order::class.java)

                                if (order != null && order.status != 2) {
                                    if (order.status == 0 || order.status != 0 && order.deadline >= dateInMillis) {
                                        orders?.add(order)
                                    }
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