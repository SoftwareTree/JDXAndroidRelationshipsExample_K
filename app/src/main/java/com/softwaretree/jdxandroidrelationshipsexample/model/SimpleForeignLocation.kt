package com.softwaretree.jdxandroidrelationshipsexample.model
/**
 * A class describing a foreign location of a company.
 *
 * @author Damodar
 */
class SimpleForeignLocation {
    var locationId: Int = 0 // auto-generated
    var companyId: String? = null
    var city: String? = null
    var country: String? = null

    constructor() : super() {
    }

    constructor(companyId: String, city: String, country: String) : super() {
        this.companyId = companyId
        this.city = city
        this.country = country
    }

}
