package com.example.keviniswara.bookinglapang.profile.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.keviniswara.bookinglapang.MainActivity

import com.example.keviniswara.bookinglapang.R
import com.example.keviniswara.bookinglapang.databinding.FragmentProfileBinding
import com.example.keviniswara.bookinglapang.login.view.LoginActivity
import com.example.keviniswara.bookinglapang.utils.Database
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding
    private val mAuth =  FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,
                container, false)

        mBinding.buttonLogout.setOnClickListener(View.OnClickListener {
            mAuth.signOut()
            Database.updateTokenId("")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        })

        return mBinding.root
    }
}
