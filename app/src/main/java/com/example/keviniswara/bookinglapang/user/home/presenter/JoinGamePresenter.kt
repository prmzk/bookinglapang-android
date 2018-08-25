package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.JoinGameContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.example.keviniswara.bookinglapang.utils.Database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class JoinGamePresenter : JoinGameContract.Presenter {

    private var mView: JoinGameContract.View? = null
    private val fieldReference: DatabaseReference = database.getReference("").child("fields")

    override fun bind(view: JoinGameContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
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

    override fun retrieveJoinGameList(date: String, sport: String) {

        var joinGameList: MutableList<FindEnemy?>? = mutableListOf()

        val findEnemyRoot: DatabaseReference = Database.database.getReference("find_enemy")

        Database.addServerDate()
        val dateFormat = "dd/MM/yy HH:mm:ss"
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        cal.time = sdf.parse(mView?.getDate() + " 23:59:59")
        val timeRoot: DatabaseReference = Database.database.getReference("server_time")

        timeRoot.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                mView?.showToastMessage("Failed to get server time.")
            }

            override fun onDataChange(dateSnapshot: DataSnapshot?) {

                val dateInMillis: Long = dateSnapshot?.value as Long

                val calendarNow = Calendar.getInstance()
                calendarNow.timeInMillis = dateInMillis

                if (cal.after(calendarNow)) {

                    findEnemyRoot.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            mView?.initListOfJoinGame(null)
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            mView?.clearOrderList()

                            if (p0 != null) {
                                for (findEnemySnapshot in p0.children) {
                                    val joinGame = findEnemySnapshot.getValue<FindEnemy>(FindEnemy::class.java) ?: FindEnemy()
                                    if (joinGame.date == date && joinGame.sport.equals(sport)) {
                                        joinGameList!!.add(joinGame)
                                    }
                                }
                                mView?.initListOfJoinGame(joinGameList)
                            } else {
                                mView?.initListOfJoinGame(null)
                            }

                        }
                    })
                } else {
                    mView?.showToastMessage("Time can't be in the past.")
                }
            }
        })

        findEnemyRoot.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                mView?.initListOfJoinGame(null)
            }

            override fun onDataChange(p0: DataSnapshot?) {

                mView?.clearOrderList()

                if (p0 != null) {
                    for (findEnemySnapshot in p0.children) {
                        val joinGame = findEnemySnapshot.getValue<FindEnemy>(FindEnemy::class.java) ?: FindEnemy()
                        if (joinGame.date == date && joinGame.sport.equals(sport)) {
                            joinGameList!!.add(joinGame)
                        }
                    }
                    mView?.initListOfJoinGame(joinGameList)
                } else {
                    mView?.initListOfJoinGame(null)
                }

            }
        })
    }
}