package com.example.keviniswara.bookinglapang.user.home.view

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentJoinGameBinding
import com.example.keviniswara.bookinglapang.model.FindEnemy
import com.example.keviniswara.bookinglapang.user.home.JoinGameContract
import com.example.keviniswara.bookinglapang.user.home.adapter.JoinGameAdapter
import com.example.keviniswara.bookinglapang.user.home.presenter.JoinGamePresenter
import java.text.SimpleDateFormat
import java.util.*

class JoinGameFragment : Fragment(), JoinGameContract.View {

    private lateinit var mPresenter: JoinGameContract.Presenter
    private lateinit var mBinding: FragmentJoinGameBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mCalendar: Calendar
    private var mAdapter: JoinGameAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_game, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mPresenter.retrieveListOfSportFromFirebase()

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerView = mBinding.rvOrder

        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)

        mBinding.date.setOnClickListener({
            initDatePicker()
        })

        mBinding.listOfSport.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (getDate() != "") {
                    mPresenter.retrieveJoinGameList(getDate(), getSport())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        return mBinding.root
    }

    override fun initPresenter(): JoinGameContract.Presenter {
        return JoinGamePresenter()
    }

    override fun initListOfJoinGame(joinGameList: MutableList<FindEnemy?>?) {
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = JoinGameAdapter(joinGameList, this)
        mRecyclerView.adapter = mAdapter
    }

    override fun initDatePicker() {
        mCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
            if (getSport() != "") {
                mPresenter.retrieveJoinGameList(getDate(), getSport())
            }
        }

        DatePickerDialog(activity, date, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabel() {
        val dateFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        mBinding.date.setText(sdf.format(mCalendar.getTime()))
    }

    override fun initListOfSportDropdown(listOfSport: List<String>) {
        val adapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, listOfSport)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        mBinding.listOfSport.adapter = adapter
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun moveToDetail(findEnemy: FindEnemy) {
        val arguments = Bundle()
        val fragment = JoinGameDetailFragment()
        arguments.putString("hostName", findEnemy.customerEmail)
        arguments.putString("hostPhoneNumber", findEnemy.customerPhone)
        fragment.arguments = arguments
        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.content, fragment).addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }

    override fun getSport(): String {
        return mBinding.listOfSport.selectedItem.toString()
    }

    override fun getDate(): String {
        return mBinding.date.text.toString()
    }

    override fun clearOrderList() {
        mAdapter?.clearOrderList()
    }
}