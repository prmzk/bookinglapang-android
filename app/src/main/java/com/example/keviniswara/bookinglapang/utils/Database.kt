package com.example.keviniswara.bookinglapang.utils

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import javax.xml.datatype.DatatypeConstants.SECONDS




class Database {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val root: DatabaseReference = database.getReference("")

    fun setUsers(id: String, user: User) {
        root.child("users").child(id).setValue(user)
    }

/*    fun getUsers(): MutableList<User>? {
        val root: DatabaseReference = database.getReference("users")

        var user: User?
        var userList: MutableList<User>? = mutableListOf<User>()
        var finish: Boolean = false

        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d("KEVIN", "masuk sini ah ah")

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
