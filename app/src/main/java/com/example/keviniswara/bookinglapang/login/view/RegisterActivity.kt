package com.example.keviniswara.bookinglapang.login.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivityRegisterBinding
import com.example.keviniswara.bookinglapang.login.RegisterContract
import com.example.keviniswara.bookinglapang.login.presenter.RegisterPresenter

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var mPresenter: RegisterContract.Presenter

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_register)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.buttonRegister.setOnClickListener(View.OnClickListener {
            mPresenter.register()
        })
    }

    override fun getEmail(): String {
        return mBinding.email.text.toString()
    }

    override fun getName(): String {
        return mBinding.name.text.toString()
    }

    override fun getPhoneNumber(): String {
        return mBinding.phoneNumber.text.toString()
    }

    override fun getPassword(): String {
        return mBinding.password.text.toString()
    }

    override fun getPasswordConfirmation(): String {
        return mBinding.confirmPassword.text.toString()
    }

    override fun initPresenter(): RegisterContract.Presenter {
        val presenter: RegisterPresenter = RegisterPresenter()
        return presenter
    }
}