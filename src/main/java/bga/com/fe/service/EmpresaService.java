package bga.com.fe.service;

import bga.com.fe.model.Empresa;
import bga.com.fe.repository.EmpresaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author bgarita, 22/01/2023
 */
@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAllDistinct() {
        // Hacer que los registros sean únicos
        List<Empresa> temp = empresaRepository.findAll(Sort.by(Sort.Direction.ASC, "nombreComercial"));
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa(0,"Todos"));

        // Recorro el array temporal validando si los registros existen o no
        // en el array definitivo. Solo agrego los que no existen.
        temp.forEach(registro -> {
            boolean existe = false;
            for (Empresa empresa : empresas) {
                if (empresa.getNombreComercial().equals(registro.getNombreComercial())) {
                    existe = true;
                    break;
                }
            } // end for

            // Si el registro no existe lo agrego.
            if (!existe) {
                empresas.add(registro);
            }

        });

        return empresas;
    }

}
