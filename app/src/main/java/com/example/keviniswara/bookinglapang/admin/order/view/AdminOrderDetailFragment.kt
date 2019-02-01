package com.example.keviniswara.bookinglapang.admin.order.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.order.AdminOrderDetailContract
import com.example.keviniswara.bookinglapang.admin.order.presenter.AdminOrderDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminOrderDetailBinding

class AdminOrderDetailFragment : Fragment(), AdminOrderDetailContract.View {

    private lateinit var mBinding: FragmentAdminOrderDetailBinding
    private lateinit var mPresenter: AdminOrderDetailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_order_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        val orderId = arguments!!.getString("orderId")
        val sport = arguments!!.getString("sport")
        val startHour = arguments!!.getString("startHour")
        val endHour = arguments!!.getString("endHour")
        val customerEmail = arguments!!.getString("customerEmail")
        val status = arguments!!.getString("status")
        val date = arguments!!.getString("date")
        val fieldId = arguments!!.getString("fieldId")

        mPresenter.initOrderDetail(orderId, sport, startHour, endHour, customerEmail, status, date, fieldId)

        return mBinding.root
    }

    override fun initPresenter(): AdminOrderDetailPresenter {
        return AdminOrderDetailPresenter()
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

    override fun setOrderName(name: String) {
        mBinding.namaPemesan.setText(name);
    }

    override fun setOrderNum(num: String) {
        mBinding.noTelp.setText(num)
    }
}