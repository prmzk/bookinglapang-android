package com.example.keviniswara.bookinglapang

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.keviniswara.bookinglapang.admin.AdminActivity
import com.example.keviniswara.bookinglapang.keeper.KeeperActivity
import com.example.keviniswara.bookinglapang.login.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SplashActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mAuth == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            val database: FirebaseDatabase = FirebaseDatabase.getInstance()

            val usersReference: DatabaseReference = database.getReference("").child("users")
                    .child(mAuth.uid)

            usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot?) {
                    val status = p0?.child("status")?.getValue(Int::class.java)

                    if (status != null) {
                        moveTo(status)
                    } else {
                        moveTo(-1)
                    }

                }

                override fun onCancelled(p0: DatabaseError?) {
                    moveTo(-1)
                }
            })
        }
    }

    fun moveTo(status: Int) {
        if (status == 0) {
            val intent = Intent(this, KeeperActivity::class.java)
            startActivity(intent)
            finish()
        } else if (status == 1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (status == 2) {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}