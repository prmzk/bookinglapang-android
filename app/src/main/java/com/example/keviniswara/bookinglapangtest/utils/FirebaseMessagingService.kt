package com.example.keviniswara.bookinglapangtest.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.example.keviniswara.bookinglapangtest.MainActivity
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.admin.AdminActivity
import com.example.keviniswara.bookinglapangtest.keeper.KeeperActivity
import com.example.keviniswara.bookinglapangtest.login.view.LoginActivity
import com.example.keviniswara.bookinglapangtest.utils.Database.database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val usersReference: DatabaseReference = database.getReference("").child("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)

        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val status = p0?.child("status")?.getValue(Int::class.java)

                sendNotification(remoteMessage, status!!)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    fun sendNotification(remoteMessage: RemoteMessage?, status: Int) {

        val messageTitle: String = remoteMessage?.notification?.title!!
        val messageBody: String = remoteMessage.notification?.body!!

        val mBuilder = NotificationCompat.Builder(this, "notify_001")
        var ii: Intent = Intent(this, LoginActivity::class.java)
        if (status == 0) {
            ii = Intent(this, KeeperActivity::class.java)
        } else if (status == 1) {
            ii = Intent(this, MainActivity::class.java)
        } else if (status == 2) {
            ii = Intent(this, AdminActivity::class.java)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, ii, 0)
        val bigText: NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
        bigText.setBigContentTitle(messageTitle)
        bigText.setSummaryText(messageBody)

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
        mBuilder.setContentTitle(messageTitle)
        mBuilder.setContentText(messageBody)
        mBuilder.setPriority(Notification.PRIORITY_MAX)
        mBuilder.setStyle(bigText);

        val mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(getString(R.string.channel_id),
                    "Notification channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
            mNotificationManager.createNotificationChannel(channel)
        }

        mNotificationManager.notify(0, mBuilder.build())
    }
}