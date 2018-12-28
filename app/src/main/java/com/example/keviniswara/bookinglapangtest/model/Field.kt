package com.example.keviniswara.bookinglapangtest.model

class Field(var field_id: String = "",
            var sports: HashMap<String,Sport> = hashMapOf(),
            var phone_number: String = "",
            var contact_person: String = "",
            var address: String = "") {

    class Sport(var sport_name: String = "")

    class Price(var day: String = "",
                var hour: String = "",
                var price: Int = 0)

    class PriceTimeDayRange(var day_range: String = "",
                            var hour_range: String = "",
                            var price: String = "",
                            var sport_name: String = "")
}