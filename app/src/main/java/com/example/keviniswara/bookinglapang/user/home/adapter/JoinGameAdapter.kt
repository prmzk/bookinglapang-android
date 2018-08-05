package com.example.keviniswara.bookinglapang.user.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.view.JoinGameFragment

class JoinGameAdapter(private val findEnemy: MutableList<FindEnemy?>?, fragment: JoinGameFragment) : RecyclerView.Adapter<JoinGameAdapter.FindEnemyHolder>() {
    private lateinit var mBinding: OrderListBinding
    private var mFragment: JoinGameFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        if (findEnemy != null) {
            return findEnemy.size
        } else return 0
    }

    override fun onBindViewHolder(holder: JoinGameAdapter.FindEnemyHolder, position: Int) {
        val itemOrder = findEnemy?.get(position)
        holder.bind(itemOrder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinGameAdapter.FindEnemyHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.order_list, parent, false)
        return FindEnemyHolder(mBinding, mFragment)
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            findEnemy?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    class FindEnemyHolder(mBinding: OrderListBinding, fragment: JoinGameFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: OrderListBinding
        private var find : FindEnemy? = null
        private var mFragment: JoinGameFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener(View.OnClickListener {
                //TODO MOVE TO DETAIL
//                mFragment.moveToDetail(find!!)

            })
        }

        fun bind(order: FindEnemy) {
            this.find = order

            mBinding.date.text = order.date
            mBinding.startHour.text = order.time + ".00"
            mBinding.fieldId.text = order.fieldId
            mBinding.sport.text = order.sport
            mBinding.transfered.visibility = View.GONE
        }
    }
}