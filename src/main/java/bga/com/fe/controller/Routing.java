package bga.com.fe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author bgarita, 06/12/2022
 */
@Controller
public class Routing {
    
    // Establecer la raíz del sitio
    @RequestMapping("/")
    public String home(){
        return "index"; // Nombre de la página HTML que será desplegada. Está en templates.
    }
} // end class
