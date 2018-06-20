package com.example.keviniswara.bookinglapang.order.presenter

import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.order.OrderContact
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
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
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val userRoot: DatabaseReference = Database.database.getReference("users")

        userRoot.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                mView!!.initListOfOrders(null)
            }

            override fun onDataChange(userData: DataSnapshot?) {

                val cusEmail: String = userData!!.child(userId).child("email").value.toString()

                val root: DatabaseReference = Database.database.getReference("orders")

                root.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (orderSnapshot in dataSnapshot.children) {

                            val order = orderSnapshot.getValue<Order>(Order::class.java)

                            if (order!!.customerEmail.equals(cusEmail)) {
                                orders!!.add(order)
                            }
                        }

                        mView!!.initListOfOrders(orders)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        mView!!.initListOfOrders(null)
                    }
                })
            }

        })
    }
}