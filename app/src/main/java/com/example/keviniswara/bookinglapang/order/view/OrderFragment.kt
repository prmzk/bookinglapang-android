package com.example.keviniswara.bookinglapang.order.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentOrderBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.order.OrderContact
import com.example.keviniswara.bookinglapang.order.adapter.OrderAdapter
import com.example.keviniswara.bookinglapang.order.presenter.OrderPresenter
import android.support.v7.widget.DividerItemDecoration


class OrderFragment : Fragment(), OrderContact.View {

    private lateinit var mBinding: FragmentOrderBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPresenter: OrderPresenter
    private lateinit var linearLayoutManager:LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mPresenter.retrieveOrderList()

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        return mBinding.root
    }

    override fun initPresenter(): OrderPresenter {
        return OrderPresenter()
    }

    override fun moveToDetail(orderDetail: Order) {

        val arguments = Bundle()
        val fragment = OrderDetailFragment()
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
        ft.commit()
    }

    override fun initListOfOrders(orders: MutableList<Order?>?) {
        if (orders != null) {

            Log.d("LALALA", " masuk sini kok")
            mRecyclerView.layoutManager = linearLayoutManager
            mRecyclerView.adapter = OrderAdapter(orders, this)
        } else {
            Log.d("INIT ORDER lalala", "masuk null")
        }
    }
}