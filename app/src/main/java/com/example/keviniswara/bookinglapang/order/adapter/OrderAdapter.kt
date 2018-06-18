package com.example.keviniswara.bookinglapang.order.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.Order

class OrderAdapter(private val orders: MutableList<Order?>): RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    private lateinit var mBinding: OrderListBinding

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderHolder, position: Int) {
        val itemOrder = orders[position]
        holder.bind(itemOrder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.order_list, parent, false)
        return OrderHolder(mBinding)
    }

    class OrderHolder(mBinding: OrderListBinding) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: OrderListBinding
        private var order : Order? = null

        init {
            this.mBinding = mBinding
        }

        fun bind(order: Order) {
            this.order = order

            mBinding.date.text = order.date
            mBinding.hour.text = "Pukul " + order.startHour + ".00 - " + order.endHour + ".00"
            mBinding.fieldId.text = order.fieldId
        }
    }
}