
package company.mahmoud.models;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mahmoudkassem
 */
@Transactional
public interface EmployeeDao extends CrudRepository<Employee, Long>{
    
    @Query("SELECT e FROM Employee e WHERE e.id = :id AND e.company.companyId = :companyId")
    public Employee findByIdAndCompanyId(@Param("id") Long id,@Param("companyId")Long companyId);
        
    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.company.companyId = :companyId")
    public Float findAvgSalaryByCompanyId(@Param("companyId")Long companyId);
    
    Optional<Company> findById(Long companyId);

}
