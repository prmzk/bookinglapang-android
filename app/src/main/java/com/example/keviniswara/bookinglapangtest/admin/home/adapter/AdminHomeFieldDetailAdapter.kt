package com.example.keviniswara.bookinglapangtest.admin.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.admin.home.view.AdminHomeFieldDetailFragment
import com.example.keviniswara.bookinglapangtest.databinding.AdminSportListBinding
import com.example.keviniswara.bookinglapangtest.model.Price

class AdminHomeFieldDetailAdapter(private val sports: MutableList<Price?>?, fragment: AdminHomeFieldDetailFragment) : RecyclerView.Adapter<AdminHomeFieldDetailAdapter.AdminHomeHolder>() {

    private lateinit var mBinding: AdminSportListBinding

    private var mFragment: AdminHomeFieldDetailFragment

    init {
        mFragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.admin_sport_list, parent, false)
        return AdminHomeHolder(mBinding, mFragment)
    }

    override fun getItemCount(): Int {
        return sports?.size ?: 0
    }

    override fun onBindViewHolder(holder: AdminHomeHolder, position: Int) {
        val itemSport = sports?.get(position)
        holder.bind(itemSport!!)
    }

    class AdminHomeHolder(mBinding: AdminSportListBinding, fragment: AdminHomeFieldDetailFragment) : RecyclerView.ViewHolder(mBinding.root) {
        private var mBinding: AdminSportListBinding

        private var sport: Price? = null

        private var mFragment: AdminHomeFieldDetailFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener({
                //TODO MOVE TO DETAIL
            })
        }

        fun bind(sport: Price) {
            this.sport = sport
            mBinding.rangeDay.text = sport.day_range
            mBinding.rangeTime.text = sport.hour_range
            mBinding.sportName.text = sport.sport_name
            mBinding.price.text = sport.price
        }
    }
}