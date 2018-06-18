package com.example.keviniswara.bookinglapang.model
class User(var name: String,
           var email: String,
           var phoneNumber: String,
           var status: Int,
           var field: String?,
           var orders: List<Order>?,
           var notifications: List<Notification>?) {
    constructor() : this("", "", "", -1, null, null, null)

    class Notification(var from: String = "",
                       var message: String = "")
}