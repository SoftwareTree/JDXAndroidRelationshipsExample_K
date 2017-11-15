package com.softwaretree.jdxandroidrelationshipsexample.model

/**
 * A class describing a department.
 *
 * @author Damodar
 */
class SimpleDept {
    var deptId: Int = 0
    var companyId: String? = null
    var deptName: String? = null

    constructor() : super() {
    }

    constructor(deptId: Int, companyId: String, deptName: String) : super() {
        this.deptId = deptId
        this.companyId = companyId
        this.deptName = deptName
    }

}
