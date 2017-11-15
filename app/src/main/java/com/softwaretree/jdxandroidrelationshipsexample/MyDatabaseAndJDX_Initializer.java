package com.softwaretree.jdxandroidrelationshipsexample;

import java.util.ArrayList;

import com.softwaretree.jdx.JDXS;
import com.softwaretree.jdx.JDXSetup;
import com.softwaretree.jdxandroid.DatabaseAndJDX_Initializer;
import com.softwaretree.jdxandroidrelationshipsexample.model.SimpleAddr;
import com.softwaretree.jdxandroidrelationshipsexample.model.SimpleCompany;
import com.softwaretree.jdxandroidrelationshipsexample.model.SimpleDept;
import com.softwaretree.jdxandroidrelationshipsexample.model.SimpleEmp;
import com.softwaretree.jdxandroidrelationshipsexample.model.SimpleForeignLocation;
import com.softwaretree.jx.JXResource;
import com.softwaretree.jx.JXSession;

import android.content.ContextWrapper;

/**
 * Application specific initialization code for setting up the database and the JDXA ORM.  
 *  
 * @author Damodar Periwal
 */
public class MyDatabaseAndJDX_Initializer extends DatabaseAndJDX_Initializer {

    public MyDatabaseAndJDX_Initializer(ContextWrapper contextWrapper, String dbName, int dbVersion,
            String ORMFileName, boolean forceCreateSchema) throws Exception {
        super(contextWrapper, dbName, dbVersion, ORMFileName, forceCreateSchema);
    }

    /**
     * Creates new application objects and stores them in the database using JDXA ORM.
     * In this app, these objects are populated only when the database schema is newly created.
     * 
     * @exception Exception
     */
    @Override
    protected void populateDatabase(JDXSetup jdxSetup, boolean newSchemaCreated) throws Exception {
        
        if (null == jdxSetup) {
            return;
        }
        
        if (!newSchemaCreated) {  // The database would have already been pre-populated.
        	return;
        }

        // Obtain ORM handles.
        JXResource jxResource = jdxSetup.checkoutJXResource();
        JXSession jxSessionHandle = jxResource.getJXSessionHandle();
        JDXS jdxHandle = jxResource.getJDXHandle();

        String companyClassName = SimpleCompany.class.getName();
        String employeeClassName = SimpleEmp.class.getName();

        try {
            String companyId = "C1";

            int deptId1 = 101;
            int deptId2 = 102;

            String empId1 = "E1";
            String empId2 = "E2";
            String empId3 = "E3";

            // Start a new transaction to make all the following statements (until tx_commit) to
            // execute as one unit of operation in the database
            jxSessionHandle.tx_begin();

            // First delete existing SimpleEmp and associated SimpleAddr objects
            jdxHandle.delete2(employeeClassName, "deptId=" + deptId1 + " OR " + "deptId=" + deptId2,
                    JDXS.FLAG_DEEP);

            // Now delete existing SimpleCompany and the associated SimpleForeignLocation and
            // SimpleDept objects
            jdxHandle.delete2(companyClassName, "companyId = " + "'" + companyId + "'",
                    JDXS.FLAG_DEEP);

            // ****************************************************************
            // Now create and insert a new SimpleCompany and 2 new SimpleDept objects in separate
            // calls to JDX
            // ****************************************************************
            SimpleCompany company1 = new SimpleCompany(companyId, "JDX Sample Corporation",
                    "San Jose", "CA", "USA");
            jdxHandle.insert(company1, JDXS.FLAG_SHALLOW, null); // SHALLOW because no departments are attached to be inserted with the company object

            SimpleDept dept1 = new SimpleDept(deptId1, companyId, "Department1");
            jdxHandle.insert(dept1, JDXS.FLAG_DEEP, null); // SHALLOW and DEEP are equivalent in this case

            SimpleDept dept2 = new SimpleDept(deptId2, companyId, "Department2");
            jdxHandle.insert(dept2, JDXS.FLAG_SHALLOW, null); // SHALLOW and DEEP are equivalent in this case

            // ****************************************************************
            // Example of Deep insert of BYVALUE contained objects (persistence by reachability)
            // ****************************************************************

            // First delete existing SimpleCompany and associated SimpleDept objects
            jdxHandle.delete2(companyClassName, "companyId = " + "'" + companyId + "'",
                    JDXS.FLAG_DEEP);

            // Now create a new SimpleCompany object and associated SimpleForeignLocation and
            // SimpleDept objects
            company1 = new SimpleCompany(companyId, "JDXA Sample Corporation",
                    "San Jose", "CA", "USA");
            
            SimpleForeignLocation[] foreignLocations = new SimpleForeignLocation[2];
            foreignLocations[0] = new SimpleForeignLocation(companyId, "France", "Paris");
            foreignLocations[1] = new SimpleForeignLocation(companyId, "China", "Shanghai");
            company1.setForeignLocations(foreignLocations);

            ArrayList<SimpleDept> depts = new ArrayList<SimpleDept>();
            depts.add(new SimpleDept(deptId1, companyId, "Department1"));
            depts.add(new SimpleDept(deptId2, companyId, "Department2"));
            company1.setDepts(depts);

            // Now insert the newly created SimpleCompany along with the associated
            // SimpleForeignLocation and SimpleDept objects with one JDX call
            jdxHandle.insert(company1, JDXS.FLAG_DEEP, null);

            // ****************************************************************
            // Create and insert a SimpleEmp objects along with the associated SimpleAddr object
            // ****************************************************************
            SimpleAddr addr1 = new SimpleAddr("111 Main Street", null,
                    "San Jose", "CA", "95129", "USA");
            SimpleEmp emp1 = new SimpleEmp(empId1, "John Smith", "Manager",
                    "111-222-3333", (float) 75000.00, dept1, addr1);

            jdxHandle.insert(emp1, JDXS.FLAG_DEEP, null);

            // ****************************************************************
            // Now create and insert multiple SimpleEmp objects along with their associated
            // SimpleAddr objects in one call to JDX.
            //
            // Because the addrId attribute of the SimpleAddr class is declared as an implicit
            // attribute in the mapping file, its value for an instance will automatically be
            // initialized by JDX using the empId value of the containing SimpleEmp object.
            // ****************************************************************
            ArrayList<SimpleEmp> employees = new ArrayList<SimpleEmp>();

            SimpleAddr addr2 = new SimpleAddr("222 State Street", null,
                    "Washington", "D.C.", "20502", "USA");
            SimpleEmp emp2 = new SimpleEmp(empId2, "Mark Clinton", "Engineer",
                    "444-555-6666", (float) 90000.00, dept1, addr2);
            employees.add(emp2);

            SimpleAddr addr3 = new SimpleAddr("333 City Boulevard", "Apartment 33",
                    "San Francisco", "CA", "94103", "USA");
            SimpleEmp emp3 = new SimpleEmp(empId3, "Bob Brown", "Consultant",
                    "777-888-9999", (float) 95000.00, dept2, addr3);
            employees.add(emp3);

            jdxHandle.insert(employees, JDXS.FLAG_DEEP, null);

            // ****************************************************************
            // Commit the transaction
            // ****************************************************************
            jxSessionHandle.tx_commit();

        } catch (Exception ex) {
            throw ex;
        } finally {
            jdxSetup.checkinJXResource(jxResource);
        }
    }
}