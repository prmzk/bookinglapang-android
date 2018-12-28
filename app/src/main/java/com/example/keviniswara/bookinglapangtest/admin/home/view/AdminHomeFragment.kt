package com.example.keviniswara.bookinglapangtest.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.admin.home.AdminHomeContract
import com.example.keviniswara.bookinglapangtest.admin.home.adapter.AdminHomeAdapter
import com.example.keviniswara.bookinglapangtest.admin.home.presenter.AdminHomePresenter
import com.example.keviniswara.bookinglapangtest.databinding.FragmentAdminHomeBinding
import com.example.keviniswara.bookinglapangtest.model.Field

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

        mBinding.fabAdd.setOnClickListener({
            startFieldDetailFragment()
        })

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

    override fun startFieldDetailFragment() {
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, AdminHomeFieldDetailFragment()).addToBackStack(AdminHomeFieldDetailFragment().javaClass.simpleName)
        ft.commit()
    }

    override fun moveToDetail(fieldDetail: Field) {
        val arguments = Bundle()
        val fragment = AdminHomeFieldDetailFragment()
        arguments.putString("fieldId", fieldDetail.field_id)
        arguments.putString("contactPerson", fieldDetail.contact_person)
        arguments.putString("phoneNumber", fieldDetail.phone_number)
        arguments.putString("address", fieldDetail.address)
        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }
}