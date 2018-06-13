package com.example.keviniswara.bookinglapang.login.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivitySignInBinding
import kotlinx.android.synthetic.main.activity_sign_in.view.*

class LoginActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mBinding: ActivitySignInBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_sign_in)

        mBinding.buttonSignIn.setOnClickListener(View.OnClickListener {

            var email = mBinding.email.text.toString()
            var password = mBinding.password.text.toString()

            if (email == "") {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT)
            } else if (password == "") {
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT)
            } else {
                // login
            }
        })

        mBinding.buttonRegister.setOnClickListener(View.OnClickListener {

            /*val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()*/
        })
    }
}