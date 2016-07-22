package com.muks.employee.employee;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
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

    @Test
    public void testMyFirst() {
        System.out.println("# My first test.");
        Assert.assertEquals(true, true);
    }

    @Test
    public void testMySecond() {
        System.out.println("# My Second test.");
        Assert.assertEquals(true, true);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void shouldGetCountOfEmployeesTest() {
        EmployeeController employeeController =new EmployeeController(new EmployeeService());
        Assert.assertEquals(10,employeeController.getProjectedEmployeeCount());
    }

    @Test
    public void firstMockTest() {
        // create mock
        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
        PowerMockito.when(mock.getEmployeeCount()).thenReturn(8);

        EmployeeController employeeController = new EmployeeController(mock);
        Assert.assertEquals(16, employeeController.getProjectedEmployeeCount());
    }

    @Test
    public void verifyMethodInvokationTest() {
        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
        EmployeeController employeeController = new EmployeeController(mock);

        Employee employee = new Employee();
        employeeController.saveEmployee(employee);

        //Verifying that controller did call the
        //saveEmployee() method on the mocked service instance.
        Mockito.verify(mock).saveEmployee(employee);
    }
}
