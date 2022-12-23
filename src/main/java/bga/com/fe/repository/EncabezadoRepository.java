package bga.com.fe.repository;

import bga.com.fe.model.Encabezado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bgarita, 06/12/2022
 */
public interface EncabezadoRepository extends JpaRepository<Encabezado, Integer> {
    @Query("from Encabezado e where e.numeroEmisor = ?1")
    List<Encabezado> findByNumeroEmisor(String numeroEmisor);

    @Query("from Encabezado e where e.clave = ?1")
    public Encabezado findByClave(String clave);
}
