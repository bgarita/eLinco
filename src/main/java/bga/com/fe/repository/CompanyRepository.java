package bga.com.fe.repository;

import bga.com.fe.model.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bgarita, 15/12/2022
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    
    @Query("from Company c where c.nombreReceptor like %?1%") 
    public List<Company> findByNombre(String nombre);
}
