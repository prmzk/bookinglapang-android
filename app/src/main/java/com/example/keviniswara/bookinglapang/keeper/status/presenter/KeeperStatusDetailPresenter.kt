package com.example.keviniswara.bookinglapang.keeper.status.presenter

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

    val userRoot: DatabaseReference = Database.database.getReference("users")

    override fun initOrderDetail(sport: String, startHour: String, endHour: String, customerEmail: String, status: String, date: String, fieldId: String) {

        userRoot.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
            }

            override fun onDataChange(userData: DataSnapshot) {

                for (userSnapshot in userData!!.children) {

                    val userEmail = userSnapshot.child("email").getValue<String>(String::class.java)

                    if (userEmail.equals(customerEmail)) {

                        val userName = userSnapshot.child("name").getValue<String>(String::class.java)

                        val userPhoneNumber = userSnapshot.child("phoneNumber").getValue<String>(String::class.java)

                        mView?.setDate(date)
                        mView?.setFieldId(fieldId)
                        mView?.setSport(sport)
                        if (endHour.toInt() < 10) mView?.setEndHour("0$endHour.00") else mView?.setEndHour("$endHour.00")
                        if (startHour.toInt() < 10) mView?.setStartHour("0$startHour.00") else mView?.setStartHour("$startHour.00")
                        if (userName != null) mView?.setUserName(userName)
                        if (userPhoneNumber != null) mView?.setUserPhoneNumber(userPhoneNumber)

                        if (status == "0") {
                            mView?.setButtonVisibility(true)
                        } else {
                            mView?.setButtonVisibility(false)
                        }
                    }
                }
            }
        })
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

            override fun onCancelled(p0: DatabaseError) {
                mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
            }

            override fun onDataChange(orderData: DataSnapshot) {

                if (orderData != null) {
                    for (orderSnapshot in orderData.children) {

                        val order = orderSnapshot.getValue<Order>(Order::class.java)

                        if (order != null && order.orderId.equals(orderId)) {

                            if (type == 0) {
                                orderRoot.child(orderSnapshot.key!!).child("status").setValue(1)
                            } else if (type == 1) {
                                orderRoot.child(orderSnapshot.key!!).child("status").setValue(3)
                            }

                            userEmaiFromOrder = order.customerEmail

                            userRoot.addListenerForSingleValueEvent(object : ValueEventListener {

                                override fun onCancelled(p0: DatabaseError) {
                                    mView?.makeToast("Terjadi kesalahan, silahkan coba lagi.")
                                }

                                override fun onDataChange(userData: DataSnapshot) {

                                    for (userSnapshot in userData!!.children) {

                                        val userEmail = userSnapshot.child("email").getValue<String>(String::class.java)

                                        if (userEmail.equals(userEmaiFromOrder)) {

                                            val userId = userSnapshot.key

                                            for (userOrderSnapshot in userData.child(userSnapshot.key!!).child("orders").children) {

                                                val order = userOrderSnapshot.getValue<Order>(Order::class.java)

                                                val orderKey = userOrderSnapshot.key

                                                if (order != null && order.orderId.equals(orderId)) {

                                                    if (type == 0) {
                                                        userRoot.child(userId!!).child("orders").child(orderKey!!)
                                                                .child("status").setValue(1)
                                                        mView?.makeToast("Sukses mengubah status pesanan menjadi ada.")

                                                        Database.addXMinutesDeadline(orderSnapshot.key, userId, userOrderSnapshot.key, 30)

                                                    } else if (type == 1) {
                                                        userRoot.child(userId!!).child("orders").child(orderKey!!)
                                                                .child("status").setValue(3)
                                                        mView?.makeToast("Sukses mengubah status pesanan menjadi gagal.")

                                                        Database.addXMinutesDeadline(orderSnapshot.key, userId, userOrderSnapshot.key, 1440)
                                                    }
                                                    mView?.finish()
                                                }
                                            }

                                            sendNotificationToUser(userId!!, type)
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