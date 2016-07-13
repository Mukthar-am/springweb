package com.muks.employee.employee.emailer;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by 15692 on 04/07/16.
 */



public class EmployeeTests  {

    @Test
    public void testMySample() {
        System.out.println("+ Sample testing");
        Assert.assertEquals(true, true);
    }
////public class EmployeeTests {
//
//    @Test(expectedExceptions = UnsupportedOperationException.class)
//    public void shouldGetCountOfEmployees() {
//        EmployeeController employeeController =new EmployeeController(new EmployeeService());
//        Assert.assertEquals(10,employeeController.getProjectedEmployeeCount());
//    }
//
//
//    @Test
//    public void firstMockTest() {
//        // create mock
//        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
//        PowerMockito.when(mock.getEmployeeCount()).thenReturn(8);
//
//        EmployeeController employeeController = new EmployeeController(mock);
//        Assert.assertEquals(8, employeeController.getProjectedEmployeeCount());
//    }
//
//
//    @Test
//    public void verifyMethodInvokationTest()
//    {
//        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
//        EmployeeController employeeController = new EmployeeController(mock);
//
//        Employee employee = new Employee();
//        employeeController.saveEmployee(employee);
//
//        //Verifying that controller did call the
//        //saveEmployee() method on the mocked service instance.
//        Mockito.verify(mock).saveEmployee(employee);
//    }

}
