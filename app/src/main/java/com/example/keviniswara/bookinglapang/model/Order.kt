package com.example.keviniswara.bookinglapang.model

class Order(var customerName:String,
            var customerEmail: String,
            var date: String,
            var endHour: String,
            var fieldId: String,
            var sport: String,
            var startHour: String,
            var status: Int,
            var deadline: Long,
            var feedback: String = "",
            var request: String = "",
            var orderId: String,
            val lastUpdate: Long) {
    constructor() : this("","", "", "", "", "", "",
            -1, 0, "","","", 0)
}
