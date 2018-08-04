package com.example.keviniswara.bookinglapang.model

class FindEnemy(var customerEmail: String,
                var customerPhone: String,
                var date: String,
                var fieldId: String,
                var sport: String,
                var time: String,
                var status: Int,
                var deadline: Long,
                var orderId: String) {
    constructor() : this("", "", "", "", "", "",
            -1, 0, "")
}
