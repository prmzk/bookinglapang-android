package com.example.keviniswara.bookinglapang.keeper.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.InputType
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
        val feedback = arguments!!.getString("feedback")
        val request = arguments!!.getString("request")

        if(status!="0"){
            mBinding.feedback.keyListener = null
        }

        mBinding.request.keyListener = null

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId, feedback, request)

        mBinding.availableButton.setOnClickListener({
            mPresenter.setField(orderId, 0, mBinding.feedback.text.toString())
        })

        mBinding.notAvailableButton.setOnClickListener({
            mPresenter.setField(orderId, 1, mBinding.feedback.text.toString())
        })

        mBinding.confirmButton.setOnClickListener({
            mPresenter.setField(orderId,2,"")
        })

        mBinding.declineButton.setOnClickListener({
            mPresenter.setField(orderId,3,"")
        })
        return mBinding.root
    }

    override fun setFeedback(feedback: String){
        mBinding.feedback.setText(feedback)
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

    override fun setRequest(request: String) {
        mBinding.request.setText(request)
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

    override fun setButtonVisibility(status:Int) {
        mBinding.availableButton.visibility = View.GONE
        mBinding.notAvailableButton.visibility = View.GONE
        mBinding.confirmButton.visibility = View.GONE
        mBinding.declineButton.visibility = View.GONE

        if (status==0) {
            mBinding.availableButton.visibility = View.VISIBLE
            mBinding.notAvailableButton.visibility = View.VISIBLE
        } else if(status==4){
            mBinding.confirmButton.visibility = View.VISIBLE
            mBinding.declineButton.visibility = View.VISIBLE
        }
    }
}