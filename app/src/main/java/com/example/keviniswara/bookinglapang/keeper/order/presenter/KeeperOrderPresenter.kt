package com.example.keviniswara.bookinglapang.keeper.order.presenter

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.keviniswara.bookinglapang.keeper.order.KeeperOrderContract
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class KeeperOrderPresenter : KeeperOrderContract.Presenter {

    private var mView: KeeperOrderContract.View? = null

    override fun bind(view: KeeperOrderContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun retrieveOrderList(start: String, end: String) {

        var orders: MutableList<Order?>? = mutableListOf()

        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val orderRoot: DatabaseReference = Database.database.getReference("orders")

        val formatter = DateTimeFormatter.ofPattern("d/M/u", Locale.ENGLISH)

        val startDate = LocalDate.parse(start, formatter)

        val endDate = LocalDate.parse(end, formatter)

        if (endDate.isBefore(startDate)) {
            mView?.makeToast("Waktu akhir tidak boleh lebih cepat dari waktu awal!")
            mView?.clearOrderList()
        } else {

            userRoot.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {

                    mView?.initListOfOrders(null)
                }

                override fun onDataChange(userData: DataSnapshot?) {

                    if (userData!!.child(userId).hasChild("field")) {

                        val fieldId: String? = userData.child(userId).child("field").getValue<String>(String::class.java)

                        orderRoot.addValueEventListener(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError?) {

                                mView?.initListOfOrders(null)
                            }

                            override fun onDataChange(orderData: DataSnapshot?) {

                                mView?.clearOrderList()

                                if (orderData != null) {
                                    for (orderSnapshot in orderData.children) {

                                        val order = orderSnapshot.getValue<Order>(Order::class.java)

                                        if (order != null && order.status == 2 && fieldId.equals(order.fieldId)) {

                                            val orderDate = LocalDate.parse(order.date, formatter)

                                            if (orderDate.equals(startDate) || orderDate.equals(endDate) ||
                                                    orderDate.isAfter(startDate) && orderDate.isBefore(endDate)) {
                                                orders!!.add(order)
                                            }
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
    }
}