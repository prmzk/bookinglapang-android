package com.example.keviniswara.bookinglapangtest.keeper.order.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.databinding.KeeperOrderListBinding
import com.example.keviniswara.bookinglapangtest.keeper.order.view.KeeperOrderFragment
import com.example.keviniswara.bookinglapangtest.model.Order

class KeeperOrderAdapter(private val orders: MutableList<Order?>?, fragment: KeeperOrderFragment)
    : RecyclerView.Adapter<KeeperOrderAdapter.KeeperOrderHolder>() {

    private lateinit var mBinding: KeeperOrderListBinding
    private var mFragment: KeeperOrderFragment

    init {
        mFragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeeperOrderAdapter.KeeperOrderHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.keeper_order_list, parent, false)
        return KeeperOrderHolder(mBinding, mFragment)
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

    override fun onBindViewHolder(holder: KeeperOrderAdapter.KeeperOrderHolder, position: Int) {
        val itemOrder = orders?.get(position)
        holder.bind(itemOrder!!)
    }

    class KeeperOrderHolder(mBinding: KeeperOrderListBinding, fragment: KeeperOrderFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: KeeperOrderListBinding
        private var order : Order? = null
        private var mFragment: KeeperOrderFragment

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