package com.example.keviniswara.bookinglapang

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import com.example.keviniswara.bookinglapang.databinding.ActivityMainBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.home.view.HomeFragment
import com.example.keviniswara.bookinglapang.user.order.view.OrderFragment
import com.example.keviniswara.bookinglapang.user.profile.view.ProfileFragment
import com.example.keviniswara.bookinglapang.user.status.view.ActiveOrderDetailFragment
import com.example.keviniswara.bookinglapang.user.status.view.StatusFragment
import com.example.keviniswara.bookinglapang.utils.BottomNavigationViewHelper
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null
    private lateinit var mToolbar: Toolbar

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_nav_bar_1 -> {
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_bar_2 -> {
                val fragment = OrderFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_bar_3 -> {
                var fragment = StatusFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_bar_4 -> {
                var fragment = ProfileFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        content = mBinding.content

        mToolbar = mBinding.toolbar

        setSupportActionBar(mToolbar)

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val navHelper = BottomNavigationViewHelper()
        navHelper.removeShiftMode(navigation)
        val menuView = navigation.getChildAt(0) as BottomNavigationMenuView

        for (i in 0 until menuView.childCount) {

            // change icon size
            val iconView = menuView.getChildAt(i).findViewById<View>(android.support.design.R.id.icon)
            val layoutParams = iconView.getLayoutParams()
            val displayMetrics = resources.displayMetrics
            layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, displayMetrics).toInt()
            layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, displayMetrics).toInt()
            iconView.setLayoutParams(layoutParams)
        }

        val orderId = intent.getStringExtra("orderId")

        if(orderId!=null){
            val orderRef : DatabaseReference =  Database.database.getReference("orders")

            orderRef.orderByChild("orderId").equalTo(orderId).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    val fragment = HomeFragment()
                    addFragment(fragment)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val orderObj = p0.children.iterator().next().getValue(Order::class.java)

                    val fragment = HomeFragment()
                    addFragment(fragment)

                    if(orderObj!=null){
                        val arguments = Bundle()
                        val fragment = ActiveOrderDetailFragment()
                        arguments.putString("startHour", orderObj.startHour)
                        arguments.putString("endHour", orderObj.endHour)
                        arguments.putString("customerEmail", orderObj.customerEmail)
                        arguments.putString("customerName",orderObj.customerName)
                        arguments.putString("status", orderObj.status.toString())
                        arguments.putString("date", orderObj.date)
                        arguments.putString("sport", orderObj.sport)
                        arguments.putString("fieldId", orderObj.fieldId)
                        arguments.putLong("deadline", orderObj.deadline)
                        arguments.putString("orderId", orderObj.orderId)
                        fragment.arguments = arguments
                        addFragment(fragment)
                    }

                }

            })
        }else{
            val fragment = HomeFragment()
            addFragment(fragment)
        }
    }

    @SuppressLint("CommitTransaction")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {

            val stackCount = fragmentManager.backStackEntryCount
            var exit: Boolean = false
            var popStack: Boolean = false

            if (supportFragmentManager.findFragmentByTag("HomeFragment") != null &&
                    supportFragmentManager.findFragmentByTag("HomeFragment").isVisible) {
                popStack = true
                exit = true

            } else if (supportFragmentManager.findFragmentByTag("OrderFragment") != null &&
                    supportFragmentManager.findFragmentByTag("OrderFragment").isVisible) {
                popStack = true
                exit = true
            } else if (supportFragmentManager.findFragmentByTag("StatusFragment") != null &&
                    supportFragmentManager.findFragmentByTag("StatusFragment").isVisible) {
                popStack = true
                exit = true
            } else if (supportFragmentManager.findFragmentByTag("ProfileFragment") != null &&
                    supportFragmentManager.findFragmentByTag("ProfileFragment").isVisible) {
                popStack = true
                exit = true
            } else {
                supportFragmentManager.popBackStack()
            }

            if (popStack) {
                for (i in 0 until stackCount) {
                    fragmentManager.popBackStack()
                }
            }

            if (exit) {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }
}
