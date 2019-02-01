package com.example.keviniswara.bookinglapang.keeper.order.view

import android.support.v4.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentKeeperOrderDetailBinding
import com.example.keviniswara.bookinglapang.keeper.order.KeeperOrderDetailContract
import com.example.keviniswara.bookinglapang.keeper.order.presenter.KeeperOrderDetailPresenter

class KeeperOrderDetailFragment : Fragment(), KeeperOrderDetailContract.View {

    private lateinit var mPresenter: KeeperOrderDetailPresenter
    private lateinit var mBinding: FragmentKeeperOrderDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keeper_order_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        val sport = arguments!!.getString("sport")
        val startHour = arguments!!.getString("startHour")
        val endHour = arguments!!.getString("endHour")
        val customerEmail = arguments!!.getString("customerEmail")
        val status = arguments!!.getString("status")
        val date = arguments!!.getString("date")
        val fieldId = arguments!!.getString("fieldId")
        val orderId = arguments!!.getString("orderId")

        mPresenter.initOrderDetail(orderId, sport, startHour, endHour, customerEmail, status, date, fieldId)

        return mBinding.root
    }

    override fun initPresenter(): KeeperOrderDetailPresenter {
        return KeeperOrderDetailPresenter()
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

    override fun setCustName(name: String) {
        mBinding.namaPemesan.setText(name)
    }

    override fun setCustNum(number: String) {
        mBinding.noTelp.setText(number)
    }
}