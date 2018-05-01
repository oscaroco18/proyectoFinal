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
@WebServlet("/crearCuenta")
public class CrearCuenta extends HttpServlet {

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

        try(DBManager manager = new DBManager())
        {
          //Miramos disponibilidad del usuario
          Boolean nombreUsuarioExistente = manager.getDisponibilidadUsuario(usuario);

          if(nombreUsuarioExistente == true)//El usuario ya existe
          {

            RequestDispatcher  rd =request.getRequestDispatcher("usuarioExistente.html");
            rd.forward(request , response);
          }
          else//El usuario está disponible
          {
            String contrasena = request.getParameter("contrasena");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String direccion = request.getParameter("direccion");
            String localidad = request.getParameter("localidad");
            int telefono = Integer.parseInt(request.getParameter("telefono"));
            String correoElectronico = request.getParameter("correoElectronico");
            String gestorDe = request.getParameter("gestorDe");

            //Insertamos el usuario en la BD
            manager.insertarUsuario(usuario, contrasena, nombre, apellidos, direccion, localidad, telefono, correoElectronico, gestorDe);

            //Iniciamos sesión con el usuaio
            response.sendRedirect("iniciarSesion?usuario="+ usuario +"&contrasena=" + contrasena);
          }

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
