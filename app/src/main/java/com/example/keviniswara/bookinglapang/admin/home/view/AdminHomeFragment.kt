package com.example.keviniswara.bookinglapang.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeContract
import com.example.keviniswara.bookinglapang.admin.home.adapter.AdminHomeAdapter
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomePresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeBinding
import com.example.keviniswara.bookinglapang.model.Field

class AdminHomeFragment: Fragment(), AdminHomeContract.View {

    private lateinit var mBinding: FragmentAdminHomeBinding

    private lateinit var mPresenter: AdminHomeContract.Presenter

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var mAdapter: AdminHomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvField

        val dividerItemDecoration = DividerItemDecoration(this.activity, linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        mPresenter.retrieveFieldList()

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeContract.Presenter {
        return AdminHomePresenter()
    }

    override fun initListOfFields(fields: MutableList<Field?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = AdminHomeAdapter(fields, this)
        mRecyclerView.adapter = mAdapter
    }
}