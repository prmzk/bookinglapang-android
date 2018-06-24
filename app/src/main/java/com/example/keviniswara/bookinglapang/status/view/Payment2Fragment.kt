package com.example.keviniswara.bookinglapang.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentPayment2Binding
import com.example.keviniswara.bookinglapang.status.Payment2Contract
import com.example.keviniswara.bookinglapang.status.presenter.Payment2Presenter

class Payment2Fragment : Fragment(), Payment2Contract.View {

    private lateinit var mBinding: FragmentPayment2Binding

    private lateinit var mPresenter: Payment2Contract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_2,
                container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        return mBinding.root
    }

    override fun initPresenter(): Payment2Contract.Presenter {
        return Payment2Presenter()
    }
}