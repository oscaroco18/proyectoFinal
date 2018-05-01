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
@WebServlet("/viewPedido")
public class ViewPedido extends HttpServlet {

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
          response.sendRedirect(".html");/////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        try(DBManager manager = new DBManager())
        {
            Pedidos pedido = new Pedidos();
            pedido = (Pedidos)manager.getPedido(Integer.parseInt(request.getParameter("id")));

            int idRestaurante = manager.getIdRestaurante(pedido.getRestaurante());
            request.setAttribute("idRestaurante", idRestaurante + "");
            request.setAttribute("pedido",pedido);
            request.setAttribute("usuario", usuario);

            RequestDispatcher  rd =request.getRequestDispatcher("pedido.jsp");
            rd.forward(request , response);

        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
