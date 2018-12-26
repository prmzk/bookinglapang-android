package com.example.keviniswara.bookinglapangtest.model

class Transaction(var name: String = "",
                  var phoneNumber: String = "",
                  var bank: Bank? = null,
                  var payment: Int = 0)