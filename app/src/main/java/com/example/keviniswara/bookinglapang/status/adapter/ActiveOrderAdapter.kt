package com.example.keviniswara.bookinglapang.order.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.status.view.StatusFragment


class ActiveOrderAdapter(private val orders: MutableList<Order?>, fragment: StatusFragment)
    : RecyclerView.Adapter<ActiveOrderAdapter.ActiveOrderHolder>() {

    private lateinit var mBinding: OrderListBinding
    private var mFragment: StatusFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ActiveOrderHolder, position: Int) {
        val itemOrder = orders[position]
        if (itemOrder != null) {
            holder.bind(itemOrder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveOrderAdapter.ActiveOrderHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.order_list, parent, false)
        return ActiveOrderHolder(mBinding, mFragment)
    }

    class ActiveOrderHolder(mBinding: OrderListBinding, fragment: StatusFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: OrderListBinding
        private var order: Order? = null
        private var mFragment: StatusFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener(View.OnClickListener {
                if (order != null) {
                    mFragment.moveToDetail(order!!)
                }

            })
        }

        fun bind(order: Order) {
            this.order = order

            mBinding.date.text = order.date
            mBinding.hour.text = "Pukul " + order.startHour + ".00 - " + order.endHour + ".00"
            mBinding.fieldId.text = order.fieldId
        }
    }
}