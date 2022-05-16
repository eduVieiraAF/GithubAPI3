package com.example.githubapi3.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapi3.data.model.RepoList
import com.example.githubapi3.data.api.RetroServiceInterface
import com.example.githubapi3.MyApplication
import com.example.githubapi3.ui.model.RepoVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mService: RetroServiceInterface
    private var liveDataList: MutableLiveData<List<RepoVO>>

    private var lastQueryParam: String = ""
    private var page = 0
    private var isLoading = false

    init {
        (application as MyApplication).getRetroComponent().inject(this)
        liveDataList = MutableLiveData()
    }

    fun getLiveObserver(): LiveData<List<RepoVO>> = liveDataList


    fun makeApiCall(queryParam: String = lastQueryParam, pagination: Boolean) {

        if (isLoading) return
        isLoading = true

        if (pagination.not()) page = 0
        lastQueryParam = queryParam

        CoroutineScope(Dispatchers.IO).launch {
            val response = mService.getDataFromAPI(queryParam, ++page)

            if (response.isSuccessful) liveDataList.postValue(transformResponse(response))
            else liveDataList.postValue(null)

            isLoading = false
        }
    }

    private fun transformResponse(response: Response<RepoList>) = response.body()?.items?.map {
        RepoVO.fromResponse(it)
    }
}