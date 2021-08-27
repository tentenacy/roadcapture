package com.untilled.roadcapture.features.signup

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object SignupBindingAdapters {

    @JvmStatic
    @BindingAdapter("emailValidation")
    fun setEmailValidation(view: EditText, text: String){

    }

    @JvmStatic
    @BindingAdapter("emailValidationAttrChanged")
    fun setEmailValidationInverseBindingListener(view: EditText, listener: InverseBindingListener){
        view.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "emailValidation", event = "emailValidationAttrChanged")
    fun getEmailValidation(view: EditText): String{
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("passwordValidation")
    fun setPasswordValidation(view: EditText, text: String){

    }

    @JvmStatic
    @BindingAdapter("passwordValidationAttrChanged")
    fun setPasswordValidationInverseBindingListener(view: EditText, listener: InverseBindingListener){
        view.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "passwordValidation", event = "passwordValidationAttrChanged")
    fun getPasswordValidation(view: EditText): String{
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("passwordVerification")
    fun setPasswordVerification(view: EditText, text: String){

    }

    @JvmStatic
    @BindingAdapter("passwordVerificationAttrChanged")
    fun setPasswordVerificationInverseBindingListener(view: EditText, listener: InverseBindingListener){
        view.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "passwordVerification", event = "passwordVerificationAttrChanged")
    fun getPasswordVerification(view: EditText): String{
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("usernameValidation")
    fun setUsernameValidation(view: EditText, text: String){

    }

    @JvmStatic
    @BindingAdapter("usernameValidationAttrChanged")
    fun setUsernameValidationInverseBindingListener(view: EditText, listener: InverseBindingListener){
        view.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "usernameValidation", event = "usernameValidationAttrChanged")
    fun getUsernameValidation(view: EditText): String{
        return view.text.toString()
    }
}