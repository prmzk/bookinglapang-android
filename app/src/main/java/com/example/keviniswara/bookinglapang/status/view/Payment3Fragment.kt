package com.example.keviniswara.bookinglapang.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentPayment3Binding
import com.example.keviniswara.bookinglapang.status.Payment3Contract
import com.example.keviniswara.bookinglapang.status.presenter.Payment3Presenter

class Payment3Fragment: Fragment(), Payment3Contract.View {

    private lateinit var mPresenter: Payment3Contract.Presenter

    private lateinit var mBinding: FragmentPayment3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_3, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): Payment3Contract.Presenter {
        return Payment3Presenter()
    }
}