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
@WebServlet("/gestionCarta")
public class GestionCarta extends HttpServlet {

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
          response.sendRedirect(".html");///////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        try(DBManager manager = new DBManager())
        {
          String idRestaurante = request.getParameter("idRestaurante");

          //////////////////////PARTE PARA BORRAR Y MODIFICAR
          String borrar = request.getParameter("borrarSeleccionados");
          String modificarPrecios = request.getParameter("modificarPrecio");

          int i = 0;
          int platosBorrados=0;
          int platosModificados=0;
          String nuevoPrecio;

          if((borrar!=null) || (modificarPrecios!=null))
          {
            String []platosABorrarModificar = request.getParameterValues("platosABorrarModificar");

            if(platosABorrarModificar!=null)
            {
              for(i = 0; i<platosABorrarModificar.length;i++)
              {
                //out.print("TENGO QUE MODIFICAR EL PLATO " + Integer.parseInt(platosABorrarModificar[i]));
                //out.print("</br>");
                if(borrar!=null)
                {
                    platosBorrados = (int)manager.borrarPlato(Integer.parseInt(platosABorrarModificar[i]));
                }else if(modificarPrecios!=null)
                {
                    nuevoPrecio = request.getParameter(platosABorrarModificar[i]);
                    if(!nuevoPrecio.equals(""))
                    {
                        platosModificados = (int)manager.modificarPlato(Float.parseFloat(nuevoPrecio),Integer.parseInt(platosABorrarModificar[i]));
                    }

                }

              }
            }
          }

          //////////////////////PARTE PARA AÑADIR
          String add = request.getParameter("addPlato");
          if(add!=null)
          {

            String nombrePlatoAnadir = request.getParameter("nombrePlatoAnadir");
            String precioPlatoAnadir = request.getParameter("precioPlatoAnadir");

            if(nombrePlatoAnadir != null)
            {
              if(!manager.verificarExistenciaPlato(Integer.parseInt(idRestaurante),nombrePlatoAnadir))
              {
                manager.insertarPlato(Integer.parseInt(idRestaurante), nombrePlatoAnadir, Float.parseFloat(precioPlatoAnadir));
              }

            }

          }

          response.sendRedirect("viewRestaurante?id="+idRestaurante);
          //out.print("</html>");
        }
        catch (SQLException|NamingException e)
        {
          e.printStackTrace();
          response.sendError(500);
        }

    }
}
