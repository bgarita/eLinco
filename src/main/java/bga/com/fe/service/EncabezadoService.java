package bga.com.fe.service;

import bga.com.fe.model.Encabezado;
import bga.com.fe.repository.EncabezadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 06/12/2022
 */
@Service
public class EncabezadoService {
    @Autowired
    private EncabezadoRepository encabezadoRepository;
    
    public Encabezado save(Encabezado encabezado) {
        return encabezadoRepository.save(encabezado);
    }
    
    public List<Encabezado> findByNumeroEmisor(String numeroEmisor) {
        return encabezadoRepository.findByNumeroEmisor(numeroEmisor);
    }
    
    public Encabezado findByClave(String clave) {
        return encabezadoRepository.findByClave(clave);
    }
}
