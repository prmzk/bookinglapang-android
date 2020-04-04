package com.example.keviniswara.bookinglapang.user.home

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView
import com.example.keviniswara.bookinglapang.model.Field
import com.google.android.gms.maps.GoogleMap

interface FieldDetailContract {
    interface View : BaseView<Presenter> {
        fun initAddress(address : String)
        fun initListofSport(sportList: MutableList<String?>)
        fun initListofimage(imageList: MutableList<String?>)
        fun initFieldData(fieldData: Field?)
    }

    interface Presenter : BasePresenter<View> {
        fun retrieveListOfFieldFromFirebase(fieldName: String)
        fun retrieveListOfSportFromFirebase(fieldName: String)
        fun retrieveListOfImagesFromFirebase(fieldName: String)
    }
}