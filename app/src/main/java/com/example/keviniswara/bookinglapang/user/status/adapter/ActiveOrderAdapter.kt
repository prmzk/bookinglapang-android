package com.example.keviniswara.bookinglapang.user.order.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.Order
import com.example.keviniswara.bookinglapang.user.status.view.StatusFragment
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class ActiveOrderAdapter(private val orders: MutableList<Order?>?, fragment: StatusFragment)
    : RecyclerView.Adapter<ActiveOrderAdapter.ActiveOrderHolder>() {

    private lateinit var mBinding: OrderListBinding
    private var mFragment: StatusFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        if (orders != null) {
            return orders.size
        } else return 0
    }

    override fun onBindViewHolder(holder: ActiveOrderHolder, position: Int) {
        val itemOrder = orders?.get(position)
        if (itemOrder != null) {
            holder.bind(itemOrder)
        }
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            orders?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
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

                Database.addServerDate()

                val timeRoot: DatabaseReference = Database.database.getReference("server_time")

                timeRoot.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(dateSnapshot: DataSnapshot) {

                        val dateInMillis: Long = dateSnapshot?.value as Long

                        if (order != null && (order!!.status == 0 || order!!.status != 0
                                        && order!!.deadline >= dateInMillis)) {
                            mFragment.moveToDetail(order!!)
                        } else {
                            mFragment.makeToast("Pesanan sudah kadaluarsa")
                            mFragment.refresh()
                        }

                    }
                })

            })
        }

        fun bind(order: Order) {
            this.order = order

            mBinding.date.text = order.date
            mBinding.startHour.text = order.startHour + ".00"
            mBinding.fieldId.text = order.fieldId
            mBinding.sport.text = order.sport
            when {
                order.status == 0 -> {
                    mBinding.notVerified.visibility = View.VISIBLE
                    mBinding.notTransfer.visibility = View.GONE
                    mBinding.failed.visibility = View.GONE
                }
                order.status == 1 -> {
                    mBinding.notVerified.visibility = View.GONE
                    mBinding.notTransfer.visibility = View.VISIBLE
                    mBinding.failed.visibility = View.GONE
                }
                order.status == 3 -> {
                    mBinding.notVerified.visibility = View.GONE
                    mBinding.notTransfer.visibility = View.GONE
                    mBinding.failed.visibility = View.VISIBLE
                }
            }
        }
    }
}