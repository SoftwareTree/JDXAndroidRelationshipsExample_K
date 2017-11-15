package com.softwaretree.jdxandroidrelationshipsexample.model

/**
 * A class describing a Company. It has 0 or more departments and 0 or more foreign locations.
 *
 * @author Damodar
 */
class SimpleCompany {
    var companyId: String? = null
    var companyName: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var foreignLocations: Array<SimpleForeignLocation>? = null
    var depts: List<SimpleDept>? = null // identified by companyId

    constructor() : super() {
    }

    constructor(companyId: String, companyName: String, city: String, state: String, country: String) : super() {
        this.companyId = companyId
        this.companyName = companyName
        this.city = city
        this.state = state
        this.country = country
    }

}
