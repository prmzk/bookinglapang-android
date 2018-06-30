package com.example.keviniswara.bookinglapang.admin.home.presenter

import com.example.keviniswara.bookinglapang.admin.home.AdminHomeContract
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AdminHomePresenter: AdminHomeContract.Presenter {

    private var mView: AdminHomeContract.View? = null

    private val fieldReference: DatabaseReference = Database.database.getReference("fields")

    override fun bind(view: AdminHomeContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveFieldList() {
        val fields: MutableList<Field?>? = mutableListOf()
        fieldReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0 != null) {
                    for (fieldSnapshot in p0.children) {
                        val field = fieldSnapshot.getValue(Field::class.java)
                        fields!!.add(field)
                    }
                }
                mView?.initListOfFields(fields)
            }

            override fun onCancelled(p0: DatabaseError?) {
                mView?.initListOfFields(null)
            }
        })
    }
}