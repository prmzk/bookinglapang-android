package com.example.keviniswara.bookinglapang.utils

object VerifyUtils {
    fun verifyPhone(input :String?):Boolean{
        if(input==null){
            return false;
        }else {
            val phoneRegex = Regex("^(^\\+62\\s?|^0)(\\d{3,4}-?){2}\\d{3,4}\$")
            return phoneRegex.matches(input)
        }
    }
}