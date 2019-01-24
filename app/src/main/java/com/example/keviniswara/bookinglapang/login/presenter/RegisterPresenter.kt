package com.example.keviniswara.bookinglapang.login.presenter

import android.util.Log
import com.example.keviniswara.bookinglapang.login.RegisterContract
import com.example.keviniswara.bookinglapang.model.User
import com.example.keviniswara.bookinglapang.utils.Database
import com.example.keviniswara.bookinglapang.utils.TextUtils
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class RegisterPresenter : RegisterContract.Presenter {

    private var mView: RegisterContract.View? = null

    override fun register() {
        val email = mView?.getEmail()
        val name = mView?.getName()
        val phoneNumber = mView?.getPhoneNumber()
        val password = mView?.getPassword()
        val confirmPassword = mView?.getPasswordConfirmation()

        when {
            email.isNullOrEmpty() -> nullErrorMessage("Email")
            name.isNullOrEmpty() -> nullErrorMessage("Nama")
            phoneNumber.isNullOrEmpty() -> nullErrorMessage("No. telepon")
            password.isNullOrEmpty() -> nullErrorMessage("Password")
            confirmPassword.isNullOrEmpty() -> nullErrorMessage("Confirm password")
            password != confirmPassword -> confirmPasswordNotMatchErrorMessage()
            !TextUtils.verifyPhone(phoneNumber) -> phoneNumberInvalidErrorMessage()
        }

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !name.isNullOrEmpty() &&
                !phoneNumber.isNullOrEmpty() && !confirmPassword.isNullOrEmpty() &&
                password == confirmPassword && TextUtils.verifyPhone(phoneNumber)) {
            mView!!.getAuth().createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener({ task ->
                        if (task.isSuccessful) {
                            val tokenId = FirebaseInstanceId.getInstance().token
                            //Add data to database
                            val user = User(name!!, email, phoneNumber!!, 1, null, null, null, tokenId!!)
                            Database.setUsers(user)
                            val newUser = FirebaseAuth.getInstance().currentUser

                            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()

                            newUser!!.updateProfile(profileUpdates)
                            mView!!.startMainActivity()
                        } else {
                            when {
                                task.exception is FirebaseAuthWeakPasswordException -> {
                                    Log.d("REGISTER", "Password should be at least 6 characters")
                                    weakPasswordErrorMessage()
                                }
                                task.exception is FirebaseAuthInvalidCredentialsException -> {
                                    Log.d("REGISTER", "The email address is badly formatted")
                                    invalidEmailFormatErrorMessage()
                                }
                                task.exception is FirebaseAuthUserCollisionException -> {
                                    Log.d("REGISTER", "The email address is already in use by another account.")
                                    emailAlreadyInUseErrorMessage()
                                }
                            }
                            Log.d("REGISTER", task.exception.toString())
                        }
                    })
        }
    }

    private fun phoneNumberInvalidErrorMessage() {
        mView!!.getErrorMessage().text = "Nomor telepon invalid"
    }

    private fun confirmPasswordNotMatchErrorMessage() {
        mView!!.getErrorMessage().text = "Password dan confirm password tidak cocok."
    }

    private fun nullErrorMessage(content: String) {
        mView!!.getErrorMessage().text = content + " wajib diisi."
    }

    private fun weakPasswordErrorMessage() {
        mView!!.getErrorMessage().text = "Password minimal terdiri dari 6 karakter."
    }

    private fun invalidEmailFormatErrorMessage() {
        mView!!.getErrorMessage().text = "Email tidak valid."
    }

    private fun emailAlreadyInUseErrorMessage() {
        mView!!.getErrorMessage().text = "Email sudah digunakan oleh akun lain."
    }

    override fun bind(view: RegisterContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }
}