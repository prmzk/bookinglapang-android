package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.user.home.SearchFieldContract
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class SearchFieldPresenter : SearchFieldContract.Presenter {

    private var mView: SearchFieldContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    private val usersReference: DatabaseReference = database.getReference("").child("users")

    override fun bind(view: SearchFieldContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveListOfFieldFromFirebase() {
        var listOfField = mutableListOf<String>()
        fieldReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                fetchDataFieldName(p0, listOfField)

                mView!!.initListOfFieldDropdown(listOfField)
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }

    private fun fetchDataFieldName(dataSnapshot: DataSnapshot?, listOfField: MutableList<String>) {
        listOfField.clear()
        for (ds in dataSnapshot!!.children) {
            val name = ds.getValue(Field::class.java)!!.field_id
            listOfField.add(name)
        }
    }

    override fun retrieveListOfSportFromFirebase(fieldName: String) {
        var listOfSport = mutableListOf<String>()
        fieldReference.child(fieldName).child("sports").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                fetchDataSportName(p0, listOfSport)

                mView!!.initListOfSportDropdown(listOfSport)
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }

    private fun fetchDataSportName(dataSnapshot: DataSnapshot?, listOfSport: MutableList<String>) {
        listOfSport.clear()
        for (ds in dataSnapshot!!.children) {
            val name = ds.getValue(Field.Sport::class.java)!!.sport_name
            listOfSport.add(name)
        }
    }

    override fun addOrderToFirebase() {
        val fieldName = mView!!.getFieldName()
        val sportName = mView!!.getSport()
        val date = mView!!.getDate()
        val startHour = mView!!.getStartHour()
        val finishHour = mView!!.getFinishHour()
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val order = Order(userEmail, date, finishHour, fieldName, sportName, startHour, 0, 0, uuid)
        Database.addNewOrder(order)
    }

    override fun sendOrderNotificationToFieldKeeper() {
        val notification = User.Notification(FirebaseAuth.getInstance().currentUser!!.uid, "New field order")
        sendNotificationDataToFieldKeeper(mView!!.getFieldName(), notification)
    }

    private fun sendNotificationDataToFieldKeeper(fieldName: String, notification: User.Notification) {
        usersReference.orderByChild("field").equalTo(fieldName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                for(ds in dataSnapshot!!.children) {
                    val uid = ds.key
                    Database.addNotification(uid, notification)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }
}