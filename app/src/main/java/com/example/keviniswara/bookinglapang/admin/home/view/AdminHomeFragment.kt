package com.example.keviniswara.bookinglapang.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeContract
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomePresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeBinding

class AdminHomeFragment: Fragment(), AdminHomeContract.View {

    private lateinit var mBinding: FragmentAdminHomeBinding

    private lateinit var mPresenter: AdminHomeContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeContract.Presenter {
        return AdminHomePresenter()
    }
}