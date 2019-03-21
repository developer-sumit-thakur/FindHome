package com.sumitt.findhome.model

class Homes {
    var bathrooms: Float? = null
    var bedrooms: Float? = null
    var city: String? = null
    var id: String? = null
    var listingType: String? = null
    var latitude: Float? = null
    var longitude: Float? = null
    var neighborhood: String? = null
    var numberOfPhotos: Int = 0
    var price: Float = 0.toFloat()
    var propertyType: String? = null
    var squareFeet: Float = 0.toFloat()
    var stateCode: String? = null
    var streetName: String? = null
    var streetNumber: String? = null
    var zipCode: String? = null
    var photos = ArrayList<String>()
}