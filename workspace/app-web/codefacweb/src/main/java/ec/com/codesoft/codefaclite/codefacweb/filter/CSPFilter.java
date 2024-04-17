/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * @author CARLOS_CODESOFT
 */
public class CSPFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Puedes realizar inicializaciones aquí si es necesario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Convierte ServletResponse a HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Configurar el encabezado CSP en la respuesta HTTP        
        httpResponse.setHeader("Content-Security-Policy", "default-src 'self'");

        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Puedes realizar tareas de limpieza aquí si es necesario
    }
}