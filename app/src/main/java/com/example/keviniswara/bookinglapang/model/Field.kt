package com.example.keviniswara.bookinglapang.model

class Field(var field_id: String, var sport: String, var priceList: List<Price>) {

    constructor() : this("", "", mutableListOf<Price>())

    class Price(var day: String, var hour: String, var price: Int) {

        constructor() : this("", "", -1)

    }
}