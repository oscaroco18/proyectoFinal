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


@WebServlet("/confirmarPedido")
public class ConfirmarPedido extends HttpServlet {

    /**
     * Método del servlet que responde a una petición GET.
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

      HttpSession session = request.getSession();
      Usuarios usuario = (Usuarios)session.getAttribute("usuario");
      if(usuario==null)
      {
        response.sendRedirect(".html");////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      }
      List<Platos> carroPlatos = (List<Platos>)session.getAttribute("carroPlatos");
      if(carroPlatos == null){
        carroPlatos = new ArrayList<Platos>();
        session.setAttribute("carroPlatos",carroPlatos);
      }

      int i=0;
      try (DBManager manager = new DBManager())
      {
        Pedidos pedido = (Pedidos)session.getAttribute("pedido");
        if(usuario==null)
        {
          response.sendRedirect(".html");/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        String direccionPedido = request.getParameter("direccionPedido");

        manager.insertarPedido(usuario.getUsuario(), pedido.getRestaurante(),pedido.getLocalidad(), direccionPedido, pedido.getPrecioTotal());
        //Obviamente, el estado no hace falta ponerlo como parámetro, se supone que al insertar es el primer estado (encargado o parecido)

        int idUltimoPedido = manager.getIdUltimoPedido(usuario.getId());

        for(Platos plato: pedido.getPlatos())
        {
            manager.insertarPlatoEnPedido(plato.getId(),idUltimoPedido);
            //Insertamos plato a plato en el pedido
        }

        session.removeAttribute("pedido"); //Lo quitamos de sesión, ya que el pedido se ha efectuado (no está pendiente)

        response.sendRedirect("viewCliente");

      }
      catch (SQLException|NamingException e)
      {
        e.printStackTrace();
        response.sendError(500);
      }

    }
}
