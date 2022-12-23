package bga.com.fe.service;

import bga.com.fe.model.Detalle;
import bga.com.fe.repository.DetalleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 06/12/2022
 */
@Service
public class DetalleService {
    @Autowired
    private DetalleRepository detalleRepository;
    
    public Detalle save(Detalle detalle) {
        return detalleRepository.save(detalle);
    }
    
    public Detalle findByClave(String clave) {
        return detalleRepository.findByClave(clave);
    }
    
    public Optional<Detalle> findById(Integer id) {
        return detalleRepository.findById(id);
    }
}
