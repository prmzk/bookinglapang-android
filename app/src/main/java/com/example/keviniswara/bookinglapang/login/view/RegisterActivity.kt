package com.example.keviniswara.bookinglapang.login.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.example.keviniswara.bookinglapang.MainActivity
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivityRegisterBinding
import com.example.keviniswara.bookinglapang.login.RegisterContract
import com.example.keviniswara.bookinglapang.login.presenter.RegisterPresenter
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var mPresenter: RegisterContract.Presenter

    private lateinit var mBinding: ActivityRegisterBinding

    private lateinit var mAuth: FirebaseAuth

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

    override fun getAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    override fun getErrorMessage(): TextView {
        return mBinding.errorMessage
    }

    override fun initPresenter(): RegisterContract.Presenter {
        val presenter: RegisterPresenter = RegisterPresenter()
        return presenter
    }

    override fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}