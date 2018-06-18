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



class OrderFragment : Fragment(), OrderContact.View{

    private lateinit var mBinding: FragmentOrderBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPresenter: OrderPresenter
    private lateinit var linearLayoutManager:LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mPresenter.retrieveOrderList()

        linearLayoutManager = LinearLayoutManager(this.activity)

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        val orders: MutableList<Order?> = mutableListOf()
        for (i in 1..5) {
            orders.add(Order())
        }
        mRecyclerView.adapter = OrderAdapter(orders)

        return mBinding.root
    }

    companion object {
        fun newInstance(): OrderFragment {
            val fragment = OrderFragment()
            return fragment
        }
    }

    override fun initPresenter(): OrderPresenter {
        val presenter = OrderPresenter()
        return presenter
    }

    override fun initListOfOrders(orders: MutableList<Order?>?) {
        if (orders != null) {
            mRecyclerView.layoutManager = linearLayoutManager
            mRecyclerView.adapter = OrderAdapter(orders)
        } else {
            Log.d("KEVIN INIT", "masuk null")
        }
    }
}