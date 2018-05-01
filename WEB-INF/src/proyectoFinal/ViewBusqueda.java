package proyectoFinal;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.lang.Object.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import proyectoFinal.*;

/**
 *
 */
@WebServlet("/viewBusqueda")
public class ViewBusqueda extends HttpServlet {

    /**
     * Método del servlet que responde a una petición GET.
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        // establece ContentType y sistema de codificación de caracteres
        response.setContentType("text/html; charset=UTF-8");

        // obtiene un PrintWriter para escribir la respuesta
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Usuarios usuario = (Usuarios)session.getAttribute("usuario");
        if(usuario==null)
        {
          response.sendRedirect(".html");//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        try(DBManager manager = new DBManager())
        {
          String nombre = request.getParameter("nombreRestaurante");
          if(nombre.equals(""))
          {
            nombre = null;
          }

          int localidad = Integer.parseInt(request.getParameter("localidadRestaurante"));

          int especialidad = Integer.parseInt(request.getParameter("especialidadRestaurante"));

          String []stringParaCeliacos = request.getParameterValues("paraCeliacos");
          boolean paraCeliacos = false;
          if(stringParaCeliacos == null)
          {
            paraCeliacos = false;
          }
          else{
            int restauranteCeliacos = Integer.parseInt(stringParaCeliacos[0]);

            if(restauranteCeliacos == 1)
            {
              paraCeliacos = true;
            }
          }
          List<Restaurantes> listaRestaurantesBusqueda = new ArrayList<Restaurantes>();

          listaRestaurantesBusqueda = manager.getBusquedaRestaurantes(nombre, localidad, especialidad, paraCeliacos);
          request.setAttribute("listaRestaurantesBusqueda", listaRestaurantesBusqueda);

          RequestDispatcher  rd =request.getRequestDispatcher("busqueda.jsp");
          rd.forward(request , response);

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

      }
    }
