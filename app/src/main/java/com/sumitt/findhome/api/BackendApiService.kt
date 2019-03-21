package com.sumitt.findhome.api

import com.sumitt.findhome.model.Homes
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class TruliaService {

    interface ResponseListener {
        fun onSuccess(respones: List<Homes>)
        fun onError(errorMsg: String)
    }

    companion object {
        private val baseUrl = "https://trulia-interview-challenge.herokuapp.com/"

        lateinit var dataService: ServiceInterface
        var instance: TruliaService? = null

        fun newInstance(): TruliaService {
            if (instance == null) instance = TruliaService()
            return instance as TruliaService
        }
    }

    fun initService() {

        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        dataService = retrofit.create<ServiceInterface>(ServiceInterface::class.java)
    }

    fun getListings(start: Int, end: Int, responseListener: ResponseListener): Observable<List<Homes>>? {
        var retVal: Observable<List<Homes>>? = null

        try {
            var hashMap = HashMap<String, Int>()
            hashMap.put("start", start)
            hashMap.put("count", end)

            dataService.getListing(hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DisposableObserver<List<Homes>>() {
                        override fun onComplete() {}

                        override fun onNext(value: List<Homes>) {
                            if (responseListener != null) {
                                responseListener.onSuccess(value)
                            }
                        }

                        override fun onError(e: Throwable) {
                            if (responseListener != null) {
                                responseListener.onError("error: ${e.message}")
                            }
                        }
                    })
        } catch (ex: Exception) {
            if (responseListener != null) {
                responseListener.onError("error: ${ex.message}")
            }
        }

        return retVal
    }
}