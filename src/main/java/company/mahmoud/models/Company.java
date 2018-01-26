
package company.mahmoud.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author mahmoudkassem
 */
@Entity
@Table(name = "companies")
public class Company {
    
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "company_id")
  private long companyId;
  
  @NotNull
  private String name;
  

  @OneToMany(mappedBy="company")
  @Cascade({CascadeType.ALL})
  private List<Employee> employees;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
  
  
    
}
