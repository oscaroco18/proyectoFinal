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
@WebServlet("/viewCarrito")
public class ViewCarrito extends HttpServlet {

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
        response.sendRedirect(".html");/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      }

      request.setAttribute("usuario",usuario);

      Pedidos pedido = (Pedidos)session.getAttribute("pedido");
      if(pedido == null){
        pedido = new Pedidos();
        session.setAttribute("pedido",pedido);
      }

      request.setAttribute("pedido",pedido);

      RequestDispatcher rd = request.getRequestDispatcher("carritoPlatos.jsp");
      rd.forward (request ,response);

    }
}
