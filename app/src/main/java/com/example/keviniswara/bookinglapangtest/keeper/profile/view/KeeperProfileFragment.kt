package com.example.keviniswara.bookinglapangtest.keeper.profile.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.databinding.FragmentKeeperProfileBinding
import com.example.keviniswara.bookinglapangtest.keeper.profile.KeeperProfileContract
import com.example.keviniswara.bookinglapangtest.keeper.profile.presenter.KeeperProfilePresenter
import com.example.keviniswara.bookinglapangtest.login.view.LoginActivity

class KeeperProfileFragment : Fragment(), KeeperProfileContract.View {

    private lateinit var mBinding: FragmentKeeperProfileBinding

    private lateinit var mPresenter: KeeperProfileContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keeper_profile,
                container, false)

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

    override fun initPresenter(): KeeperProfileContract.Presenter {
        return KeeperProfilePresenter()
    }

    override fun getKeeperName(): String {
        return mBinding.keeperName.text.toString()
    }

    override fun getFieldName(): String {
        return mBinding.fieldName.text.toString()
    }

    override fun getPhoneNumber(): String {
        return mBinding.phoneNumber.text.toString()
    }

    override fun setKeeperName(name: String) {
        mBinding.keeperName.setText(name)
    }

    override fun setFieldName(name: String) {
        mBinding.fieldName.setText(name)
    }

    override fun setPhoneNumber(phoneNumber: String) {
        mBinding.phoneNumber.setText(phoneNumber)
    }

    override fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun hideKeyboard() {
        val imm: InputMethodManager? = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}