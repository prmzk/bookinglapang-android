package com.example.keviniswara.bookinglapang.keeper.status.presenter

import com.example.keviniswara.bookinglapang.keeper.status.KeeperStatusContract
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class KeeperStatusPresenter : KeeperStatusContract.Presenter {

    private var mView: KeeperStatusContract.View? = null

    override fun retrieveOrderList() {

        var orders: MutableList<Order?>? = mutableListOf()

        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val orderRoot: DatabaseReference = Database.database.getReference("orders")

        userRoot.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {

                mView?.initListOfOrders(null)
            }

            override fun onDataChange(userData: DataSnapshot?) {

                if (userData!!.child(userId).hasChild("field")) {

                    val fieldId: String? = userData.child(userId).child("field").getValue<String>(String::class.java)

                    orderRoot.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                            mView?.initListOfOrders(null)
                        }

                        override fun onDataChange(orderData: DataSnapshot?) {

                            mView?.clearOrderList()

                            if (orderData != null) {
                                for (orderSnapshot in orderData.children) {

                                    val order = orderSnapshot.getValue<Order>(Order::class.java)

                                    if (order != null && order.status == 0 && fieldId.equals(order.fieldId)) {
                                        orders?.add(order)
                                    }
                                }
                            } else {
                                mView?.initListOfOrders(null)
                            }

                            mView?.initListOfOrders(orders)
                        }

                    })

                } else {
                    mView?.initListOfOrders(null)
                }

            }

        })
    }

    override fun bind(view: KeeperStatusContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}