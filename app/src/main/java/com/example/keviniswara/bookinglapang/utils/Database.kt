package com.example.keviniswara.bookinglapang.utils

import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
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

    fun addTransaction(orderId: String, transaction: Transaction) {
        root.child("transactions").child(orderId).setValue(transaction)
    }

    fun addServerDate() {
        root.child("server_time").setValue(ServerValue.TIMESTAMP)
    }
}
