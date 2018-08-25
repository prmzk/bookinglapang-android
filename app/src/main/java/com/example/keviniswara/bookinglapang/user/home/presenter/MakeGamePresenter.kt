package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.MakeGameContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class MakeGamePresenter : MakeGameContract.Presenter {

    private var mView: MakeGameContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun bind(view: MakeGameContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveListOfFieldFromFirebase(sportName: String) {
        var listOfField = mutableListOf<String>()
        fieldReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (fieldData in dataSnapshot.children) {
                        if (fieldData.hasChild("sports")) {
                            fieldReference.child(fieldData.key).child("sports")
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(sportDataSnapshot: DataSnapshot?) {

                                            if (sportDataSnapshot != null) {
                                                for (sport in sportDataSnapshot.children) {
                                                    val sportNameFromDatabase = sport
                                                            .getValue(Field.Sport::class.java)?.sport_name as String

                                                    if (sportName == sportNameFromDatabase) {
                                                        val fieldName = fieldData
                                                                .getValue(Field::class.java)?.field_id as String
                                                        listOfField.add(fieldName)

                                                        break
                                                    }
                                                }
                                            }
                                            mView?.initListOfFieldDropdown(listOfField)
                                        }

                                        override fun onCancelled(p0: DatabaseError?) {

                                        }
                                    })
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                mView?.showToastMessage("Failed to retrieve field list from database.")
            }
        })
    }

    override fun retrieveListOfSportFromFirebase() {
        var listOfSport = mutableListOf<String>()
        fieldReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (fieldData in dataSnapshot.children) {
                        if (fieldData.hasChild("sports")) {
                            listOfSport.clear()
                            fieldReference.child(fieldData.key).child("sports")
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(sportDataSnapshot: DataSnapshot?) {

                                            if (sportDataSnapshot != null) {
                                                for (sport in sportDataSnapshot.children) {
                                                    val name = sport.getValue(Field.Sport::class.java)?.sport_name as String

                                                    if (name.isNotBlank() && !listOfSport.contains(name)) {
                                                        listOfSport.add(name)
                                                    }
                                                }
                                            }

                                            mView?.initListOfSportDropdown(listOfSport)
                                        }

                                        override fun onCancelled(p0: DatabaseError?) {

                                        }
                                    })
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                mView?.showToastMessage("Failed to retrieve sport list from database.")
            }
        })
    }

    override fun addFindEnemyToFirebase() {

        var customerEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
        var customerPhone: String
        var customerName: String
        val date = mView?.getDate() ?: ""
        val fieldName = mView?.getFieldName() ?: ""
        val sportName = mView?.getSport() ?: ""
        val time = mView?.getTime() ?: ""
        val uuid = UUID.randomUUID().toString().replace("-", "")


        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                mView?.showToastMessage("Something went wrong, please try again.")
            }

            override fun onDataChange(p0: DataSnapshot?) {
                customerPhone = p0?.child("phoneNumber")?.getValue<String>(String::class.java) ?: ""
                customerName = p0?.child("name")?.getValue<String>(String::class.java) ?: ""

                if (customerEmail.isBlank() || date.isBlank() ||
                        fieldName.isBlank() || sportName.isBlank() || time.isBlank()) {
                    mView?.showToastMessage("Form must not be blank.")
                } else if (customerPhone.isBlank()) {
                    mView?.showToastMessage("Failed to get user's data.")
                } else {
                    val findEnemy = FindEnemy(uuid, customerEmail, customerName, customerPhone, date,
                            fieldName, sportName, time)

                    Database.addNewFindEnemy(findEnemy)
                    mView?.showToastMessage("Success add game to database.")
                }
                mView?.goBack()
            }
        })
    }
}