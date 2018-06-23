package com.example.keviniswara.bookinglapang.home.view

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentSearchFieldBinding
import com.example.keviniswara.bookinglapang.home.SearchFieldContract
import com.example.keviniswara.bookinglapang.home.presenter.SearchFieldPresenter
import java.text.SimpleDateFormat
import java.util.*


class SearchFieldFragment : Fragment(), SearchFieldContract.View {

    private lateinit var mPresenter: SearchFieldContract.Presenter

    private lateinit var mBinding: FragmentSearchFieldBinding

    private lateinit var mCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_field,
                container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.date.setOnClickListener(View.OnClickListener {
            initDatePicker()
        })

        mPresenter.retrieveListOfFieldFromFirebase()

        mBinding.listOfField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mPresenter.retrieveListOfSportFromFirebase(p0!!.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        mBinding.buttonContinue.setOnClickListener(View.OnClickListener {
            mPresenter.addOrderToFirebase()
            mPresenter.sendOrderNotificationToFieldKeeper()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.content, HomeFragment())
            ft.commit()
        })

        return mBinding.root
    }

    override fun initPresenter(): SearchFieldContract.Presenter {
        val presenter: SearchFieldPresenter = SearchFieldPresenter()
        return presenter
    }

    override fun getFieldName(): String {
        return mBinding.listOfField.selectedItem.toString()
    }

    override fun getSport(): String {
        return mBinding.listOfSports.selectedItem.toString()
    }

    override fun getDate(): String {
        return mBinding.date.text.toString()
    }

    override fun getStartHour(): String {
        return mBinding.startHour.text.toString()
    }

    override fun getFinishHour(): String {
        return mBinding.finishHour.text.toString()
    }

    private fun updateLabel() {
        val dateFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        mBinding.date.setText(sdf.format(mCalendar.getTime()))
    }

    override fun initDatePicker() {
        mCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        DatePickerDialog(activity, date, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun initListOfFieldDropdown(listOfField: List<String>) {
        val adapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, listOfField)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        mBinding.listOfField.adapter = adapter
    }

    override fun initListOfSportDropdown(listOfSport: List<String>) {
        val adapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, listOfSport)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        mBinding.listOfSports.adapter = adapter
    }
}