package com.example.keviniswara.bookinglapang

interface BasePresenter<T> {
    fun bind(view: T)
    fun unbind()
}