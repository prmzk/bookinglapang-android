package com.example.keviniswara.bookinglapang.user.home.view

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentSearchFieldBinding
import com.example.keviniswara.bookinglapang.databinding.NumberPickerDialogBinding
import com.example.keviniswara.bookinglapang.user.home.SearchFieldContract
import com.example.keviniswara.bookinglapang.user.home.presenter.SearchFieldPresenter
import java.text.SimpleDateFormat
import java.util.*


class SearchFieldFragment : Fragment(), SearchFieldContract.View {

    private lateinit var mPresenter: SearchFieldContract.Presenter

    private lateinit var mBinding: FragmentSearchFieldBinding

    private lateinit var mCalendar: Calendar

    private val arrayOfString = arrayOf("00.00", "01.00", "02.00", "03.00", "04.00", "05.00",
            "06.00", "07.00", "08.00", "09.00", "10.00", "11.00", "12.00", "13.00", "14.00",
            "15.00", "16.00", "17.00", "18.00", "19.00", "20.00", "21.00", "22.00", "23.00")

    private var defaultStartHour = 0
    private var defaultEndHour = 0

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

        mBinding.startHour.setOnClickListener({
            initNumberPicker("Pilih jam", 0, 23, 0, arrayOfString)
        })

        mBinding.finishHour.setOnClickListener({
            initNumberPicker("Pilih jam", 0, 23, 1, arrayOfString)
        })

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
            showToastMessage("Order berhasil disimpan.")
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
        return defaultStartHour.toString()
    }

    override fun getFinishHour(): String {
        return defaultEndHour.toString()
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

    // type 0 = start, 1 = end
    override fun initNumberPicker(title: String, minValue: Int, maxValue: Int, type: Int, array: Array<String>) {


        val mBottomSheetDialog: BottomSheetDialog = BottomSheetDialog(activity!!, R.style.BottomSheetDialogTheme)

        val mBindingNumber : NumberPickerDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater,
                R.layout.number_picker_dialog, null, false)
        mBottomSheetDialog.setContentView(mBindingNumber.root)

        mBindingNumber.tvDialogTitle.text = title

        val numberPicker = mBindingNumber.numberPicker

        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue
        numberPicker.displayedValues = array
        numberPicker.wrapSelectorWheel = false
        if (type == 0) {
            numberPicker.value = defaultStartHour
        } else {
            numberPicker.value = defaultEndHour
        }

        mBindingNumber.buttonSave.setOnClickListener({
            if (type == 0) {
                defaultStartHour = numberPicker.value
                mBinding.startHour.setText(arrayOfString[numberPicker.value])
            } else {
                defaultEndHour = numberPicker.value
                mBinding.finishHour.setText(arrayOfString[numberPicker.value])
            }
            mBottomSheetDialog.dismiss()
        })
        mBottomSheetDialog.show()
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

    override fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}