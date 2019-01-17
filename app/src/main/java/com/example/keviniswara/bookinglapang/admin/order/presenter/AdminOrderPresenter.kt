package com.example.keviniswara.bookinglapang.admin.order.presenter

import com.example.keviniswara.bookinglapang.admin.order.AdminOrderContract
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminOrderPresenter : AdminOrderContract.Presenter {

    private var mView: AdminOrderContract.View? = null

    override fun bind(view: AdminOrderContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveOrderList(start: String, end: String) {

        var orders: MutableList<Order?>? = mutableListOf()

        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val orderRoot: DatabaseReference = Database.database.getReference("orders")

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

            orderRoot.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                    mView?.initListOfOrders(null)
                }

                override fun onDataChange(orderData: DataSnapshot) {

                    mView?.clearOrderList()

                    if (orderData != null) {
                        for (orderSnapshot in orderData.children) {

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

                                mView?.initListOfOrders(orders)
                            }
                        }
                    } else {
                        mView?.initListOfOrders(null)
                    }
                }

            })
        }

    }
}