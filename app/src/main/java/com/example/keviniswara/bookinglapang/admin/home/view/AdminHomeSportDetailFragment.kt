package com.example.keviniswara.bookinglapang.admin.home.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.admin.home.AdminHomeSportDetailContract
import com.example.keviniswara.bookinglapang.admin.home.presenter.AdminHomeSportDetailPresenter
import com.example.keviniswara.bookinglapang.databinding.FragmentAdminHomeSportDetailBinding
import com.example.keviniswara.bookinglapang.databinding.NumberPickerDialogBinding

class AdminHomeSportDetailFragment: Fragment(), AdminHomeSportDetailContract.View {

    private lateinit var mBinding: FragmentAdminHomeSportDetailBinding

    private lateinit var mPresenter: AdminHomeSportDetailContract.Presenter

    private var defaultStartDay = 0
    private var defaultEndDay = 0
    private var defaultStartHour = 0
    private var defaultEndHour = 0

    private val arrayOfString = arrayOf("00.00", "01.00", "02.00", "03.00", "04.00", "05.00",
            "06.00", "07.00", "08.00", "09.00", "10.00", "11.00", "12.00", "13.00", "14.00",
            "15.00", "16.00", "17.00", "18.00", "19.00", "20.00", "21.00", "22.00", "23.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home_sport_detail, container, false)

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mBinding.dayStart.setOnClickListener({
            initDayPicker("Pilih hari", 0)
        })

        mBinding.dayEnd.setOnClickListener({
            initDayPicker("Pilih hari", 1)
        })

        mBinding.hourStart.setOnClickListener({
            initHourPicker("Pilih jam", 0, arrayOfString)
        })

        mBinding.hourEnd.setOnClickListener({
            initHourPicker("Pilih jam", 1, arrayOfString)
        })

        val fieldId = arguments!!.getString("fieldId")

        mBinding.buttonSave.setOnClickListener({
            mPresenter.savePrice(fieldId)
            fragmentManager!!.popBackStackImmediate()
        })

        return mBinding.root
    }

    override fun initPresenter(): AdminHomeSportDetailContract.Presenter {
        return AdminHomeSportDetailPresenter()
    }

    override fun getStartDay(): String {
        return mBinding.dayStart.text.toString()
    }

    override fun getEndDay(): String {
        return mBinding.dayEnd.text.toString()
    }

    override fun getStartHour(): String {
        return defaultStartHour.toString()
    }

    override fun getEndHour(): String {
        return defaultEndHour.toString()
    }

    override fun getPrice(): String {
        return mBinding.price.text.toString()
    }

    override fun getSport(): String {
        return mBinding.sport.text.toString()
    }

    // type 0 = start, 1 = end
    override fun initDayPicker(title: String, type: Int) {

        val mBottomSheetDialog: BottomSheetDialog = BottomSheetDialog(activity!!, R.style.BottomSheetDialogTheme)

        val mBindingNumber : NumberPickerDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater,
                R.layout.number_picker_dialog, null, false)
        mBottomSheetDialog.setContentView(mBindingNumber.root)

        mBindingNumber.tvDialogTitle.text = title

        val numberPicker = mBindingNumber.numberPicker
        val arrayOfDay = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

        numberPicker.minValue = 0
        numberPicker.maxValue = arrayOfDay.size - 1
        numberPicker.displayedValues = arrayOfDay

        if (type == 0) {
            numberPicker.value = defaultStartDay
        } else {
            numberPicker.value = defaultEndDay
        }
        numberPicker.wrapSelectorWheel = false

        mBindingNumber.buttonSave.setOnClickListener({
            if (type == 0) {
                defaultStartDay = numberPicker.value
                mBinding.dayStart.setText(arrayOfDay[numberPicker.value])
            } else {
                defaultEndDay = numberPicker.value
                mBinding.dayEnd.setText(arrayOfDay[numberPicker.value])
            }
            mBottomSheetDialog.dismiss()
        })
        mBottomSheetDialog.show()
    }

    // type 0 = start, 1 = end
    override fun initHourPicker(title: String, type: Int, array: Array<String>) {

        val mBottomSheetDialog: BottomSheetDialog = BottomSheetDialog(activity!!, R.style.BottomSheetDialogTheme)

        val mBindingNumber : NumberPickerDialogBinding = DataBindingUtil.inflate(activity!!.layoutInflater,
                R.layout.number_picker_dialog, null, false)
        mBottomSheetDialog.setContentView(mBindingNumber.root)

        mBindingNumber.tvDialogTitle.text = title

        val numberPicker = mBindingNumber.numberPicker

        numberPicker.minValue = 0
        numberPicker.maxValue = 23
        numberPicker.displayedValues = array
        if (type == 0) {
            numberPicker.value = defaultStartHour
        } else {
            numberPicker.value = defaultEndHour
        }
        numberPicker.wrapSelectorWheel = false

        mBindingNumber.buttonSave.setOnClickListener({
            if (type == 0) {
                defaultStartHour = numberPicker.value
                mBinding.hourStart.setText(arrayOfString[numberPicker.value])
            } else {
                defaultEndHour = numberPicker.value
                mBinding.hourEnd.setText(arrayOfString[numberPicker.value])
            }
            mBottomSheetDialog.dismiss()
        })
        mBottomSheetDialog.show()
    }
}