package com.sumitt.findhome.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.sumitt.findhome.api.TruliaService
import com.sumitt.findhome.model.Homes

/**
 * ViewModel class to load data from backend and post to UI
 * @author sumit.T
 * */
class ListingViewModel : ViewModel() {

    companion object {
        private val TAG: String = ListingViewModel::class.java.simpleName
    }

    lateinit var truliaService: TruliaService

    var start: Int = 0
    var end: Int = 25

    var listings: MutableLiveData<List<Homes>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            loadData(field)
            return field
        }

    fun loadData(result: MutableLiveData<List<Homes>>?) {
        result?.apply {
            Log.d(TAG, "loadData()")
            truliaService = TruliaService.newInstance()
            truliaService.initService()
            truliaService.getListings(start, end, object : TruliaService.ResponseListener {
                override fun onSuccess(response: List<Homes>) {
                    Log.d(TAG, "Response : " + Gson().toJson(response.toString()))
                    result?.postValue(response)
                }

                override fun onError(errorMsg: String) {
                    Log.e(TAG, errorMsg)
                    result?.postValue(null)
                }
            })
        }
    }
}