package com.example.keviniswara.bookinglapang.admin.profile.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.profile.AdminProfileContract
import com.example.keviniswara.bookinglapang.admin.profile.presenter.AdminProfilePresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminProfileBinding

class AdminProfileFragment: Fragment(), AdminProfileContract.View {
    private lateinit var mBinding: FragmentAdminProfileBinding

    private lateinit var mPresenter: AdminProfileContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_profile, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): AdminProfileContract.Presenter {
        return AdminProfilePresenter()
    }
}