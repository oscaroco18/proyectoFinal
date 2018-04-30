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

public class Pedidos
{
  private int id;
  private String fecha;
  private String usuario;
  private String restaurante;
  private String localidad;
  private String direccion;
  private float precioTotal;
  private String estado;
  private List<Platos> platos;


  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

	public String getFecha()
  {
		return fecha;
	}

	public void setFecha(String fecha)
  {
		this.fecha = fecha;
	}

  public String getUsuario()
  {
		return usuario;
	}

	public void setUsuario(String usuario)
  {
		this.usuario = usuario;
	}

  public String getRestaurante()
  {
    return restaurante;
  }

  public void setRestaurante(String restaurante)
  {
    this.restaurante = restaurante;
  }

  public String getDireccion()
  {
    return direccion;
  }

  public void setDireccion(String direccion)
  {
    this.direccion = direccion;
  }

  public String getLocalidad()
  {
    return localidad;
  }

  public void setLocalidad(String localidad)
  {
    this.localidad = localidad;
  }

  public float getPrecioTotal()
  {
    return precioTotal;
  }

  public void setPrecioTotal(float precioTotal)
  {
    this.precioTotal = precioTotal;
  }


  public void setPlatos(List<Platos> platos)
  {
    this.platos = platos;
  }

  public List<Platos> getPlatos()
  {
    return platos;
  }

  public String getEstado()
  {
    return estado;
  }


  public void setEstado(String estado)
  {
    this.estado = estado;
  }
}
