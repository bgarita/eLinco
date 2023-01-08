package bga.com.fe.repository;

import bga.com.fe.model.Emisor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bgarita, 07/01/2023
 */
public interface EmisorRepository extends JpaRepository<Emisor, String> {
    
    @Query("from Emisor e where e.nombreEmisor like %?1%") 
    public List<Emisor> findByNombre(String nombre);
}
