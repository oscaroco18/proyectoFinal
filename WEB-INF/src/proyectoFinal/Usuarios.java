package proyectoFinal;

public class Usuarios
{
  private int id;
  private String usuario;
  private String contrasena;
  private String nombre;
  private String apellidos;
  private String direccion;
  private String localidad;
  private int telefono;
  private String correoElectronico;
  private int gestorDe;


  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

	public String getNombre()
  {
		return nombre;
	}

	public void setNombre(String nombre)
  {
		this.nombre = nombre;
	}

  public String getApellidos()
  {
		return apellidos;
	}

	public void setApellidos(String apellidos)
  {
		this.apellidos = apellidos;
	}

  public int getTelefono()
  {
    return telefono;
  }

  public void setTelefono(int telefono)
  {
    this.telefono = telefono;
  }

  public String getUsuario()
  {
    return usuario;
  }

  public void setUsuario(String usuario)
  {
    this.usuario = usuario;
  }

  public String getContrasena()
  {
    return contrasena;
  }

  public void setContrasena(String contrasena)
  {
    this.contrasena = contrasena;
  }

  public String getCorreoElectronico()
  {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico)
  {
    this.correoElectronico = correoElectronico;
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


  public int getGestorDe()
  {
    return gestorDe;
  }

  public void setGestorDe(int gestorDe)
  {
    this.gestorDe = gestorDe;
  }


	public String toString()
  {
		return id + " - " + nombre + " " + apellidos;
	}
}
