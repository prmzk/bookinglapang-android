package com.example.keviniswara.bookinglapang.keeper.status.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.KeeperStatusListBinding
import com.example.keviniswara.bookinglapang.keeper.status.view.KeeperStatusFragment
import com.example.keviniswara.bookinglapang.model.Order

class KeeperStatusAdapter(private val orders: MutableList<Order?>?, fragment: KeeperStatusFragment)
    : RecyclerView.Adapter<KeeperStatusAdapter.KeeperStatusHolder>() {

    private lateinit var mBinding: KeeperStatusListBinding
    private var mFragment: KeeperStatusFragment

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeeperStatusAdapter.KeeperStatusHolder{

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.keeper_status_list, parent, false)
        return KeeperStatusHolder(mBinding, mFragment)
    }

    override fun getItemCount(): Int {
        if (orders != null) {
            return orders.size
        } else return 0
    }

    override fun onBindViewHolder(holder: KeeperStatusHolder, position: Int) {
        val itemOrder = orders?.get(position)
        holder.bind(itemOrder!!)
    }

    class KeeperStatusHolder(mBinding: KeeperStatusListBinding, fragment: KeeperStatusFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: KeeperStatusListBinding
        private var order : Order? = null
        private var mFragment: KeeperStatusFragment

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