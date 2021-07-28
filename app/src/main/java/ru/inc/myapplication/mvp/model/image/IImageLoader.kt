package ru.inc.myapplication.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}