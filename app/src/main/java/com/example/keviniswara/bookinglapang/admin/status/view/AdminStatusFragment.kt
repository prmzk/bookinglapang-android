package com.example.keviniswara.bookinglapang.admin.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.status.AdminStatusContract
import com.example.keviniswara.bookinglapang.admin.status.adapter.AdminStatusAdapter
import com.example.keviniswara.bookinglapang.admin.status.presenter.AdminStatusPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminStatusBinding
import com.example.keviniswara.bookinglapang.model.Order

class AdminStatusFragment: Fragment(), AdminStatusContract.View {

    private lateinit var mBinding: FragmentAdminStatusBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPresenter: AdminStatusPresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mAdapter: AdminStatusAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_status, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)


        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mPresenter.retrieveOrderList()
    }

    override fun initListOfOrders(orders: MutableList<Order?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = AdminStatusAdapter(orders, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun moveToDetail(orderDetail: Order) {
        val arguments = Bundle()
        val fragment = AdminStatusDetailFragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("customerName",orderDetail.customerName)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putString("orderId", orderDetail.orderId)
        arguments.putString("feedback", orderDetail.feedback)

        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment
                .javaClass.simpleName)
        ft.commit()
    }

    override fun clearOrderList() {
        mAdapter?.clearOrderList()
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun initPresenter(): AdminStatusPresenter {
        return AdminStatusPresenter()
    }

    override fun refresh() {
        clearOrderList()
        mPresenter.retrieveOrderList()
    }
}