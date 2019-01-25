package com.example.keviniswara.bookinglapang.user.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Bank
import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.user.status.Payment3Contract
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class Payment3Presenter: Payment3Contract.Presenter {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val bankReference: DatabaseReference = database.getReference("").child("banks")

    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    private val priceReference: DatabaseReference = database.getReference("").child("prices_list")

    private val transactionReference: DatabaseReference = database.getReference("").child("transactions")

    private var mView: Payment3Contract.View? = null

    override fun bind(view: Payment3Contract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun getBankDetail(bankName: String, orderId: String) {
        bankReference.orderByChild("name").equalTo(bankName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0!!.children) {
                    val bank = ds.getValue(Bank::class.java)!!
                    mView!!.setName(bank.billsOwner)
                    mView!!.setBillsNumber(bank.billsNumber)
                    addBankToTransaction(bank, orderId)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("PAYMENT 3 PRESENTER", p0.toString())
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

    private fun addTotalPaymentToTransaction(total: Long, orderId: String) {
        transactionReference.child(orderId).child("payment").setValue(total)
    }

    override fun addBankToTransaction(bank: Bank, orderId: String) {
        transactionReference.child(orderId).child("bank").setValue(bank)
    }

}