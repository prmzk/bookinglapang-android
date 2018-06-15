package com.example.keviniswara.bookinglapang.home.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mBinding : FragmentHomeBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false)

        val view: View = mBinding.root

        var parentHeight = 0
        var buttonHeight = 0
        var parentWidth = 0
        var buttonWidth = 0

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                parentHeight = view.height
                buttonHeight = mBinding.buttonCariLapangan.height
                parentWidth = view.width
                buttonWidth = mBinding.buttonCariLapangan.width

                val buttonCariLapanganY: Float = ((parentHeight - buttonHeight * 13 / 8)/ 2).toFloat()

                val buttonCariLawanY: Float = (buttonCariLapanganY + buttonHeight * 3 / 4)

                val buttonCariLapanganX: Float = ((parentWidth / 2 - buttonWidth * 5 / 6)).toFloat()

                val buttonCariLawanX: Float = (buttonCariLapanganX + buttonWidth * 2 / 3)

                mBinding.buttonCariLapangan.y = buttonCariLapanganY

                mBinding.buttonCariLawan.y = buttonCariLawanY

                mBinding.buttonCariLapangan.x = buttonCariLapanganX

                mBinding.buttonCariLawan.x = buttonCariLawanX

                if (parentHeight > 0 && buttonHeight > 0) {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })

        mBinding.buttonCariLapangan.setOnClickListener(View.OnClickListener {
            mBinding.lalala.setText("cari lapangan coy")
        })
        mBinding.buttonCariLawan.setOnClickListener(View.OnClickListener {
            mBinding.lalala.setText("cari lawan coy")
        })
        return view
    }
}
