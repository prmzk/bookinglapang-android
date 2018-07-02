package com.example.keviniswara.bookinglapang.admin.profile.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.profile.AdminProfileContract
import com.example.keviniswara.bookinglapang.admin.profile.presenter.AdminProfilePresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminProfileBinding
import com.example.keviniswara.bookinglapang.login.view.LoginActivity

class AdminProfileFragment: Fragment(), AdminProfileContract.View {

    private lateinit var mBinding: FragmentAdminProfileBinding

    private lateinit var mPresenter: AdminProfileContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_profile, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.buttonLogout.setOnClickListener({
            mPresenter.logout()
        })

        mPresenter.getProfileFromDatabase()

        mBinding.buttonSave.setOnClickListener({
            mPresenter.save()
        })

        return mBinding.root
    }

    override fun initPresenter(): AdminProfileContract.Presenter {
        return AdminProfilePresenter()
    }

    override fun getName(): String {
        return mBinding.name.text.toString()
    }

    override fun getPhoneNumber(): String {
        return mBinding.phoneNumber.text.toString()
    }

    override fun setName(name: String) {
        mBinding.name.setText(name)
    }

    override fun setPhoneNumber(phoneNumber: String) {
        mBinding.phoneNumber.setText(phoneNumber)
    }

    override fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun hideKeyboard() {
        val imm: InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.parentLayout.windowToken, 0)
    }
}