package com.example.keviniswara.bookinglapang.keeper.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.keeper.status.KeeperStatusDetailContract
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class KeeperStatusDetailPresenter : KeeperStatusDetailContract.Presenter {

    private var mView: KeeperStatusDetailContract.View? = null

    override fun initOrderDetail(sport: String, startHour: String, endHour: String, customerEmail: String, status: String, date: String, fieldId: String) {
        mView?.setDate(date)
        mView?.setEndHour(endHour)
        mView?.setFieldId(fieldId)
        mView?.setSport(sport)
        mView?.setStartHour(startHour)
    }

    override fun bind(view: KeeperStatusDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    // type = 0, status available, type = 1, status not available
    override fun setField(orderId: String, type: Int) {

        var userEmaiFromOrder: String

        val userRoot: DatabaseReference = Database.database.getReference("users")

        val orderRoot: DatabaseReference = Database.database.getReference("orders")

        orderRoot.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {
                mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
            }

            override fun onDataChange(orderData: DataSnapshot?) {

                if (orderData != null) {
                    for (orderSnapshot in orderData.children) {

                        val order = orderSnapshot.getValue<Order>(Order::class.java)

                        if (order != null && order.orderId.equals(orderId)) {

                            if (type == 0) {
                                orderRoot.child(orderSnapshot.key).child("status").setValue(1)
                            } else if (type == 1) {
                                orderRoot.child(orderSnapshot.key).child("status").setValue(3)
                            }

                            userEmaiFromOrder = order.customerEmail

                            userRoot.addListenerForSingleValueEvent(object : ValueEventListener {

                                override fun onCancelled(p0: DatabaseError?) {
                                    mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
                                }

                                override fun onDataChange(userData: DataSnapshot?) {

                                    for (userSnapshot in userData!!.children) {

                                        val userEmail = userSnapshot.child("email").getValue<String>(String::class.java)

                                        if (userEmail.equals(userEmaiFromOrder)) {

                                            val userId = userSnapshot.key

                                            for (orderSnapshot in userData.child(userSnapshot.key).child("orders").children) {

                                                val order = orderSnapshot.getValue<Order>(Order::class.java)

                                                val orderKey = orderSnapshot.key

                                                if (order != null && order.orderId.equals(orderId)) {

                                                    if (type == 0) {
                                                        userRoot.child(userId).child("orders").child(orderKey)
                                                                .child("status").setValue(1)
                                                        mView?.makeToast("Sukses mengubah status pesanan menjadi ada.")
                                                    } else if (type == 1) {
                                                        userRoot.child(userId).child("orders").child(orderKey)
                                                                .child("status").setValue(3)
                                                        mView?.makeToast("Sukses mengubah status pesanan menjadi gagal.")
                                                    }
                                                    mView?.finish()
                                                }
                                            }

                                            sendNotificationToUser(userId, type)
                                        }
                                    }
                                }
                            })
                        }
                    }
                } else {
                    mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
                }
            }

        })
    }

    // type = 0, status available, type = 1, status not available
    override fun sendNotificationToUser(userId: String, type: Int) {

        val usersReference: DatabaseReference = Database.database.getReference("users")

        var message: String = ""

        when (type) {
            0 -> message = "Lapangan tersedia"
            1 -> message = "Lapangan tidak tersedia"
        }

        val notification = User.Notification(FirebaseAuth.getInstance().currentUser!!.uid, message)

        Database.addNotification(userId, notification)
    }
}