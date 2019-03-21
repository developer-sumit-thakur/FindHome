package com.sumitt.findhome.api

import com.sumitt.findhome.model.Homes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ServiceInterface {
    @GET("/listings")
    fun getListing(@QueryMap param: Map<String, Int>): Observable<List<Homes>>
}