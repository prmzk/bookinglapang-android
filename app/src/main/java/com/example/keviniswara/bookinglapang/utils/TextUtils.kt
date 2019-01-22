package com.example.keviniswara.bookinglapang.utils

import java.lang.Exception

object TextUtils {
    fun verifyPhone(input :String?):Boolean{
        if(input==null){
            return false;
        }else {
            val phoneRegex = Regex("^(^\\+62\\s?|^0)(\\d{3,4}-?){2}\\d{3,4}\$")
            return phoneRegex.matches(input)
        }
    }
    fun convertToCurrency(input: String?):String{
        if(input==null){
            return "Error";
        }else {
            try {
                val num:Int = input.toInt();
                return String.format("Rp. %,d", num).replace(",",".");
            }catch (e:Exception){
                return "Error";
            }
        }
    }
}