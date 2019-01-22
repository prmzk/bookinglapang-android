package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.user.home.SearchFieldContract
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.model.User
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class SearchFieldPresenter : SearchFieldContract.Presenter {

    private var mView: SearchFieldContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    private val pricingReference: DatabaseReference = database.getReference("").child("prices_list")

    private val pricingTextReference: DatabaseReference = database.getReference("").child("prices")

    private val usersReference: DatabaseReference = database.getReference("").child("users")

    override fun bind(view: SearchFieldContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun getPrice(){
        val fieldid = mView!!.getFieldName()
        val sportName = mView!!.getSport()

        val date = mView!!.getDate()
        val day = dateToIntDay(date)

        val beginTime = mView!!.getStartHour().toInt()
        val endTime = mView!!.getFinishHour().toInt()

        var price:Long = 0

        pricingReference.child(fieldid).child(sportName).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0!!.child(day.toString()).children) {
                    if (ds.key!!.toInt() in beginTime until endTime) {
                        price += ds.getValue(String::class.java)!!.toInt()
                    }
                }

                mView!!.updatePrice(price.toString())
            }

            override fun onCancelled(p0: DatabaseError) {
                mView!!.updatePrice("Error")
            }

        })


    }

    override fun checkTimeValid() {
        val fieldid = mView!!.getFieldName()
        val sportName = mView!!.getSport()

        val date = mView!!.getDate()
        val day = dateToIntDay(date)

        val beginTime = mView!!.getStartHour().toInt()
        val endTime = mView!!.getFinishHour().toInt()

        pricingReference.child(fieldid).child(sportName).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChild(day.toString())){
                    var valid:Boolean = true
                    for(hour in beginTime until endTime){
                        if(!p0.child(day.toString()).hasChild(hour.toString())){
                            valid = false
                        }
                    }

                    if(valid){
                        getPrice()
                    }else{
                        getValidTime()
                    }
                }else{
                    getValidTime()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    override fun getValidTime() {
        val fieldid = mView!!.getFieldName()
        val sportName = mView!!.getSport()

        var validTime:String = ""

        pricingTextReference.child(fieldid).child(sportName).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                validTime = "Waktu yang tersedia\n"
                for(time in p0.children){
                    val timeItem = time.getValue(Field.PriceTimeDayRange::class.java)
                    if(timeItem!=null){
                        validTime += timeItem.day_range.toString()
                        validTime += " jam "
                        validTime += timeItem.hour_range.toString()
                    }

                    validTime += "\n"
                }

                validTime += "Harap cek waktu dan tanggal"

                mView!!.updatePrice("-")
                mView!!.showToastMessage(validTime)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    override fun retrieveListOfFieldFromFirebase() {
        var listOfField = mutableListOf<String>()
        fieldReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                fetchDataFieldName(p0, listOfField)

                mView!!.initListOfFieldDropdown(listOfField)
            }

            override fun onCancelled(p0: DatabaseError) {

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
            override fun onDataChange(p0: DataSnapshot) {
                fetchDataSportName(p0, listOfSport)

                mView!!.initListOfSportDropdown(listOfSport)
            }

            override fun onCancelled(p0: DatabaseError) {

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
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(ds in dataSnapshot!!.children) {
                    val uid = ds.key
                    Database.addNotification(uid!!, notification)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun dateToIntDay(dateString: String) : Int {
        val date = SimpleDateFormat("dd/MM/yy", Locale.US).parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_WEEK)
    }
}