/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.filter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


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