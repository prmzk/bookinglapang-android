package com.example.keviniswara.bookinglapang.user.status.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Bank
import com.example.keviniswara.bookinglapang.model.Transaction
import com.example.keviniswara.bookinglapang.user.status.Payment3Contract
import com.google.firebase.database.*

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

    override fun getPayment(orderId: String) {
        transactionReference.child(orderId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val trans = p0.getValue(Transaction::class.java)
                if (trans != null) {
                    mView?.setTotal(trans.payment.toString())
                }
            }

        })
    }

    override fun addBankToTransaction(bank: Bank, orderId: String) {
        transactionReference.child(orderId).child("bank").setValue(bank)
    }

}