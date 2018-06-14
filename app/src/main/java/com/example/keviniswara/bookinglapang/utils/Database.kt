package com.example.keviniswara.bookinglapang.utils

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



object Database {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val root: DatabaseReference = database.getReference("")
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    fun setUsers(user: User) {
        root.child("users").child(userId).child("name").setValue(user.name)
        root.child("users").child(userId).child("phoneNumber").setValue(user.phoneNumber)
        root.child("users").child(userId).child("status").setValue(user.status)
        root.child("users").child(userId).child("email").setValue(user.email)
        root.child("users").child(userId).child("field").setValue(user.field)
        root.child("users").child(userId).child("orders").setValue(user.orders)
    }

    fun getUsers() {
        val root: DatabaseReference = database.getReference("users")
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("KEVIN", dataSnapshot.child("bookinglapang8@gmailcom").toString())

                val user: User?

                if (dataSnapshot.child("bookinglapang8@gmailcom").hasChild("field") &&
                        dataSnapshot.child("bookinglapang8@gmailcom").hasChild("orders")) {
                    user = dataSnapshot.child("bookinglapang8@gmailcom").getValue<User>(User::class.java)
                } else  {
                    val name: String = dataSnapshot.child("bookinglapang8@gmailcom").child("name")
                            .getValue<String>(String::class.java) ?: ""
                    val email: String = dataSnapshot.child("bookinglapang8@gmailcom").child("email")
                            .getValue<String>(String::class.java) ?: ""
                    val status: Int = dataSnapshot.child("bookinglapang8@gmailcom").child("status")
                            .getValue<Int>(Int::class.java) ?: 0
                    val phoneNumber: String = dataSnapshot.child("bookinglapang8@gmailcom").child("phoneNumber")
                            .getValue<String>(String::class.java) ?: ""
                    val field: String?
                    val orders: List<Order>?

                    if (dataSnapshot.child("bookinglapang8@gmailcom").hasChild("field")) {
                        field = dataSnapshot.child("bookinglapang8@gmailcom").child("field")
                                .getValue<String>(String::class.java)
                    } else {
                        field = null
                    }

                    orders = null

                    user = User(name, email, phoneNumber, status, field, orders)
                }
                Log.d("KEVIN", user?.name)
                Log.d("KEVIN", user?.phoneNumber)
                Log.d("KEVIN", user?.status.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("KEVIN", "The read failed: " + databaseError.code)
            }
        })
    }
}