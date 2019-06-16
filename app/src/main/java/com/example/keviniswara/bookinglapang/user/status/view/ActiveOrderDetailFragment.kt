package com.example.keviniswara.bookinglapang.user.status.view

import android.databinding.DataBindingUtil
import android.opengl.Visibility
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
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_active_order_detail.*


class ActiveOrderDetailFragment: Fragment(), ActiveOrderDetailContract.View {

    private lateinit var mPresenter: ActiveOrderDetailContract.Presenter

    private lateinit var mBinding: FragmentActiveOrderDetailBinding

    private var orderDeadline:Long = 0

    private val mHandler = Handler()
    val mHandlerTask: Runnable = object : Runnable {
        override fun run() {
            updateTime()
            mHandler.postDelayed(this, 1000*60)
        }
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mHandlerTask)
    }

    override fun onResume() {
        super.onResume()
        mHandlerTask.run()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_order_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        val sport = arguments!!.getString("sport")
        val startHour = arguments!!.getString("startHour")
        val endHour = arguments!!.getString("endHour")
        val customerEmail = arguments!!.getString("customerEmail")
        val customerName = arguments!!.getString("customerName")
        val status = arguments!!.getString("status")
        val date = arguments!!.getString("date")
        val fieldId = arguments!!.getString("fieldId")
        val deadline = arguments!!.getLong("deadline")
        val orderId = arguments!!.getString("orderId")
        val feedback = arguments!!.getString("feedback")

        orderDeadline = deadline

        val order = Order(customerName,customerEmail, date, endHour, fieldId, sport, startHour, status.toInt(), deadline, feedback, orderId)

        initButton(status)

        mBinding.payButton.setOnClickListener({
            moveToPayment(order)
        })

        mPresenter.initOrderDetail(sport, startHour, endHour, customerEmail, status, date, fieldId, feedback)

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

    override fun setFeedback(feedback: String) {
        mBinding.feedback.setText(feedback)
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
        mBinding.verificationButton.visibility = View.GONE
        mBinding.verifiedButton.visibility = View.GONE
        mBinding.paidButton.visibility = View.GONE
        mBinding.payButton.visibility = View.GONE
        mBinding.failed.visibility = View.GONE
        mBinding.booked.visibility = View.GONE
        mBinding.verificationButton.visibility = View.GONE

        when(status){
            "0" -> {
                mBinding.paidButton.visibility = View.VISIBLE
                mBinding.verificationButton.visibility = View.VISIBLE
            }
            "1" -> {
                mBinding.verifiedButton.visibility = View.VISIBLE
                mBinding.payButton.visibility = View.VISIBLE
                mBinding.deadlineText.visibility = View.VISIBLE
            }
            "2" -> mBinding.booked.visibility = View.VISIBLE
            "3" -> mBinding.failed.visibility = View.VISIBLE
            "4" -> mBinding.waitingConfirmation.visibility = View.VISIBLE
        }
    }

    override fun moveToPayment(orderDetail: Order) {
        val arguments = Bundle()
        val fragment = Payment1Fragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("customerName",orderDetail.customerName)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putLong("deadline", orderDetail.deadline)
        arguments.putString("orderId", orderDetail.orderId)
        arguments.putString("feedback", orderDetail.feedback)

        fragment.arguments = arguments
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.content, fragment)?.addToBackStack(fragment
                .javaClass.simpleName)
        ft?.commit()
    }

    private fun updateTime(){
        val currTime = System.currentTimeMillis()
        if(orderDeadline!=0.toLong()){
            val diffTime = orderDeadline - currTime
            if(diffTime<0){
                Toast.makeText(activity, "Waktu pembayaran habis",Toast.LENGTH_LONG).show()
                fragmentManager!!.popBackStack()
            }else{
                val minutesDiff = diffTime/60000
                mBinding.deadlineText.setText(String.format(getString(R.string.order_status_deadline_fill),minutesDiff))
            }

        }
    }
}