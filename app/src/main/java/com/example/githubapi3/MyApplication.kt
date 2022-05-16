package com.example.githubapi3

import android.app.Application
import com.example.githubapi3.data.di.DaggerRetroComponent
import com.example.githubapi3.data.di.RetroComponent
import com.example.githubapi3.data.di.RetroModule

class MyApplication: Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    fun getRetroComponent(): RetroComponent {

        return retroComponent
    }
}