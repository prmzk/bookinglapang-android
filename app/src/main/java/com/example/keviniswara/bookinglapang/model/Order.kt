package com.example.keviniswara.bookinglapang.model

class Order(var customerEmail: String,
            var date: String,
            var endHour: String,
            var fieldId: String,
            var sport: String,
            var startHour: String,
            var status: Int,
            var deadline: Int,
            var orderId: String) {
    constructor() : this("", "", "", "", "", "",
            -1, 0, "")
}
