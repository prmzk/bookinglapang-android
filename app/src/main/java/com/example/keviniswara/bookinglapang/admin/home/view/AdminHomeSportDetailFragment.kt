package com.example.keviniswara.bookinglapang.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeSportDetailContract
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomeSportDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeSportDetailBinding

class AdminHomeSportDetailFragment: Fragment(), AdminHomeSportDetailContract.View {

    private lateinit var mBinding: FragmentAdminHomeSportDetailBinding

    private lateinit var mPresenter: AdminHomeSportDetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home_sport_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeSportDetailContract.Presenter {
        return AdminHomeSportDetailPresenter()
    }
}