package com.example.keviniswara.bookinglapang.user.home.presenter

import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.JoinGameContract
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class JoinGamePresenter : JoinGameContract.Presenter {

    private var mView: JoinGameContract.View? = null

    override fun bind(view: JoinGameContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveJoinGameList(date: String, sport: String) {
        var joinGameList: MutableList<FindEnemy?>? = mutableListOf()

        val findEnemyRoot: DatabaseReference = Database.database.getReference("findEnemy")

        findEnemyRoot.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                mView?.initListOfJoinGame(null)
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0 != null) {
                    for (findEnemySnapshot in p0.children) {
                        val joinGame = findEnemySnapshot.getValue<FindEnemy>(FindEnemy::class.java)
                        if (joinGame!!.date.equals(date) && joinGame.sport.equals(sport)) {
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