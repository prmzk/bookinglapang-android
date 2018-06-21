package com.example.keviniswara.bookinglapang.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentActiveOrderDetailBinding
import com.example.keviniswara.bookinglapang.status.ActiveOrderDetailContract
import com.example.keviniswara.bookinglapang.status.presenter.ActiveOrderDetailPresenter

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

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId)

        return mBinding.root
    }

    override fun setFieldId(fieldId: String) {
        mBinding.fieldId.text = fieldId
    }

    override fun setSport(sport: String) {
        mBinding.sport.text = sport
    }

    override fun setStartHour(startHour: String) {
        mBinding.startHour.text = startHour
    }

    override fun setEndHour(endHour: String) {
        mBinding.endHour.text = endHour
    }

    override fun setDate(date: String) {
        mBinding.date.text = date
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

    override fun setStatusDone() {
        mBinding.verificationButton.text = "Sudah diverifikasi"
        mBinding.paidButton.text = "Sudah dibayar"
        mBinding.verificationButton.setBackgroundResource(R.drawable.button_green)
        mBinding.paidButton.setBackgroundResource(R.drawable.button_green)
    }

    override fun setStatusCancelled() {
        mBinding.verificationButton.setBackgroundResource(R.drawable.button_grey)
        mBinding.paidButton.setBackgroundResource(R.drawable.button_grey)
    }

    override fun initPresenter(): ActiveOrderDetailContract.Presenter {
        return ActiveOrderDetailPresenter()
    }
}