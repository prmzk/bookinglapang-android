package com.example.keviniswara.bookinglapang.model

class User(var email: String, var field: String?, var name: String, var orders: List<Order>?, var phoneNumber: String,
           var status: Int) {
    constructor() : this("", null, "", null, "", -1)
}