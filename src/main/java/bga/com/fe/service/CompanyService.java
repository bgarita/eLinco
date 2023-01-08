package bga.com.fe.service;

import bga.com.fe.model.Company;
import bga.com.fe.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 15/12/2022
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    
    public Company save(Company company) {
        return companyRepository.save(company);
    }
    
    public List<Company> findByNombre(String nombre) {
        return companyRepository.findByNombre(nombre);
    }
    
    public Optional<Company> findById(String numeroReceptor) {
        return companyRepository.findById(numeroReceptor);
    }
    
    public List<Company> findAll() {
        return companyRepository.findAll(Sort.by(Sort.Direction.ASC, "nombreReceptor"));
    }
}
