package com.example.keviniswara.bookinglapang.admin.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.view.AdminHomeFieldDetailFragment
import com.example.keviniswara.bookinglapang.databinding.AdminSportListBinding
import com.example.keviniswara.bookinglapang.model.Field

class AdminHomeFieldDetailAdapter(private val sports: MutableList<Field.PriceTimeDayRange>?, fragment: AdminHomeFieldDetailFragment) : RecyclerView.Adapter<AdminHomeFieldDetailAdapter.AdminHomeHolder>() {

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

        private var sport: Field.PriceTimeDayRange? = null

        private var mFragment: AdminHomeFieldDetailFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener({
                //TODO MOVE TO DETAIL
            })
        }

        fun bind(sport: Field.PriceTimeDayRange) {
            this.sport = sport
            mBinding.rangeDay.text = sport.day_range
            mBinding.rangeTime.text = sport.hour_range
            mBinding.sportName.text = sport.sport_name
            mBinding.price.text = sport.price
        }
    }
}