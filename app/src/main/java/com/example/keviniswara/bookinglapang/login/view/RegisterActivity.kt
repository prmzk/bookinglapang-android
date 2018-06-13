package com.example.keviniswara.bookinglapang.login.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mBinding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_register)

        mBinding.buttonRegister.setOnClickListener(View.OnClickListener {
            
        })
    }
}