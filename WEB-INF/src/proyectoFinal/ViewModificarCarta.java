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
@WebServlet("/viewModificarCarta")
public class ViewModificarCarta extends HttpServlet {

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

        try(DBManager manager = new DBManager())
        {
          HttpSession session = request.getSession();
          Usuarios usuario = (Usuarios)session.getAttribute("usuario");
          if(usuario==null)
          {
            response.sendRedirect("inicioFallido.html");
          }

          Restaurantes restaurante = new Restaurantes();

          restaurante = (Restaurantes)manager.getRestaurante(Integer.parseInt(request.getParameter("id")));

          List<Platos> carta = new ArrayList<Platos>();

          carta = manager.getCarta(restaurante.getNombre());

          request.setAttribute("restaurante",restaurante);
          request.setAttribute("carta",carta);
          request.setAttribute("usuario",usuario);

          RequestDispatcher  rd =request.getRequestDispatcher("modificarCarta.jsp");
          rd.forward(request , response);

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
