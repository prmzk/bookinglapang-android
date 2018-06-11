package com.example.keviniswara.bookinglapang

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.widget.FrameLayout
import com.example.keviniswara.bookinglapang.R.attr.content
import com.example.keviniswara.bookinglapang.databinding.ActivityMainBinding
import com.example.keviniswara.bookinglapang.home.view.HomeFragment
import com.example.keviniswara.bookinglapang.order.view.OrderFragment
import com.example.keviniswara.bookinglapang.profile.view.ProfileFragment
import com.example.keviniswara.bookinglapang.status.view.StatusFragment

class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.bottom_nav_bar_1 -> {
                    val fragment = HomeFragment()
                    addFragment(fragment)
                    return true
                }
                R.id.bottom_nav_bar_2 -> {
                    val fragment = OrderFragment()
                    addFragment(fragment)
                    return true
                }
                R.id.bottom_nav_bar_3 -> {
                    var fragment = StatusFragment()
                    addFragment(fragment)
                    return true
                }
                R.id.bottom_nav_bar_4 -> {
                    var fragment = ProfileFragment()
                    addFragment(fragment)
                    return true
                }
            }
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        content = mBinding.content
        val navigation = findViewById(R.id.bottom_nav_bar) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


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
