package com.example.keviniswara.bookinglapang.user.profile.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentEditPaswordBinding
import com.example.keviniswara.bookinglapang.databinding.FragmentEditProfileBinding
import com.example.keviniswara.bookinglapang.user.profile.EditPasswordContract
import com.example.keviniswara.bookinglapang.user.profile.EditProfileContract
import com.example.keviniswara.bookinglapang.user.profile.presenter.EditPasswordPresenter
import com.example.keviniswara.bookinglapang.user.profile.presenter.EditProfilePresenter


class EditPasswordFragment : Fragment(), EditPasswordContract.View {

    private lateinit var mBinding: FragmentEditPaswordBinding
    private lateinit var mPresenter: EditPasswordContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_pasword,
                container, false)

        mPresenter = initPresenter()
//
        mPresenter.bind(this)
//
//        mPresenter.getProfileFromDatabase()
//
        mBinding.buttonSave.setOnClickListener({
//            mPresenter.save()
            val fragment = ProfileFragment()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(com.example.keviniswara.bookinglapang.R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
            ft.commit()
        })
//
        return mBinding.root
    }
//
    override fun initPresenter(): EditPasswordContract.Presenter {
        return EditPasswordPresenter()
    }
//
//    override fun getName(): String {
//        return mBinding.name.text.toString()
//    }
//
//    override fun getPhoneNumber(): String {
//        return mBinding.phoneNumber.text.toString()
//    }
//
//    override fun setName(name: String) {
//        mBinding.name.setText(name)
//    }
//
//    override fun setPhoneNumber(phoneNumber: String) {
//        mBinding.phoneNumber.setText(phoneNumber)
//    }
//
//    override fun hideKeyboard() {
//        val imm: InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
//    }

}
