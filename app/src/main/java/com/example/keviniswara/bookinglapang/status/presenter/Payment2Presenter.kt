package com.example.keviniswara.bookinglapang.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Bank
import com.example.keviniswara.bookinglapang.status.Payment2Contract
import com.google.firebase.database.*

class Payment2Presenter: Payment2Contract.Presenter {

    private var mView: Payment2Contract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val bankReference: DatabaseReference = database.getReference("").child("banks")

    override fun bind(view: Payment2Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveListOfBankFromFirebase() {
        var listOfBank = mutableListOf<String>()
        bankReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                fetchDataBank(p0, listOfBank)

                mView!!.initListOfBankDropdown(listOfBank)
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.d("PAYMENT 2", "Error retrieve list of bank")
            }
        })
    }

    private fun fetchDataBank(dataSnapshot: DataSnapshot?, listOfBank: MutableList<String>) {
        listOfBank.clear()
        for (ds in dataSnapshot!!.children) {
            val name = ds.getValue(Bank::class.java)!!.name
            listOfBank.add(name)
        }
    }
}