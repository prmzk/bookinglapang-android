package com.example.keviniswara.bookinglapang.admin.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.status.AdminStatusContract
import com.example.keviniswara.bookinglapang.admin.status.presenter.AdminStatusPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminStatusBinding

class AdminStatusFragment: Fragment(), AdminStatusContract.View {
    private lateinit var mBinding: FragmentAdminStatusBinding

    private lateinit var mPresenter: AdminStatusContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): AdminStatusContract.Presenter {
        return AdminStatusPresenter()
    }
}