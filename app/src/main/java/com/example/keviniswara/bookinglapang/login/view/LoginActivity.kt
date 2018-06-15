package com.example.keviniswara.bookinglapang.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapang.MainActivity
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.ActivitySignInBinding
import com.example.keviniswara.bookinglapang.login.LoginContract
import com.example.keviniswara.bookinglapang.login.presenter.LoginPresenter

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
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    override fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setErrorMessage(text: String) {
        mBinding.errorMessage.text = text
    }

    override fun initPresenter(): LoginContract.Presenter {
        val presenter: LoginPresenter = LoginPresenter()
        return presenter
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    override fun getEmail(): String {
        return mBinding.email.text.toString()
    }

    override fun getPassword(): String {
        return mBinding.password.text.toString()
    }

    override fun getActivity(): Activity {
        return this
    }
}