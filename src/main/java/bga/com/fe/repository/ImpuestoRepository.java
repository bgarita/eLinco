package bga.com.fe.repository;

import bga.com.fe.model.Impuesto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bgarita, 15/01/2023
 */
public interface ImpuestoRepository extends JpaRepository<Impuesto, Integer> {
    
    @Query("from Impuesto i where i.codigoImpuesto = ?1")
    public List<Impuesto> findCodigoImpuesto(String codigoImpuesto);
}
