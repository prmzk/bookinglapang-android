package com.example.keviniswara.bookinglapang.admin.home.presenter

import com.example.keviniswara.bookinglapang.admin.home.AdminHomeSportDetailContract
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.Price
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DatabaseReference

class AdminHomeSportDetailPresenter : AdminHomeSportDetailContract.Presenter {

    private var mView: AdminHomeSportDetailContract.View? = null

    private val fieldReference: DatabaseReference = Database.database.getReference("fields")

    private val priceReference: DatabaseReference = Database.database.getReference("prices")

    private val priceListReference: DatabaseReference = Database.database.getReference("prices_list")

    override fun bind(view: AdminHomeSportDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun savePrice(fieldId: String) {
        val startDay = mView!!.getStartDay()
        val endDay = mView!!.getEndDay()
        val startHour = mView!!.getStartHour()
        val endHour = mView!!.getEndHour()
        val price = mView!!.getPrice()
        val sport = mView!!.getSport()

        val sportInsert = Field.Sport(sport)

        fieldReference.child(fieldId).child("sports").push().setValue(sportInsert)

        val priceWithDayRange = Price("$startDay-$endDay", "$startHour.00-$endHour.00", price, sport)
        priceReference.child(fieldId).child(sport).push().setValue(priceWithDayRange)

        for (day in dayToInt(startDay)..dayToInt(endDay)) {
            for (hour in startHour.toInt()..(endHour.toInt()-1)) {
                priceListReference.child(fieldId).child(sport).child(day.toString()).child(hour.toString()).setValue(price)
            }
        }
    }

    private fun dayToInt(day: String) : Int{
        return when (day) {
            "Minggu" -> 1
            "Senin" -> 2
            "Selasa" -> 3
            "Rabu" -> 4
            "Kamis" -> 5
            "Jumat" -> 6
            "Sabtu" -> 7
            else -> {
                1
            }
        }
    }
}