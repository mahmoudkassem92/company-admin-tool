
package company.mahmoud.models;


import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author mahmoudkassem
 */
@Transactional
public interface CompanyDao extends CrudRepository<Company, Long>{
    
    Optional<Company> findByCompanyId(Long companyId);

    }
