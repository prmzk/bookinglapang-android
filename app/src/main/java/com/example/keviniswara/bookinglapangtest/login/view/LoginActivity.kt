package com.example.keviniswara.bookinglapangtest.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapangtest.MainActivity
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.admin.AdminActivity
import com.example.keviniswara.bookinglapangtest.databinding.ActivitySignInBinding
import com.example.keviniswara.bookinglapangtest.keeper.KeeperActivity
import com.example.keviniswara.bookinglapangtest.login.LoginContract
import com.example.keviniswara.bookinglapangtest.login.presenter.LoginPresenter

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

    override fun moveTo(status: Int) {
        if (status == 0) {
            val intent = Intent(this, KeeperActivity::class.java)
            startActivity(intent)
            finish()
        } else if (status == 1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (status == 2) {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
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
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
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

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}