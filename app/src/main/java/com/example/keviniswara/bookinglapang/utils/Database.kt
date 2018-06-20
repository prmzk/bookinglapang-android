package com.example.keviniswara.bookinglapang.utils

import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


object Database {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val root: DatabaseReference = database.getReference("")

    fun setUsers(user: User) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        root.child("users").child(userId).setValue(user)
    }

    fun updateTokenId(newToken: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val tokenRef = root.child("users").child(userId)
        val tokenUpdate = HashMap<String, Any>()
        tokenUpdate["tokenId"] = newToken

        tokenRef.updateChildren(tokenUpdate)
    }

    fun addNewOrder(order: Order) {
        addNewOrderToUsersChild(order)
        addNewOrderToOrdersChild(order)
    }

    private fun addNewOrderToUsersChild(order: Order) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        root.child("users").child(userId).child("orders").push().setValue(order)
    }

    private fun addNewOrderToOrdersChild(order: Order) {
        root.child("orders").push().setValue(order)
    }

    fun addNotification(uid: String, notification: User.Notification) {
        root.child("users").child(uid).child("notifications").push().setValue(notification)
    }

/*    fun getUsers(): MutableList<User>? {
        val root: DatabaseReference = database.getReference("users")

        var user: User?
        var userList: MutableList<User>? = mutableListOf<User>()
        var finish: Boolean = false

        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (userSnapshot in dataSnapshot.children) {

                    val name: String = userSnapshot.child("name")
                            .getValue<String>(String::class.java) ?: ""
                    val email: String = userSnapshot.child("email")
                            .getValue<String>(String::class.java) ?: ""
                    val status: Int = userSnapshot.child("status")
                            .getValue<Int>(Int::class.java) ?: 0
                    val phoneNumber: String = userSnapshot.child("phoneNumber")
                            .getValue<String>(String::class.java) ?: ""
                    val field: String?
                    var orders: MutableList<Order>? = mutableListOf<Order>()

                    if (userSnapshot.hasChild("field")) {
                        field = userSnapshot.child("field")
                                .getValue<String>(String::class.java)
                    } else {
                        field = null
                    }

                    if (userSnapshot.hasChild("orders")) {

                        for (orderSnapshot in userSnapshot.child("orders").children) {

                            val order = orderSnapshot.getValue<Order>(Order::class.java)
                            orders!!.add(order!!)

                        }

                    } else {
                        orders = null
                    }

                    user = User(email, field, name, orders, phoneNumber, status)

                    userList?.add(user!!)
                }
                finish = true
            }

            override fun onCancelled(databaseError: DatabaseError) {
                userList = null
                finish = true
            }
        })
        return userList
    }*/
}
