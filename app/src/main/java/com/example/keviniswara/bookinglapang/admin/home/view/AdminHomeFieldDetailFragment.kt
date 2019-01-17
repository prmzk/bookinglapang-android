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
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeFieldDetailContract
import com.example.keviniswara.bookinglapang.admin.home.adapter.AdminHomeFieldDetailAdapter
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomeFieldDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeFieldDetailBinding
import com.example.keviniswara.bookinglapang.model.Price
import com.example.keviniswara.bookinglapang.utils.Database

class AdminHomeFieldDetailFragment: Fragment(), AdminHomeFieldDetailContract.View {

    private lateinit var mBinding: FragmentAdminHomeFieldDetailBinding

    private lateinit var mPresenter: AdminHomeFieldDetailContract.Presenter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var mRecyclerView: RecyclerView

    private var mAdapter: AdminHomeFieldDetailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home_field_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvSport

        val dividerItemDecoration = DividerItemDecoration(this.activity, linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        if (arguments != null) {
            val fieldId = arguments!!.getString("fieldId")
            val contactPerson = arguments!!.getString("contactPerson")
            val phoneNumber = arguments!!.getString("phoneNumber")
            val address = arguments!!.getString("address")

            setFieldId(fieldId)
            setAddress(address)
            setContactPersonName(contactPerson)
            setHandphone(phoneNumber)
            mPresenter.retrieveSportList(fieldId)
            mBinding.fabAdd.setOnClickListener({
                startSportDetailFragment(fieldId)
            })
        } else {
            mBinding.fabAdd.visibility = View.GONE
        }

        mBinding.buttonSave.setOnClickListener({

            Database.updateField(getFieldId(), getAddress(), getContactPersonName(), getHandphone(), getFieldId())
            var frag = fragmentManager!!.getBackStackEntryAt(0)
            var len = fragmentManager!!.backStackEntryCount
            fragmentManager!!.popBackStack()
        })

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeFieldDetailContract.Presenter {
        return AdminHomeFieldDetailPresenter()
    }

    override fun getFieldId(): String {
        return mBinding.fieldName.text.toString()
    }

    override fun getHandphone(): String {
        return mBinding.handphone.text.toString()
    }

    override fun getContactPersonName(): String {
        return mBinding.contactName.text.toString()
    }

    override fun getAddress(): String {
        return mBinding.address.text.toString()
    }

    override fun setFieldId(fieldId: String) {
        mBinding.fieldName.setText(fieldId)
    }

    override fun setHandphone(handphone: String) {
        mBinding.handphone.setText(handphone)
    }

    override fun initListOfSport(sports: MutableList<Price?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = AdminHomeFieldDetailAdapter(sports, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun setContactPersonName(contactPerson: String) {
        mBinding.contactName.setText(contactPerson)
    }

    override fun setAddress(address: String) {
        mBinding.address.setText(address)
    }

    override fun startSportDetailFragment(fieldId: String) {
        val arguments = Bundle()
        val fragment = AdminHomeSportDetailFragment()
        arguments.putString("fieldId", fieldId)
        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }
}