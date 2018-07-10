package com.example.keviniswara.bookinglapang.user.status.view

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
import com.example.keviniswara.bookinglapang.databinding.FragmentStatusBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.order.adapter.ActiveOrderAdapter
import com.example.keviniswara.bookinglapang.user.status.StatusContract
import com.example.keviniswara.bookinglapang.user.status.presenter.StatusPresenter

class StatusFragment : Fragment(), StatusContract.View {

    private lateinit var mBinding: FragmentStatusBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPresenter: StatusPresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mAdapter: ActiveOrderAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_status, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mPresenter.retrieveOrderList()

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvStatus

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
        if (orders != null) {
            mRecyclerView.layoutManager = linearLayoutManager
            mAdapter = ActiveOrderAdapter(orders, this)
            mRecyclerView.adapter = mAdapter
        } else {
            Log.d("INIT ACTIVE ORDER", "masuk null")
        }
    }

    override fun moveToDetail(orderDetail: Order) {

        val arguments = Bundle()
        val fragment = ActiveOrderDetailFragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putLong("deadline", orderDetail.deadline)
        arguments.putString("orderId", orderDetail.orderId)
        fragment.arguments = arguments
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.content, fragment)?.addToBackStack(fragment
                .javaClass.simpleName)
        ft?.commit()
    }

    override fun initPresenter(): StatusPresenter {
        val presenter = StatusPresenter()
        return presenter
    }

    override fun clearOrderList() {
        mAdapter?.clearOrderList()
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun refresh() {
        clearOrderList()
        mPresenter.retrieveOrderList()
    }
}
