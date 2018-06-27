package com.example.keviniswara.bookinglapang.admin.order.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.order.AdminOrderContract
import com.example.keviniswara.bookinglapang.admin.order.presenter.AdminOrderPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminOrderBinding

class AdminOrderFragment: Fragment(), AdminOrderContract.View {

    private lateinit var mBinding: FragmentAdminOrderBinding

    private lateinit var mPresenter: AdminOrderContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_order, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): AdminOrderContract.Presenter {
        return AdminOrderPresenter()
    }
}