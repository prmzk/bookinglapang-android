package com.example.keviniswara.bookinglapang.admin.home.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeFieldDetailContract
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminHomeFieldDetailPresenter : AdminHomeFieldDetailContract.Presenter {

    private var mView: AdminHomeFieldDetailContract.View? = null

    private val fieldReference: DatabaseReference = Database.database.getReference("fields")

    override fun bind(view: AdminHomeFieldDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveSportList(sport: String) {
        val sports: MutableList<Field.PriceTimeDayRange?>? = mutableListOf()
        fieldReference.child(sport).child("sports").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0 != null) {
                    for (ds in p0.children) {
                        for (price in ds.child("price_time_day_range_list").children) {
                            val newPrice = price.getValue(Field.PriceTimeDayRange::class.java)
                            sports!!.add(newPrice)
                        }
                    }
                }
                mView?.initListOfSport(sports)
            }

            override fun onCancelled(p0: DatabaseError?) {
                mView?.initListOfSport(null)
                Log.d("ADMIN FIELD DETAIL", "Failed to get sport and price detail.")
            }
        })
    }
}