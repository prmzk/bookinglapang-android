package com.example.keviniswara.bookinglapang.user.order.presenter

import android.os.Build
import android.support.annotation.RequiresApi
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.order.OrderContact
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class OrderPresenter() : OrderContact.Presenter {

    private var mView: OrderContact.View? = null

    override fun bind(view: OrderContact.View) {
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

        val formatter = DateTimeFormatter.ofPattern("d/M/u", Locale.ENGLISH)

        val startDate = LocalDate.parse(start, formatter)

        val endDate = LocalDate.parse(end, formatter)

        if (endDate.isBefore(startDate)) {
            mView?.makeToast("Waktu akhir tidak boleh lebih cepat dari waktu awal!")
            mView?.clearOrderList()
        } else {

            userRoot.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {

                    mView?.initListOfOrders(null)
                }

                override fun onDataChange(userData: DataSnapshot?) {

                    mView?.clearOrderList()

                    if (userData!!.child(userId).hasChild("orders")) {
                        for (orderSnapshot in userData.child(userId).child("orders").children) {

                            val order = orderSnapshot.getValue<Order>(Order::class.java)

                            if (order != null && order.status == 2) {

                                val orderDate = LocalDate.parse(order.date, formatter)

                                if (orderDate.equals(startDate) || orderDate.equals(endDate) ||
                                        orderDate.isAfter(startDate) && orderDate.isBefore(endDate)) {
                                    orders!!.add(order)
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
    }
}