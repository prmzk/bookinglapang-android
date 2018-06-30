package com.example.keviniswara.bookinglapang.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeFieldDetailContract
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomeFieldDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeFieldDetailBinding
import com.example.keviniswara.bookinglapang.utils.Database

class AdminHomeFieldDetailFragment: Fragment(), AdminHomeFieldDetailContract.View {

    private lateinit var mBinding: FragmentAdminHomeFieldDetailBinding

    private lateinit var mPresenter: AdminHomeFieldDetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home_field_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        if (arguments != null) {
            val fieldId = arguments!!.getString("fieldId")
            val contactPerson = arguments!!.getString("contactPerson")
            val phoneNumber = arguments!!.getString("phoneNumber")
            val address = arguments!!.getString("address")

            setFieldId(fieldId)
            setAddress(address)
            setContactPersonName(contactPerson)
            setHandphone(phoneNumber)
        }

        mBinding.buttonSave.setOnClickListener({
            Database.updateField(getFieldId(), getAddress(), getContactPersonName(),
                    getHandphone(), getFieldId())
        })

        mBinding.fabAdd.setOnClickListener({
            startSportDetailFragment()
        })

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeFieldDetailContract.Presenter {
        return AdminHomeFieldDetailPresenter()
    }

    override fun getFieldId(): String {
        return mBinding.fieldName.toString()
    }

    override fun getHandphone(): String {
        return mBinding.handphone.toString()
    }

    override fun getContactPersonName(): String {
        return mBinding.contactName.toString()
    }

    override fun getAddress(): String {
        return mBinding.address.toString()
    }

    override fun setFieldId(fieldId: String) {
        mBinding.fieldName.setText(fieldId)
    }

    override fun setHandphone(handphone: String) {
        mBinding.handphone.setText(handphone)
    }

    override fun setContactPersonName(contactPerson: String) {
        mBinding.contactName.setText(contactPerson)
    }

    override fun setAddress(address: String) {
        mBinding.address.setText(address)
    }

    override fun startSportDetailFragment() {
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, AdminHomeSportDetailFragment())
        ft.commit()
    }
}