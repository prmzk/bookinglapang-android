package com.example.keviniswara.bookinglapangtest.user.home.presenter

import com.example.keviniswara.bookinglapangtest.user.home.JoinGameDetailContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot

class JoinGameDetailPresenter : JoinGameDetailContract.Presenter {

    private var mView: JoinGameDetailContract.View? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val usersReference: DatabaseReference = database.getReference("").child("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    private val findEnemyReference: DatabaseReference = database.getReference("").child("find_enemy")

    override fun bind(view: JoinGameDetailContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun retrieveCurrentUserDetail() {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val name = p0!!.child("name").value.toString()
                val phoneNumber = p0.child("phoneNumber").value.toString()
                mView!!.setVisitorName(name)
                mView!!.setVisitorPhoneNumber(phoneNumber)
            }

            override fun onCancelled(p0: DatabaseError) {
                mView?.showToastMessage("Could not retrieve user data from firebase")
            }
        })
    }

    override fun checkCurrentFindEnemy(hostEmail: String) {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val currentUserEmail = p0!!.child("email").value.toString()

                if (hostEmail == currentUserEmail) {
                    mView?.setupCancelButton()
                } else {
                    mView?.hideCancelButton()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                mView?.showToastMessage("Could not retrieve user data from firebase")
            }
        })
    }

    override fun deleteFindEnemy(findEnemyId: String) {
        findEnemyReference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                mView?.showToastMessage("Failed to delete order")
            }

            override fun onDataChange(findEnemiesSnapshot: DataSnapshot) {
                for (findEnemySnapshot in findEnemiesSnapshot!!.children) {

                    val uuid = findEnemySnapshot.child("id").getValue<String>(String::class.java)

                    if (findEnemyId == uuid) {
                        findEnemySnapshot.ref.removeValue()
                        mView?.showToastMessage("Order has been deleted")
                        mView?.goBackTwoTimes()
                    }
                }
            }

        })
    }
}