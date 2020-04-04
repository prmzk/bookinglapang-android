package com.example.keviniswara.bookinglapang.user.home.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.FieldDetailContract
import com.example.keviniswara.bookinglapang.user.home.MakeGameContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class FieldDetailPresenter : FieldDetailContract.Presenter {

    private var mView: FieldDetailContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun bind(view: FieldDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveListOfFieldFromFirebase(field_name: String) {
        var listOfField = mutableListOf<String>()
        fieldReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    for (fieldData in dataSnapshot.children) {
                        if (fieldData.hasChild("sports")) {
                            fieldReference.child(field_name)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(fieldDataSnapshot: DataSnapshot) {
                                            val name = fieldDataSnapshot.getValue(Field::class.java)?.address as String
                                            val fieldData = fieldDataSnapshot.getValue(Field::class.java)
                                            // mView?.initAddress(name)
                                            mView?.initFieldData(fieldData)
//                                            mView?.initListOfFieldDropdown(listOfField)
                                        }

                                        override fun onCancelled(p0: DatabaseError) {

                                        }
                                    })
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
//                mView?.showToastMessage("Failed to retrieve field list from database.")
            }
        })
    }

    override fun retrieveListOfSportFromFirebase(fieldName: String) {
        var listOfSport = mutableListOf<String?>()
        fieldReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    for (fieldData in dataSnapshot.children) {
                        if (fieldData.hasChild("sports")) {
                            listOfSport.clear()
                            fieldReference.child(fieldName).child("sports")
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(sportDataSnapshot: DataSnapshot) {

                                            if (sportDataSnapshot != null) {
                                                for (sport in sportDataSnapshot.children) {
                                                    val name = sport.getValue(Field.Sport::class.java)?.sport_name as String

                                                    if (name.isNotBlank() && !listOfSport.contains(name)) {
                                                        listOfSport.add(name)
                                                    }
                                                }
                                            }

                                            mView?.initListofSport(listOfSport)
                                        }

                                        override fun onCancelled(p0: DatabaseError) {

                                        }
                                    })
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
//                mView?.showToastMessage("Failed to retrieve sport list from database.")
            }
        })
    }

    override fun retrieveListOfImagesFromFirebase(fieldName: String) {
        var listOfImages = mutableListOf<String?>()
        fieldReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    for (fieldData in dataSnapshot.children) {
                        if (fieldData.hasChild("image")) {
                            listOfImages.clear()
                            fieldReference.child(fieldName).child("image")
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(imageDataSnapshot: DataSnapshot) {

                                            if (imageDataSnapshot != null) {
                                                for (image in imageDataSnapshot.children) {
                                                    val name = image.getValue(Field.Image::class.java)?.image_name as String

                                                    if (name.isNotBlank() && !listOfImages.contains(name)) {
                                                        listOfImages.add(name)
                                                    }
                                                }
                                            }
                                            mView?.initListofimage(listOfImages)
                                        }

                                        override fun onCancelled(p0: DatabaseError) {

                                        }
                                    })
                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
//                mView?.showToastMessage("Failed to retrieve sport list from database.")
            }
        })
    }

}