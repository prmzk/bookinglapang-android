package com.example.keviniswara.bookinglapang.admin.status.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.status.view.AdminStatusFragment
import com.example.keviniswara.bookinglapang.databinding.AdminStatusListBinding
import com.example.keviniswara.bookinglapang.model.Order

class AdminStatusAdapter (private val orders: MutableList<Order?>?, fragment: AdminStatusFragment)
    : RecyclerView.Adapter<AdminStatusAdapter.AdminStatusHolder>() {

    private lateinit var mBinding: AdminStatusListBinding
    private var mFragment: AdminStatusFragment

    init {
        mFragment = fragment
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            orders?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminStatusAdapter.AdminStatusHolder{

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.admin_status_list, parent, false)
        return AdminStatusHolder(mBinding, mFragment)
    }

    override fun getItemCount(): Int {
        if (orders != null) {
            return orders.size
        } else return 0
    }

    override fun onBindViewHolder(holder: AdminStatusHolder, position: Int) {
        val itemOrder = orders?.get(position)
        holder.bind(itemOrder!!)
    }

    class AdminStatusHolder(mBinding: AdminStatusListBinding, fragment: AdminStatusFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: AdminStatusListBinding
        private var order : Order? = null
        private var mFragment: AdminStatusFragment

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
        }
    }

}