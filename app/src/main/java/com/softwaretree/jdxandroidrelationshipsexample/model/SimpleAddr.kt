package com.softwaretree.jdxandroidrelationshipsexample.model

/**
 * A class describing an Address.
 *
 * Special note: the addrId attribute is declared as an IMPLICIT_ATTRIB in the mapping specification
 * for the class SimpleAddr; and so, it will automatically be initialized with the related value in
 * the referencing (SimpleEmp) object when that instance of SimpleEmp is persisted by JDX ORM.
 * If addrId had not been declared as an IMPLICIT_ATTRIB in the mapping specification, it should
 * be explicitly initialized outside of JDX before the instance of a SimpleAddr is persisted.
 *
 * @author Damodar
 */
class SimpleAddr {
    var addrId: String? = null
    var addr1: String? = null
    var addr2: String? = null
    var city: String? = null
    var state: String? = null
    var zip: String? = null
    var country: String? = null

    constructor() : super() {
    }

    constructor(addr1: String, addr2: String?, city: String, state: String, zip: String, country: String) : super() {
        this.addr1 = addr1
        this.addr2 = addr2
        this.city = city
        this.state = state
        this.zip = zip
        this.country = country
    }

}
