package com.example.keviniswara.bookinglapang.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentPayment2Binding
import com.example.keviniswara.bookinglapang.model.Order
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

        val sport = arguments!!.getString("sport")
        val startHour = arguments!!.getString("startHour")
        val endHour = arguments!!.getString("endHour")
        val customerEmail = arguments!!.getString("customerEmail")
        val status = arguments!!.getString("status")
        val date = arguments!!.getString("date")
        val fieldId = arguments!!.getString("fieldId")
        val deadline = arguments!!.getLong("deadline")
        val orderId = arguments!!.getString("orderId")

        setField(fieldId)
        setSport(sport)
        setDate(date)

        mPresenter.retrieveListOfBankFromFirebase()

        mBinding.buttonPay.setOnClickListener({
            moveToPayment3(orderId, getBankName())
        })

        return mBinding.root
    }

    override fun initPresenter(): Payment2Contract.Presenter {
        return Payment2Presenter()
    }

    override fun setField(fieldName: String) {
        mBinding.field.setText(fieldName)
    }

    override fun setSport(sportName: String) {
        mBinding.sport.setText(sportName)
    }

    override fun setDate(date: String) {
        mBinding.date.setText(date)
    }

    override fun initListOfBankDropdown(listOfBank: List<String>) {
        val adapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, listOfBank)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        mBinding.listOfBank.adapter = adapter
    }

    override fun getBankName(): String {
        return mBinding.listOfBank.selectedItem.toString()
    }

    override fun moveToPayment3(orderId: String, bankName: String) {
        val arguments = Bundle()
        val fragment = Payment3Fragment()
        arguments.putString("orderId", orderId)
        arguments.putString("bankName", bankName)
        fragment.arguments = arguments
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.content, fragment)?.addToBackStack(fragment
                .javaClass.simpleName)
        ft?.commit()
    }
}