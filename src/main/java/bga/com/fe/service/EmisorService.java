package bga.com.fe.service;

import bga.com.fe.model.Emisor;
import bga.com.fe.repository.EmisorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 07/01/2023
 */
@Service
public class EmisorService {
    @Autowired
    private EmisorRepository emisorRepository;
    
    public Emisor save(Emisor emisor) {
        return emisorRepository.save(emisor);
    }
    
    public List<Emisor> findByNombre(String nombre) {
        return emisorRepository.findByNombre(nombre);
    }
    
    public Optional<Emisor> findById(String numeroEmisor) {
        return emisorRepository.findById(numeroEmisor);
    }
    
    public List<Emisor> findAll() {
        return emisorRepository.findAll(Sort.by(Sort.Direction.ASC, "nombreEmisor"));
    }
}
