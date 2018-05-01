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
@WebServlet("/iniciarSesion")
public class IniciarSesion extends HttpServlet {

    /**
     * Método del servlet que responde a una petición GET.
     *
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        // establece ContentType y sistema de codificación de caracteres
        response.setContentType("text/html; charset=UTF-8");

        // obtiene un PrintWriter para escribir la respuesta
        PrintWriter out = response.getWriter();

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        try(DBManager manager = new DBManager())
        {
          Usuarios userEncontrado = manager.getUsuario(usuario, contrasena);

          if(userEncontrado!=null)
          {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", userEncontrado);

            response.sendRedirect("viewCliente");
          }
          else
          {
            response.sendRedirect("inicioFallido.html");
          }

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        // establece ContentType y sistema de codificación de caracteres
        response.setContentType("text/html; charset=UTF-8");

        // obtiene un PrintWriter para escribir la respuesta
        PrintWriter out = response.getWriter();

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        try(DBManager manager = new DBManager())
        {
          Usuarios userEncontrado = manager.getUsuario(usuario, contrasena);

          if(userEncontrado!=null)
          {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", userEncontrado);

            response.sendRedirect("viewCliente");
          }
          else
          {
            response.sendRedirect("inicioFallido.html");
          }

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
