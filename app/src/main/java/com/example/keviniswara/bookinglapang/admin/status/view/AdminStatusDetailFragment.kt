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
import com.example.keviniswara.bookinglapang.utils.TextUtils
import java.time.Instant
import java.time.ZoneId
import java.util.*

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
        val customerName = arguments!!.getString("customerName")
        val feedback = arguments!!.getString("feedback")
        val lastUpdate = arguments!!.getLong("lastUpdate")

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId, orderId,customerName, feedback, lastUpdate)

        setButtonVisibility(status)

        if(status!="0"){
            mBinding.feedback.keyListener = null;
        }

        mBinding.alreadyTransferButton.setOnClickListener(View.OnClickListener {
//            mPresenter.alreadyTransfer(orderId)
            mPresenter.setField(orderId, 4, "")
        })

        mBinding.failedButton.setOnClickListener(View.OnClickListener {
//            mPresenter.failed(orderId)
            mPresenter.setField(orderId, 3, "")
        })

        mBinding.availableButton.setOnClickListener(View.OnClickListener {
            mPresenter.setField(orderId, 0, mBinding.feedback.text.toString())
        })

        mBinding.notAvailableButton.setOnClickListener(View.OnClickListener {
            mPresenter.setField(orderId, 1, mBinding.feedback.text.toString())
        })

        return mBinding.root
    }

    private fun setButtonVisibility(status:String){
        mBinding.failedButton.visibility = View.GONE
        mBinding.alreadyTransferButton.visibility = View.GONE
        mBinding.waitingConfirmation.visibility = View.GONE
        mBinding.availableButton.visibility = View.GONE
        mBinding.notAvailableButton.visibility = View.GONE

        when(status){
            "0" -> {
                mBinding.availableButton.visibility = View.VISIBLE
                mBinding.notAvailableButton.visibility = View.VISIBLE
            }
            "1" -> {
                mBinding.failedButton.visibility = View.VISIBLE
                mBinding.alreadyTransferButton.visibility = View.VISIBLE
            }
            "4" -> {
                mBinding.waitingConfirmation.visibility = View.VISIBLE
            }
        }

    }

    override fun initPresenter(): AdminStatusDetailPresenter {
        return AdminStatusDetailPresenter()
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

    override fun setBank(bank: String) {
        mBinding.bankDestination.setText(bank)
    }

    override fun setRekOwner(name: String) {
        mBinding.rekeningOwner.setText(name)
    }

    override fun setPrice(price: Int) {
        mBinding.transferPrice.setText(TextUtils.convertToCurrency(price));
    }

    override fun setOrderOwner(name: String) {
        mBinding.orderUserName.setText(name)
    }

    override fun setFeedback(feedback: String) {
        mBinding.feedback.setText(feedback)
    }

    override fun setLastUpdate(lastUpdate: Long) {
        if(lastUpdate > 0){
            val date = Date(lastUpdate)
            mBinding.lastUpdate.setText("Last update : "+date.toString())
        }
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun finish() {
        val fm: FragmentManager? = fragmentManager
        fm?.popBackStack()
    }
}