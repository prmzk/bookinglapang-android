package com.example.keviniswara.bookinglapang.admin.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.status.AdminStatusDetailContract
import com.example.keviniswara.bookinglapang.admin.status.presenter.AdminStatusDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminStatusDetailBinding

class AdminStatusDetailFragment : Fragment(), AdminStatusDetailContract.View {

    private lateinit var mPresenter: AdminStatusDetailPresenter
    private lateinit var mBinding: FragmentAdminStatusDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_status_detail, container, false)

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

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId)

        mBinding.alreadyTransferButton.setOnClickListener(View.OnClickListener {
            mPresenter.alreadyTransfer(orderId)
        })

        mBinding.failedButton.setOnClickListener(View.OnClickListener {
            mPresenter.failed(orderId)
        })
        return mBinding.root
    }

    override fun initPresenter(): AdminStatusDetailPresenter {
        return AdminStatusDetailPresenter()
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

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun finish() {
        val fm: FragmentManager? = fragmentManager
        fm?.popBackStack()
    }
}