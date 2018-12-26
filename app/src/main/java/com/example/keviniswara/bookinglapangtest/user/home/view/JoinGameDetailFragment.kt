package com.example.keviniswara.bookinglapangtest.user.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapangtest.R
import com.example.keviniswara.bookinglapangtest.databinding.FragmentJoinGameDetailBinding
import com.example.keviniswara.bookinglapangtest.user.home.JoinGameDetailContract
import com.example.keviniswara.bookinglapangtest.user.home.presenter.JoinGameDetailPresenter

class JoinGameDetailFragment : Fragment(), JoinGameDetailContract.View {

    private lateinit var mPresenter: JoinGameDetailContract.Presenter
    private lateinit var mBinding: FragmentJoinGameDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_game_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        setHostName(arguments!!.getString("hostName"))
        setHostPhoneNumber(arguments!!.getString("hostPhoneNumber"))

        mPresenter.checkCurrentFindEnemy(arguments!!.getString("hostEmail"))

        mPresenter.retrieveCurrentUserDetail()

        return mBinding.root
    }

    override fun initPresenter(): JoinGameDetailContract.Presenter {
        return JoinGameDetailPresenter()
    }

    override fun hideCancelButton() {
        mBinding.btnCancel.visibility = View.GONE
    }

    override fun setupCancelButton() {
        mBinding.btnCancel.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                mPresenter.deleteFindEnemy(arguments!!.getString("findEnemyId"))
            }
        }
    }

    override fun goBackTwoTimes() {
        fragmentManager?.popBackStack()
        fragmentManager?.popBackStack()
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
        mBinding.visitorPhoneNumber.text = phoneNumber
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}