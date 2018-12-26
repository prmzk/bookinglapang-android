package com.example.keviniswara.bookinglapangtest

interface BasePresenter<T> {
    fun bind(view: T)
    fun unbind()
}