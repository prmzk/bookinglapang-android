package com.example.keviniswara.bookinglapang.user.profile.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.keviniswara.bookinglapang.MainActivity
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentProfileBinding
import com.example.keviniswara.bookinglapang.home.HomeActivity
import com.example.keviniswara.bookinglapang.login.view.LoginActivity
import com.example.keviniswara.bookinglapang.user.profile.ProfileContract
import com.example.keviniswara.bookinglapang.user.profile.presenter.ProfilePresenter
import kotlinx.android.synthetic.main.activity_main.*


class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var mBinding: FragmentProfileBinding
    private lateinit var mPresenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,
                container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.buttonLogout.setOnClickListener({
            mPresenter.logout()
        })

        mPresenter.getProfileFromDatabase()

        mBinding.buttonSave.setOnClickListener({
            val fragment = EditProfileFragment()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(com.example.keviniswara.bookinglapang.R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
            ft.commit()
        })

        mBinding.changePassBtn.setOnClickListener({
            val fragment = EditPasswordFragment()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(com.example.keviniswara.bookinglapang.R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
            ft.commit()
        })

        return mBinding.root
    }

    override fun initPresenter(): ProfileContract.Presenter {
        return ProfilePresenter()
    }

//    override fun getName(): String {
//        return mBinding.name.text.toString()
//    }
//
//    override fun getPhoneNumber(): String {
//        return mBinding.phoneNumber.text.toString()
//    }
//
//    override fun getEmail(): String {
//        return mBinding.email.text.toString()
//    }

    override fun setName(name: String) {
        mBinding.name.setText(name)
    }

    override fun setPhoneNumber(phoneNumber: String) {
        mBinding.phoneNumber.setText(phoneNumber)
    }

    override fun setEmail(email: String) {
        mBinding.email.setText(email)
    }

    override fun startLoginActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun hideKeyboard() {
        val imm: InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}
