package com.softwaretree.jdxandroidrelationshipsexample.model

/**
 * A class describing an Employee.
 *
 * Special note: the deptId attribute is declared as an IMPLICIT_ATTRIB in the mapping specification
 * for the class SimpleEmp; and so, it will automatically be initialized with the related value in
 * a referenced (SimpleDept) object when an instance of SimpleEmp is persisted by JDX ORM.
 * If deptId had not been declared as an IMPLICIT_ATTRIB in the mapping specification, it should
 * be explicitly initialized outside of JDX before the instance of a SimpleEmp is persisted.
 *
 * @author Damodar
 */
class SimpleEmp {
    var empId: String? = null
    var empName: String? = null
    var title: String? = null
    var ssn: String? = null
    var salary: Float = 0.toFloat()
    var deptId: Int = 0
    var dept: SimpleDept? = null     // identified by deptId
    var address: SimpleAddr? = null  // identified by empId

    constructor() : super() {
    }

    constructor(empId: String, empName: String, title: String, ssn: String, salary: Float,
                dept: SimpleDept, address: SimpleAddr) : super() {
        this.empId = empId
        this.empName = empName
        this.title = title
        this.ssn = ssn
        this.salary = salary
        this.dept = dept
        this.address = address
    }

}
