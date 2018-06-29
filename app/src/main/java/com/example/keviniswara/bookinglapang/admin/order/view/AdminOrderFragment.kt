package com.example.keviniswara.bookinglapang.admin.order.view

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.order.AdminOrderContract
import com.example.keviniswara.bookinglapang.admin.order.adapter.AdminOrderAdapter
import com.example.keviniswara.bookinglapang.admin.order.presenter.AdminOrderPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminOrderBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.utils.Database
import java.text.SimpleDateFormat
import java.util.*

class AdminOrderFragment: Fragment(), AdminOrderContract.View {

    private lateinit var mBinding: FragmentAdminOrderBinding
    private lateinit var mPresenter: AdminOrderContract.Presenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mCalendar: Calendar
    private var mAdapter: AdminOrderAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_order, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mBinding.startDate.setOnClickListener({
            initDatePicker("start")
        })

        mBinding.endDate.setOnClickListener({
            initDatePicker("end")
        })

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        if (mBinding.startDate.text != null && mBinding.startDate.text.toString() != "" &&
                mBinding.endDate.text != null && mBinding.endDate.text.toString() != "") {

            mPresenter.retrieveOrderList(mBinding.startDate.text.toString(),
                    mBinding.endDate.text.toString())
        }
    }

    override fun initPresenter(): AdminOrderContract.Presenter {
        return AdminOrderPresenter()
    }
    override fun initListOfOrders(orders: MutableList<Order?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = AdminOrderAdapter(orders, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun moveToDetail(orderDetail: Order) {

        Log.d("lalala", "dipencet aduh")

        /*val arguments = Bundle()
        val fragment = AdminOrderDetailFragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment
                .javaClass.simpleName)
        ft.commit()*/
    }

    override fun updateStartLabel() {
        val dateFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        mBinding.startDate.setText(sdf.format(mCalendar.getTime()))
        if (mBinding.endDate.text != null && mBinding.endDate.text.toString() != "") {

            mPresenter.retrieveOrderList(mBinding.startDate.text.toString(),
                    mBinding.endDate.text.toString())
        }
    }

    override fun updateEndLabel() {
        val dateFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        mBinding.endDate.setText(sdf.format(mCalendar.getTime()))
        if (mBinding.startDate.text != null && mBinding.startDate.text.toString() != "") {

            mPresenter.retrieveOrderList(mBinding.startDate.text.toString(),
                    mBinding.endDate.text.toString())
        }
    }

    override fun initDatePicker(code: String) {

        Database.addServerDate()

        mCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            when (code) {
                "start" -> updateStartLabel()
                "end" -> updateEndLabel()
            }
        }

        DatePickerDialog(activity, date, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun clearOrderList() {
        mAdapter?.clearOrderList()
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}