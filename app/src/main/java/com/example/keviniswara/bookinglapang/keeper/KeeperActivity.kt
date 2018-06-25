package com.example.keviniswara.bookinglapang.keeper

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivityKeeperBinding
import com.example.keviniswara.bookinglapang.keeper.order.view.KeeperOrderFragment
import com.example.keviniswara.bookinglapang.keeper.profile.view.KeeperProfileFragment
import com.example.keviniswara.bookinglapang.keeper.status.view.KeeperStatusFragment
import com.example.keviniswara.bookinglapang.utils.BottomNavigationViewHelper

class KeeperActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_nav_bar_2 -> {
                val fragment = KeeperOrderFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_bar_3 -> {
                var fragment = KeeperStatusFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_bar_4 -> {
                var fragment = KeeperProfileFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding: ActivityKeeperBinding = DataBindingUtil.setContentView(this, R.layout.activity_keeper)

        content = mBinding.content

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

        val fragment = KeeperOrderFragment()
        addFragment(fragment)
    }

    @SuppressLint("CommitTransaction")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {

            val stackCount = fragmentManager.backStackEntryCount
            var exit: Boolean = false
            var popStack: Boolean = false

            if (supportFragmentManager.findFragmentByTag("KeeperOrderFragment") != null &&
                    supportFragmentManager.findFragmentByTag("KeeperOrderFragment").isVisible) {
                popStack = true
                exit = true
            } else if (supportFragmentManager.findFragmentByTag("KeeperStatusFragment") != null &&
                    supportFragmentManager.findFragmentByTag("KeeperStatusFragment").isVisible) {
                popStack = true
                exit = true
            } else if (supportFragmentManager.findFragmentByTag("KeeperProfileFragment") != null &&
                    supportFragmentManager.findFragmentByTag("KeeperProfileFragment").isVisible) {
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