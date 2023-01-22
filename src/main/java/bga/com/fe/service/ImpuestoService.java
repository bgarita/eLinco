package bga.com.fe.service;

import bga.com.fe.model.Impuesto;
import bga.com.fe.repository.ImpuestoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 15/01/2023
 */
@Service
public class ImpuestoService {
    @Autowired
    private ImpuestoRepository impuestoRepository;
    
    public Impuesto save(Impuesto impuesto) {
        return impuestoRepository.save(impuesto);
    }
    
    public List<Impuesto> findCodigoImpuesto(String codigoImpuesto) {
        return impuestoRepository.findCodigoImpuesto(codigoImpuesto);
    }
    
    public Optional<Impuesto> findById(Integer id) {
        return impuestoRepository.findById(id);
    }
}
