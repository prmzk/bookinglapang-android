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
import com.example.keviniswara.bookinglapang.model.Order
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

        val sport = arguments!!.getString("sport")
        val startHour = arguments!!.getString("startHour")
        val endHour = arguments!!.getString("endHour")
        val customerEmail = arguments!!.getString("customerEmail")
        val status = arguments!!.getString("status")
        val date = arguments!!.getString("date")
        val fieldId = arguments!!.getString("fieldId")
        val deadline = arguments!!.getLong("deadline")
        val orderId = arguments!!.getString("orderId")

        val order = Order(customerEmail, date, endHour, fieldId, sport, startHour, status.toInt(), deadline, orderId)

        mBinding.buttonPay.setOnClickListener({
            mPresenter.addTransactionToFirebase(orderId)
            moveToPayment2(order)
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

    override fun moveToPayment2(orderDetail: Order) {
        val arguments = Bundle()
        val fragment = Payment2Fragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putLong("deadline", orderDetail.deadline)
        arguments.putString("orderId", orderDetail.orderId)
        fragment.arguments = arguments
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.content, fragment)?.addToBackStack(fragment
                .javaClass.simpleName)
        ft?.commit()
    }
}