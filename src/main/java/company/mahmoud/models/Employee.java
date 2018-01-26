package company.mahmoud.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author mahmoudkassem
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String address;

    @NotNull
    private Float salary;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee(String email, String name, String surname, String address, Float salary, Company company) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.salary = salary;
        this.company = company;
    }

    public Employee(long id, String email, String name, String surname, String address, Float salary, Company company) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.salary = salary;
        this.company = company;
    }
    
    

    public Employee() {
    }

}
