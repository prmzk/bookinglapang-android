package com.example.keviniswara.bookinglapang.user.profile

import com.example.keviniswara.bookinglapang.BasePresenter
import com.example.keviniswara.bookinglapang.BaseView

interface EditPasswordContract {
    interface View  : BaseView<Presenter> {
          fun getPassword(): String
          fun getNewPassword(): String
          fun getNewPasswordConfirm(): String
          fun moveBack()
          fun showSuccessMessage()
          fun setErrorMessage(error: String)
          fun hideKeyboard()
    }
    interface Presenter : BasePresenter<View> {
          fun passwordAuthentication()
    }
}