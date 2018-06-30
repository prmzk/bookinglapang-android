package com.example.keviniswara.bookinglapang.model

class Field(var field_id: String = "",
            var sports: List<Sport> = emptyList(),
            var phone_number: String = "",
            var contact_person: String = "",
            var address: String = "") {

    class Sport(var sport_name: String = "",
                var price_list: List<Price> = emptyList())

    class Price(var day: String = "",
                var hour: String = "",
                var price: Int = 0)
}