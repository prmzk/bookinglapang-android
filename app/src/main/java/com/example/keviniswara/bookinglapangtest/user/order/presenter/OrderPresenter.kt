package com.example.keviniswara.bookinglapangtest.user.order.presenter

import com.example.keviniswara.bookinglapangtest.model.Order
import com.example.keviniswara.bookinglapangtest.user.order.OrderContact
import com.example.keviniswara.bookinglapangtest.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class OrderPresenter() : OrderContact.Presenter {

    private var mView: OrderContact.View? = null

    override fun bind(view: OrderContact.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveOrderList(start: String, end: String) {

        var orders: MutableList<Order?>? = mutableListOf()

        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val startDate = start.split("/")

        val endDate = end.split("/")

        val dayStart = Integer.parseInt(startDate[0])
        val monthStart = Integer.parseInt(startDate[1])
        val yearStart = Integer.parseInt(startDate[2])

        val dayEnd = Integer.parseInt(endDate[0])
        val monthEnd = Integer.parseInt(endDate[1])
        val yearEnd = Integer.parseInt(endDate[2])

        if (yearEnd < yearStart || yearEnd == yearStart && monthEnd < monthStart ||
                yearEnd == yearStart && monthEnd == monthStart && dayEnd < dayStart) {
            mView?.makeToast("Waktu akhir tidak boleh lebih cepat dari waktu awal!")
            mView?.clearOrderList()
        } else {

            userRoot.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                    mView?.initListOfOrders(null)
                }

                override fun onDataChange(userData: DataSnapshot) {

                    mView?.clearOrderList()

                    if (userData!!.child(userId).hasChild("orders")) {
                        for (orderSnapshot in userData.child(userId).child("orders").children) {

                            val order = orderSnapshot.getValue<Order>(Order::class.java)

                            if (order != null && order.status == 2) {

                                val orderDate = order.date.split("/")

                                val dayOrder = Integer.parseInt(orderDate[0])
                                val monthOrder = Integer.parseInt(orderDate[1])
                                val yearOrder = Integer.parseInt(orderDate[2])

                                if ((dayStart == dayOrder && monthStart == monthOrder && yearStart == yearOrder)
                                        || (dayEnd == dayOrder && monthEnd == monthOrder && yearEnd == yearOrder)
                                        || ((yearOrder < yearEnd || yearOrder == yearEnd && monthOrder < monthEnd
                                                || yearOrder == yearEnd && monthOrder == monthEnd && dayOrder < dayEnd) &&
                                                (yearOrder > yearStart || yearOrder == yearStart && monthOrder > monthStart
                                                        || yearOrder == yearStart && monthOrder == monthStart && dayOrder > dayStart))) {
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