package com.example.keviniswara.bookinglapang.user.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Bank
import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.user.status.Payment2Contract
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class Payment2Presenter: Payment2Contract.Presenter {

    private var mView: Payment2Contract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val bankReference: DatabaseReference = database.getReference("").child("banks")

    private val priceReference: DatabaseReference = database.getReference("").child("prices_list")

    private val transactionReference: DatabaseReference = database.getReference("").child("transactions")

    override fun bind(view: Payment2Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveListOfBankFromFirebase() {
        var listOfBank = mutableListOf<String>()
        bankReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                fetchDataBank(p0, listOfBank)

                mView!!.initListOfBankDropdown(listOfBank)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("PAYMENT 2", "Error retrieve list of bank")
            }
        })
    }

    override fun countTotalPayment(orderId: String, fieldName: String, sport: String, startHour: String, endHour: String, date: String) {
        transactionReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var alreadyComputed = false

                if(p0.hasChild(orderId)){
                    if(p0.child(orderId).hasChild("payment")){
                        val trans = p0.child(orderId).getValue(Transaction::class.java)

                        if (trans != null) {
                            mView!!.setTotal(trans.payment.toString())
                            alreadyComputed = true
                        }
                    }

                }

                if(!alreadyComputed){
                    val day = dateToIntDay(date)
                    var total = 0
                    val start = startHour.toInt()
                    val end = endHour.toInt()
                    priceReference.child(fieldName).child(sport).child(day.toString()).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            for (ds in p0!!.children) {
                                if (ds.key!!.toInt() in start until end) {
                                    total += ds.getValue(String::class.java)!!.toInt()
                                }
                            }

                            val r = Random()
                            val randomInt = r.nextInt(999) + 1

                            total += randomInt

                            mView!!.setTotal(total.toString())
                            addTotalPaymentToTransaction(total.toLong(), orderId)
                        }

                        override fun onCancelled(p0: DatabaseError) {

                        }
                    })
                }


            }

        })
    }

    private fun addTotalPaymentToTransaction(total: Long, orderId: String) {
        transactionReference.child(orderId).child("payment").setValue(total)
    }

    private fun dateToIntDay(dateString: String) : Int {
        val date = SimpleDateFormat("dd/MM/yy", Locale.US).parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    private fun intToDay(day: Int) : String {
        return when (day) {
            1 -> "Minggu"
            2 -> "Senin"
            3 -> "Selasa"
            4 -> "Rabu"
            5 -> "Kamis"
            6 -> "Jumat"
            7 -> "Sabtu"
            else -> {
                "Minggu"
            }
        }
    }

    private fun fetchDataBank(dataSnapshot: DataSnapshot?, listOfBank: MutableList<String>) {
        listOfBank.clear()
        for (ds in dataSnapshot!!.children) {
            val name = ds.getValue(Bank::class.java)!!.name
            listOfBank.add(name)
        }
    }
}