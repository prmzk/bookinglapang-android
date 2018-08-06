package com.example.keviniswara.bookinglapang.user.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentJoinGameDetailBinding
import com.example.keviniswara.bookinglapang.user.home.JoinGameDetailContract
import com.example.keviniswara.bookinglapang.user.home.presenter.JoinGameDetailPresenter

class JoinGameDetailFragment : Fragment(),JoinGameDetailContract.View {

    private lateinit var mPresenter: JoinGameDetailContract.Presenter
    private lateinit var mBinding: FragmentJoinGameDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_game_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        val hostName = arguments!!.getString("hostName")
        val hostPhoneNumber = arguments!!.getString("hostPhoneNumber")

        mPresenter.retrieveCurrentUserDetail()

        mPresenter.initJoinGameDetail(hostName, hostPhoneNumber)

        return mBinding.root
    }

    override fun initPresenter(): JoinGameDetailContract.Presenter {
        return JoinGameDetailPresenter()
    }

    override fun setHostName(hostName: String) {
        mBinding.hostName.text = hostName
    }

    override fun setHostPhoneNumber(phoneNumber: String) {
        mBinding.hostPhoneNumber.text = phoneNumber
    }

    override fun setVisitorName(name: String) {
        mBinding.visitorName.text = name
    }

    override fun setVisitorPhoneNumber(phoneNumber: String) {
        mBinding.hostPhoneNumber.text = phoneNumber
    }

}