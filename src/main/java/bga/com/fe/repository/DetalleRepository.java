package bga.com.fe.repository;

import bga.com.fe.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bgarita, 06/12/2022
 */
public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
    
    @Query("from Detalle d where d.clave = ?1")
    public Detalle findByClave(String clave);
}
