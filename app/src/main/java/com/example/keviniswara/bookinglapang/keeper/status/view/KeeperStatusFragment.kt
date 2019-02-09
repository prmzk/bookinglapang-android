package com.example.keviniswara.bookinglapang.keeper.status.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentKeeperStatusBinding
import com.example.keviniswara.bookinglapang.keeper.status.KeeperStatusContract
import com.example.keviniswara.bookinglapang.keeper.status.adapter.KeeperStatusAdapter
import com.example.keviniswara.bookinglapang.keeper.status.presenter.KeeperStatusPresenter
import com.example.keviniswara.bookinglapang.model.Order

class KeeperStatusFragment : Fragment(), KeeperStatusContract.View {

    private lateinit var mBinding: FragmentKeeperStatusBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPresenter: KeeperStatusPresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mAdapter: KeeperStatusAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_keeper_status, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)


        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mPresenter.retrieveOrderList()
    }

    override fun initListOfOrders(orders: MutableList<Order?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = KeeperStatusAdapter(orders, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun moveToDetail(orderDetail: Order) {
        val arguments = Bundle()
        val fragment = KeeperStatusDetailFragment()
        arguments.putString("startHour", orderDetail.startHour)
        arguments.putString("endHour", orderDetail.endHour)
        arguments.putString("customerEmail", orderDetail.customerEmail)
        arguments.putString("status", orderDetail.status.toString())
        arguments.putString("date", orderDetail.date)
        arguments.putString("sport", orderDetail.sport)
        arguments.putString("fieldId", orderDetail.fieldId)
        arguments.putString("orderId", orderDetail.orderId)
        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment
                .javaClass.simpleName)
        ft.commit()
    }

    override fun clearOrderList() {
        mAdapter?.clearOrderList()
    }

    override fun makeToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    override fun initPresenter(): KeeperStatusPresenter {
        return KeeperStatusPresenter()
    }
}