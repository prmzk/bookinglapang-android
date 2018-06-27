package com.example.keviniswara.bookinglapang.user.order.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.order.view.OrderFragment


class OrderAdapter(private val orders: MutableList<Order?>?, fragment: OrderFragment)
    : RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    private lateinit var mBinding: OrderListBinding
    private var mFragment: OrderFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        if (orders != null) {
            return orders.size
        } else return 0
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderHolder, position: Int) {
        val itemOrder = orders?.get(position)
        holder.bind(itemOrder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.order_list, parent, false)
        return OrderHolder(mBinding, mFragment)
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            orders?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    class OrderHolder(mBinding: OrderListBinding, fragment: OrderFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: OrderListBinding
        private var order : Order? = null
        private var mFragment: OrderFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener(View.OnClickListener {
                mFragment.moveToDetail(order!!)

            })
        }

        fun bind(order: Order) {
            this.order = order

            mBinding.date.text = order.date
            mBinding.startHour.text = order.startHour + ".00"
            mBinding.fieldId.text = order.fieldId
            mBinding.sport.text = order.sport
            mBinding.transfered.visibility = View.VISIBLE
        }
    }
}