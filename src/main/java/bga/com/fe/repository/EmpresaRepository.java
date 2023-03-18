package bga.com.fe.repository;

import bga.com.fe.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bgarita, 22/02/2023
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    
    
}
