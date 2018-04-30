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


@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {

    /**
     * Método del servlet que responde a una petición GET.
     *
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {

      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter() ;

      HttpSession session = request.getSession();
      List<Platos> carroPlatos = new ArrayList<Platos>();

      Usuarios usuario = new Usuarios();
      usuario = (Usuarios)session.getAttribute("usuario");

      String idRestaurante = request.getParameter("idRestaurante");

      String [] idPlatos = request.getParameterValues("platosPedido");

      if(idPlatos!=null)
      {
        int i=0;
        try (DBManager manager = new DBManager())
        {
          Restaurantes restaurante = new Restaurantes();
          restaurante = (Restaurantes)manager.getRestaurante(Integer.parseInt(idRestaurante));

          float precioTotal=0;
          for(i=0;i<idPlatos.length;i++)
          {
            Platos plato = manager.getPlato(Integer.parseInt(idPlatos[i]));
            carroPlatos.add(plato);
            precioTotal= precioTotal+ plato.getPrecio();
          }
          //Ya tenemos la lista de platos

          Pedidos pedido = new Pedidos();
          pedido.setUsuario(usuario.getNombre());
          pedido.setRestaurante(restaurante.getNombre());
          pedido.setPlatos(carroPlatos);
          pedido.setPrecioTotal(precioTotal);
          pedido.setLocalidad(restaurante.getLocalidad());
          //El estado se lo podemos poner en la misma consulta SQL (el estado inicial, ya lo cambiaran los gestores según vaya avanzando el pedido)

          //Ponemos el pedido en sesion
          session.setAttribute("pedido", pedido);

          //Redirigimos a la vista del carrito
          response.sendRedirect("viewCarrito");
        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }
      }
      else
      {
        response.sendRedirect("viewRestaurante?id="+idRestaurante);
      }

    }
}
