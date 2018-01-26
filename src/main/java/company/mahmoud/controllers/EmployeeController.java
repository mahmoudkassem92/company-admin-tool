
package company.mahmoud.controllers;

import com.google.common.collect.Lists;
import company.mahmoud.exceptions.CompanyNotFoundException;
import company.mahmoud.exceptions.EmployeeNotFoundException;

import java.net.URI;

import java.util.List;
import company.mahmoud.models.CompanyDao;
import company.mahmoud.models.Employee;
import company.mahmoud.models.EmployeeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mahmoudkassem
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CompanyDao companyDao;

    @RequestMapping(value = "/companies/{companyId}/employees",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> listCompanyEmployees(
            @PathVariable("companyId") long companyId) {
        this.validateCompany(companyId);
        return Lists.newArrayList(companyDao.findOne(companyId).getEmployees());
    }

    @RequestMapping(value = "/employees/{employeeId}",
            method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployeeDetails(
            @PathVariable("employeeId") long employeeId) {
        this.validateEmployee(employeeId);
        return employeeDao.findOne(employeeId);
    }

    @RequestMapping(value = "/companies/{companyId}/averageSalary",
            method = RequestMethod.GET)
    @ResponseBody
    public Float getCompanyAverageSalary(
            @PathVariable("companyId") long companyId) {
        this.validateCompany(companyId);
        Float avgSalary = employeeDao.findAvgSalaryByCompanyId(companyId);
        return avgSalary == null ? 0 : avgSalary;
    }

    @RequestMapping(value = "/employees/{employeeId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable("employeeId") long employeeId) {
        this.validateEmployee(employeeId);
        try {
            employeeDao.delete(employeeId);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/companies/{companyId}/employees/{employeeId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteCompanyEmployee(
            @PathVariable("employeeId") long employeeId,
            @PathVariable("companyId") long companyId) {
        this.validateCompany(companyId);
        this.validateEmployee(employeeId);
        try {
            employeeDao.delete(employeeId);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/companies/{companyId}/employees",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<?> createCompanyEmployee(
            @PathVariable("companyId") long companyId,
            @RequestBody Employee employee
    ) {
        this.validateCompany(companyId);

        return companyDao.findByCompanyId(companyId)
                .map(company -> {
                    Employee emp = employeeDao
                            .save(new Employee(employee.getEmail(), employee.getName(),
                                    employee.getSurname(), employee.getAddress(), employee.getSalary(), company));
                    return ResponseEntity.created(URI.create("/employees/" + String.valueOf(emp.getId()))).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    @RequestMapping(value = "/companies/{companyId}/employees/{employeeId}",
            method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("companyId") long companyId,
            @PathVariable("employeeId") long employeeId,
            @RequestBody Employee employee
    ) {

        this.validateCompany(companyId);
        this.validateEmployee(employeeId);

        return companyDao.findByCompanyId(companyId)
                .map(company -> {
                    employee.setId(employeeId);
                    employee.setCompany(company);
                    employeeDao
                            .save(employee);
                    return ResponseEntity.accepted().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private void validateCompany(long companyId) {
        this.companyDao.findByCompanyId(companyId).orElseThrow(
                () -> new CompanyNotFoundException(companyId));
    }

    private void validateEmployee(long employeeId) {
        this.employeeDao.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(employeeId));
    }

}
