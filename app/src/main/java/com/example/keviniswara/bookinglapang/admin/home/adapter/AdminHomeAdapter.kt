package com.example.keviniswara.bookinglapang.admin.home.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.view.AdminHomeFragment
import com.example.keviniswara.bookinglapang.databinding.AdminFieldListBinding
import com.example.keviniswara.bookinglapang.model.Field

class AdminHomeAdapter(private val fields: MutableList<Field?>?, fragment: AdminHomeFragment) : RecyclerView.Adapter<AdminHomeAdapter.AdminHomeHolder>() {

    private lateinit var mBinding: AdminFieldListBinding

    private var mFragment: AdminHomeFragment

    init {
        mFragment = fragment
    }

    class AdminHomeHolder(mBinding: AdminFieldListBinding, fragment: AdminHomeFragment) : RecyclerView.ViewHolder(mBinding.root) {

        private var mBinding: AdminFieldListBinding

        private var field: Field? = null

        private var mFragment: AdminHomeFragment

        init {
            this.mBinding = mBinding
            mFragment = fragment
            mBinding.root.setOnClickListener({
                mFragment.moveToDetail(field!!)
            })
        }

        fun bind(field: Field) {
            this.field = field

            mBinding.fieldId.text = field.field_id
            val sports : MutableList<String> = mutableListOf()
            for ((_,sport) in field.sports) {
                sports.add(sport.sport_name)
            }
            mBinding.sport.text = sports.joinToString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.admin_field_list, parent, false)
        return AdminHomeHolder(mBinding, mFragment)
    }

    override fun getItemCount(): Int {
        return fields?.size ?: 0
    }

    override fun onBindViewHolder(holder: AdminHomeHolder, position: Int) {
        val itemField = fields?.get(position)
        holder.bind(itemField!!)
    }

    fun clearFieldList() {
        val size = itemCount
        for (i in 0..(size - 1)) {
            fields?.removeAt(0)
            notifyItemRemoved(i)
        }
        notifyItemRangeRemoved(0, size)
    }
}