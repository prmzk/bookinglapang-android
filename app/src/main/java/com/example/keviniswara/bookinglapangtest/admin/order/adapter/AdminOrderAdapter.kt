package com.example.keviniswara.bookinglapangtest.admin.order.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.admin.order.view.AdminOrderFragment
import com.example.keviniswara.bookinglapangtest.databinding.AdminOrderListBinding
import com.example.keviniswara.bookinglapangtest.model.Order

class AdminOrderAdapter(private val orders: MutableList<Order?>?, fragment: AdminOrderFragment)
    : RecyclerView.Adapter<AdminOrderAdapter.AdminOrderHolder>() {

    private lateinit var mBinding: AdminOrderListBinding
    private var mFragment: AdminOrderFragment

    init {
        mFragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderAdapter.AdminOrderHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.admin_order_list, parent, false)
        return AdminOrderHolder(mBinding, mFragment)
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            orders?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        if (orders != null) {
            return orders.size
        } else return 0
    }

    override fun onBindViewHolder(holder: AdminOrderAdapter.AdminOrderHolder, position: Int) {
        val itemOrder = orders?.get(position)
        holder.bind(itemOrder!!)
    }

    class AdminOrderHolder(mBinding: AdminOrderListBinding, fragment: AdminOrderFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: AdminOrderListBinding
        private var order : Order? = null
        private var mFragment: AdminOrderFragment

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
