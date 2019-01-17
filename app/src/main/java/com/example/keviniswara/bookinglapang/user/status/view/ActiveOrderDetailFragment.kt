package com.example.keviniswara.bookinglapang.user.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentActiveOrderDetailBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.status.ActiveOrderDetailContract
import com.example.keviniswara.bookinglapang.user.status.presenter.ActiveOrderDetailPresenter

class ActiveOrderDetailFragment: Fragment(), ActiveOrderDetailContract.View {

    private lateinit var mPresenter: ActiveOrderDetailContract.Presenter

    private lateinit var mBinding: FragmentActiveOrderDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_order_detail, container, false)

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

        initButton(status)

        mBinding.payButton.setOnClickListener({
            moveToPayment(order)
        })

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId)

        return mBinding.root
    }

    override fun setFieldId(fieldId: String) {
        mBinding.fieldId.setText(fieldId)
    }

    override fun setSport(sport: String) {
        mBinding.sport.setText(sport)
    }

    override fun setStartHour(startHour: String) {
        mBinding.startHour.setText(startHour)
    }

    override fun setEndHour(endHour: String) {
        mBinding.endHour.setText(endHour)
    }

    override fun setDate(date: String) {
        mBinding.date.setText(date)
    }

    override fun setStatusNotVerified() {
        mBinding.verificationButton.setBackgroundResource(R.drawable.button_grey)
        mBinding.paidButton.setBackgroundResource(R.drawable.button_grey)
    }

    override fun setStatusNotPaid() {
        mBinding.verificationButton.text = "Sudah diverifikasi"
        mBinding.verificationButton.setBackgroundResource(R.drawable.button_green)
        mBinding.paidButton.setBackgroundResource(R.drawable.button_grey)
    }

    override fun setStatusCancelled() {
        mBinding.verificationButton.setBackgroundResource(R.drawable.button_grey)
        mBinding.paidButton.setBackgroundResource(R.drawable.button_grey)
    }

    override fun initPresenter(): ActiveOrderDetailContract.Presenter {
        return ActiveOrderDetailPresenter()
    }

    override fun initButton(status: String) {
        when (status) {
            "0" -> {
                mBinding.verificationButton.visibility = View.VISIBLE
                mBinding.verifiedButton.visibility = View.GONE
                mBinding.paidButton.visibility = View.VISIBLE
                mBinding.payButton.visibility = View.GONE
                mBinding.failed.visibility = View.GONE
            }
            "1" -> {
                mBinding.verificationButton.visibility = View.GONE
                mBinding.verifiedButton.visibility = View.VISIBLE
                mBinding.paidButton.visibility = View.GONE
                mBinding.payButton.visibility = View.VISIBLE
                mBinding.failed.visibility = View.GONE
            }
            "3" -> {
                mBinding.verificationButton.visibility = View.GONE
                mBinding.verifiedButton.visibility = View.GONE
                mBinding.paidButton.visibility = View.GONE
                mBinding.payButton.visibility = View.GONE
                mBinding.failed.visibility = View.VISIBLE
            }
        }
    }

    override fun moveToPayment(orderDetail: Order) {
        val arguments = Bundle()
        val fragment = Payment1Fragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putLong("deadline", orderDetail.deadline)
        arguments.putString("orderId", orderDetail.orderId)
        arguments.putString("orderId", orderDetail.orderId)
        fragment.arguments = arguments
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.content, fragment)?.addToBackStack(fragment
                .javaClass.simpleName)
        ft?.commit()
    }
}