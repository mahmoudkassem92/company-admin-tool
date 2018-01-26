package company.mahmoud.controllers;

import company.mahmoud.exceptions.CompanyNotFoundException;
import com.google.common.collect.Lists;
import java.net.URI;

import java.util.List;
import company.mahmoud.models.Company;
import company.mahmoud.models.CompanyDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class CompanyController {

    @Autowired
    private CompanyDao companyDao;

    @RequestMapping(value = "/companies", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public ResponseEntity<Void> createCompany(@RequestBody(required = true) Company company) {

        try {
            companyDao.save(company);
            return ResponseEntity.created(new URI("/companies/" + company.getCompanyId())).build();
        } catch (Exception e) {
            // log excpetion first, then return Conflict (409)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    @ResponseBody
    public List<Company> listAllCompanies() {
        return Lists.newArrayList(companyDao.findAll());
    }

    @RequestMapping(value = "/companies/{companyId}",
            method = RequestMethod.GET)
    @ResponseBody
    public Company getCompanyDetails(
            @PathVariable("companyId") long companyId) {

        this.validateCompany(companyId);
        return companyDao.findOne(companyId);
    }

    @RequestMapping(value = "/companies/{companyId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteCompany(
            @PathVariable("companyId") long companyId) {
        this.validateCompany(companyId);
        try {
            companyDao.delete(companyId);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
       @RequestMapping(value = "/companies/{companyId}",
            method = RequestMethod.PUT,
            headers = "Accept=application/json")
    public ResponseEntity<?> updateCompany(
            @PathVariable("companyId") long companyId,
            @RequestBody  Company company 
    ) {
        this.validateCompany(companyId);

        return companyDao.findByCompanyId(companyId)
                .map(oldcompany -> {
                    oldcompany.setName(company.getName());
                           companyDao
                            .save(oldcompany);
                    return ResponseEntity.accepted().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private void validateCompany(long companyId) {
        this.companyDao.findByCompanyId(companyId).orElseThrow(
                () -> new CompanyNotFoundException(companyId));
    }

}
