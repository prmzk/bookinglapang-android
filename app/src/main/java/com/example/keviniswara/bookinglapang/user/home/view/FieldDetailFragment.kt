package com.example.keviniswara.bookinglapang.user.home.view

import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentFieldDetailBinding
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.keviniswara.bookinglapang.model.Field
import com.example.keviniswara.bookinglapang.user.home.FieldDetailContract
import com.example.keviniswara.bookinglapang.user.home.adapter.FieldDetailImageAdapter
import com.example.keviniswara.bookinglapang.user.home.adapter.FieldDetailSportAdapter
import com.example.keviniswara.bookinglapang.user.home.presenter.FieldDetailPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class FieldDetailFragment : Fragment(), FieldDetailContract.View, OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    var mapFragment : SupportMapFragment?=null


    private lateinit var mPresenter: FieldDetailContract.Presenter

    private lateinit var mBinding: FragmentFieldDetailBinding

    private lateinit var fieldName: String

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mRecyclerImage: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var mMap: GoogleMap

    private var mAdapter: FieldDetailSportAdapter? = null

    private var mImageAdapter: FieldDetailImageAdapter? = null

    private var lat : Double = 100.0

    private var long : Double = 100.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater,
                com.example.keviniswara.bookinglapang.R.layout.fragment_field_detail, container, false)

        val view: View = mBinding.root


        fieldName = arguments!!.getString("field_name")

        mPresenter = initPresenter()

        mPresenter.bind(this)

        mPresenter.retrieveListOfFieldFromFirebase(fieldName)

        mPresenter.retrieveListOfSportFromFirebase(fieldName)

        mPresenter.retrieveListOfImagesFromFirebase(fieldName)

        mRecyclerView = mBinding.recyclerButtonDate

        mRecyclerImage = mBinding.recyclerRestaurantImages

        mBinding.ipButtonBookNow.setOnClickListener({
            val arguments = Bundle()
            val fragment = SearchFieldFragment()
            arguments.putString("field_name", fieldName)
            fragment.arguments = arguments
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(com.example.keviniswara.bookinglapang.R.id.content, fragment).addToBackStack(fragment
                    .javaClass.simpleName)
            ft.commit()
        })

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        initFieldName(fieldName);

        return view;
    }

    override fun initPresenter(): FieldDetailContract.Presenter {
        val presenter: FieldDetailPresenter = FieldDetailPresenter()
        return presenter
    }

    fun initFieldName(fieldName: String){

        mBinding.ipTextViewRestaurantName.setText(fieldName);
    }

    override fun initFieldData(fieldData: Field?) {
        lat = fieldData!!.lat
        long = fieldData!!.long
        mBinding.ipTextViewRestaurantLocation.setText(fieldData!!.address);
        mBinding.ipTextViewRestaurantDetailsText.setText(fieldData.about)
        if(mMap != null){
            val fieldPosition = LatLng(lat, long)
            mMap.addMarker(MarkerOptions().position(fieldPosition).title(fieldName))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(fieldPosition))
        }

    }

    override fun initAddress(address: String) {

        mBinding.ipTextViewRestaurantLocation.setText(address);
    }

    override fun initListofSport(sportList: MutableList<String?>) {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.setItemAnimator(DefaultItemAnimator())
        mRecyclerView.hasFixedSize()
        mRecyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager.orientation)

        mRecyclerView.addItemDecoration(dividerItemDecoration)
        mAdapter = FieldDetailSportAdapter(sportList, this)
        mRecyclerView.adapter = mAdapter
    }
    override fun initListofimage(sportList: MutableList<String?>) {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerImage.setItemAnimator(DefaultItemAnimator())
        mRecyclerImage.hasFixedSize()
        mRecyclerImage.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this.activity,
                linearLayoutManager .orientation)

        mRecyclerImage.addItemDecoration(dividerItemDecoration)
        mImageAdapter = FieldDetailImageAdapter(sportList, this)
        mRecyclerImage.adapter = mImageAdapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val fieldPosition = LatLng(lat, long)
        mMap.addMarker(MarkerOptions().position(fieldPosition).title(fieldName))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fieldPosition))
    }
}
