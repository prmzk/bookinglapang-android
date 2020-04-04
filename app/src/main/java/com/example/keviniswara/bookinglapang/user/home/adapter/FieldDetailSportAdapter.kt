package com.example.keviniswara.bookinglapang.user.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FieldDetailSportListBinding
import com.example.keviniswara.bookinglapang.databinding.OrderListBinding
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.view.FieldDetailFragment
import com.example.keviniswara.bookinglapang.user.home.view.JoinGameFragment

class FieldDetailSportAdapter(private val sports: MutableList<String?>?, fragment: FieldDetailFragment) : RecyclerView.Adapter<FieldDetailSportAdapter.FieldDetailSportHolder>() {
    private lateinit var mBinding: FieldDetailSportListBinding
    private var mFragment: FieldDetailFragment

    init {
        mFragment = fragment
    }

    override fun getItemCount(): Int {
        if (sports != null) {
            return sports.size
        } else return 0
    }

    override fun onBindViewHolder(holder: FieldDetailSportAdapter.FieldDetailSportHolder, position: Int) {
        val itemOrder = sports?.get(position)
        holder.bind(itemOrder!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldDetailSportAdapter.FieldDetailSportHolder {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.field_detail_sport_list, parent, false)
        return FieldDetailSportHolder(mBinding, mFragment)
    }

    fun clearOrderList() {
        val size = itemCount
        for (i in 0..(size-1)) {
            sports?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }

    class FieldDetailSportHolder(mBinding: FieldDetailSportListBinding, fragment: FieldDetailFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: FieldDetailSportListBinding
        private var find : FindEnemy? = null
        private var mFragment: FieldDetailFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment

        }

        fun bind(sport: String){
//            this.find = order

            mBinding.sport.setText(sport);
        }
    }
}