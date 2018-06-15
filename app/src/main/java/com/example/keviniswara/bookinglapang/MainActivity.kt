package com.example.keviniswara.bookinglapang

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.widget.FrameLayout
import com.example.keviniswara.bookinglapang.R.drawable.*
import com.example.keviniswara.bookinglapang.databinding.ActivityMainBinding
import com.example.keviniswara.bookinglapang.home.view.HomeFragment
import com.example.keviniswara.bookinglapang.order.view.OrderFragment
import com.example.keviniswara.bookinglapang.profile.view.ProfileFragment
import com.example.keviniswara.bookinglapang.status.view.StatusFragment
import android.util.TypedValue
import android.support.design.internal.BottomNavigationMenuView
import android.view.View
import android.support.design.internal.BottomNavigationItemView
import com.example.keviniswara.bookinglapang.utils.BottomNavigationViewHelper






class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.bottom_nav_bar_1 -> {
                    val fragment = HomeFragment()
                    addFragment(fragment)
                    item.setIcon(home_green)
                    return true
                }
                R.id.bottom_nav_bar_2 -> {
                    val fragment = OrderFragment()
                    addFragment(fragment)
                    item.setIcon(order_green)
                    return true
                }
                R.id.bottom_nav_bar_3 -> {
                    var fragment = StatusFragment()
                    addFragment(fragment)
                    item.setIcon(status_green)
                    return true
                }
                R.id.bottom_nav_bar_4 -> {
                    var fragment = ProfileFragment()
                    addFragment(fragment)
                    item.setIcon(profile_green)
                    return true
                }
            }
            return false
        }

    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        content = mBinding.content

        val navigation = findViewById(R.id.bottom_nav_bar) as BottomNavigationView
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

        val fragment = HomeFragment()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }
}
