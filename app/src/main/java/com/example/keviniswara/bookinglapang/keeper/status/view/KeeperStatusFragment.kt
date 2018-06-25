package com.example.keviniswara.bookinglapang.keeper.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentKeeperStatusBinding

class KeeperStatusFragment : Fragment() {

    private lateinit var mBinding: FragmentKeeperStatusBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keeper_status, container, false)

        return mBinding.root
    }
}