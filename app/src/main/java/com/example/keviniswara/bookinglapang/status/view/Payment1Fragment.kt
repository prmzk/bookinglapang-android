package com.example.keviniswara.bookinglapang.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentPayment1Binding
import com.example.keviniswara.bookinglapang.status.Payment1Contract
import com.example.keviniswara.bookinglapang.status.presenter.Payment1Presenter
import com.example.keviniswara.bookinglapang.utils.Database

class Payment1Fragment: Fragment(), Payment1Contract.View {

    private lateinit var mPresenter: Payment1Contract.Presenter

    private lateinit var mBinding: FragmentPayment1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_1, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        val orderId = arguments!!.getString("orderId")
        Log.d("PAYMENT FRAGMENT 1", orderId)

        mBinding.buttonPay.setOnClickListener({

        })

        return mBinding.root
    }

    override fun initPresenter(): Payment1Contract.Presenter {
        return Payment1Presenter()
    }

    override fun getName(): String {
        return mBinding.name.text.toString()
    }

    override fun getPhoneNumber(): String {
        return mBinding.phoneNumber.text.toString()
    }
}