package com.example.keviniswara.bookinglapang.login.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivitySignInBinding
import com.example.keviniswara.bookinglapang.login.LoginContract
import com.example.keviniswara.bookinglapang.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_sign_in.view.*

class LoginActivity : AppCompatActivity(), LoginContract.View  {

    private lateinit var mPresenter: LoginContract.Presenter

    private lateinit var mBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_sign_in)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.buttonSignIn.setOnClickListener(View.OnClickListener {
            mPresenter.login()
        })

        mBinding.buttonRegister.setOnClickListener(View.OnClickListener {
            /*val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()*/
        })
    }
    override fun initPresenter(): LoginContract.Presenter {
        val presenter: LoginPresenter = LoginPresenter()
        return presenter
    }

    override fun getEmail(): String {
        return mBinding.email.text.toString()
    }

    override fun getPassword(): String {
        return mBinding.password.text.toString()
    }
}