package com.example.keviniswara.bookinglapangtest.user.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.databinding.FragmentPayment3Binding
import com.example.keviniswara.bookinglapangtest.user.status.Payment3Contract
import com.example.keviniswara.bookinglapangtest.user.status.presenter.Payment3Presenter

class Payment3Fragment: Fragment(), Payment3Contract.View {

    private lateinit var mPresenter: Payment3Contract.Presenter

    private lateinit var mBinding: FragmentPayment3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_3, container, false)

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
        val bankName = arguments!!.getString("bankName")

        mPresenter.getBankDetail(bankName, orderId)

        mPresenter.countTotalPayment(orderId, fieldId, sport, startHour, endHour, date)

        mBinding.buttonPay.setOnClickListener({
            startHomeFragment()
            showToastMessage("Silakan tunggu konfirmasi dari administrator.")
        })

        return mBinding.root
    }

    override fun initPresenter(): Payment3Contract.Presenter {
        return Payment3Presenter()
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun startHomeFragment() {
        fragmentManager?.popBackStack()
        fragmentManager?.popBackStack()
        fragmentManager?.popBackStack()
        fragmentManager?.popBackStack()
    }

    override fun setName(name: String) {
        mBinding.name.setText(name)
    }

    override fun setBillsNumber(billsNumber: String) {
        mBinding.billsNumber.setText(billsNumber)
    }

    override fun setTotal(total: String) {
        mBinding.total.setText(total)
    }
}