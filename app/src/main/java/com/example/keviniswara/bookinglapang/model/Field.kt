package com.example.keviniswara.bookinglapang.model

class Field(var field_id: String, var sports: List<Sport>) {

    class Sport(var sport_name: String, var price_list: List<Price>)

    class Price(var day: String, var hour: String, var price: Int)
}