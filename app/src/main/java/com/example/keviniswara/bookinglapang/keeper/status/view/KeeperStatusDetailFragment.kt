package com.example.keviniswara.bookinglapang.keeper.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentKeeperStatusDetailBinding
import com.example.keviniswara.bookinglapang.keeper.status.KeeperStatusDetailContract
import com.example.keviniswara.bookinglapang.keeper.status.presenter.KeeperStatusDetailPresenter

class KeeperStatusDetailFragment : Fragment(), KeeperStatusDetailContract.View {

    private lateinit var mPresenter: KeeperStatusDetailPresenter
    private lateinit var mBinding: FragmentKeeperStatusDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keeper_status_detail, container, false)

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

        mBinding.availableButton.setOnClickListener(View.OnClickListener {
            mPresenter.setField(orderId, 0)
        })

        mBinding.notAvailableButton.setOnClickListener(View.OnClickListener {
            mPresenter.setField(orderId, 1)
        })
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

    override fun setUserPhoneNumber(phoneNumber: String) {
        mBinding.userPhone.setText(phoneNumber)
    }

    override fun setUserName(name: String) {
        mBinding.userName.setText(name)
    }

    override fun initPresenter(): KeeperStatusDetailPresenter {
        return KeeperStatusDetailPresenter()
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun finish() {
        val fm: FragmentManager? = fragmentManager
        fm?.popBackStack()
    }

    override fun setButtonVisibility(visible: Boolean) {
        if (visible) {
            mBinding.availableButton.visibility = View.VISIBLE
            mBinding.notAvailableButton.visibility = View.VISIBLE
        } else {
            mBinding.availableButton.visibility = View.GONE
            mBinding.notAvailableButton.visibility = View.GONE
        }
    }
}