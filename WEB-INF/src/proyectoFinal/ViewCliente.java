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
@WebServlet("/viewCliente")
public class ViewCliente extends HttpServlet {

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
          response.sendRedirect(".html");//////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        try(DBManager manager = new DBManager())
        {
          request.setAttribute("usuario", usuario);

          //AQUI INVOCAMOS AL manager PARA QUE ME DE LOS RESTAURANTES EN UNA CIUDAD POR PARAMETRO (LA DEL USUARIO)
          //Y LA PASAMOS COMO ATRIBUTO A LA VISTA
          List<Restaurantes> listaRestaurantesCercanos = new ArrayList<Restaurantes>();
          listaRestaurantesCercanos = manager.getListaRestaurantesCercanos(usuario.getLocalidad());
          request.setAttribute("listaRestaurantesCercanos", listaRestaurantesCercanos);

          //AQUI INVOCAMOS AL manager PARA QUE ME DE LOS PEDIDOS DEL USUARIO Y LOS PASAMOS COMO ATRIBUTO A LA VISTA
          List<Pedidos> listaPedidos = new ArrayList<Pedidos>();
          listaPedidos = manager.getPedidos(usuario.getUsuario());
          request.setAttribute("listaPedidos", listaPedidos);

          List<Localidades> listaLocalidades = new ArrayList<Localidades>();
          listaLocalidades = manager.getLocalidades();
          request.setAttribute("listaLocalidades", listaLocalidades);

          List<Especialidades> listaEspecialidades = new ArrayList<Especialidades>();
          listaEspecialidades = manager.getEspecialidades();
          request.setAttribute("listaEspecialidades", listaEspecialidades);

          RequestDispatcher  rd =request.getRequestDispatcher("cliente.jsp");
          rd.forward(request , response);

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
