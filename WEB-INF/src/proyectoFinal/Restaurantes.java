package proyectoFinal;

public class Restaurantes
{
  private int id;
	private String nombre;
  private String correoElectronico;
  private String direccion;
  private String localidad;
  private int telefono;
  private String especialidad;
  private int paraCeliacos;
  private String foto;
  private String maps;
  

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

  public String getEspecialidad()
  {
		return especialidad;
	}

	public void setEspecialidad(String especialidad)
  {
		this.especialidad = especialidad;
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

  public int getTelefono()
  {
    return telefono;
  }

  public void setTelefono(int telefono)
  {
    this.telefono = telefono;
  }

  public int getParaCeliacos()
  {
		return paraCeliacos;
	}

	public void setParaCeliacos(int paraCeliacos)
  {
		this.paraCeliacos = paraCeliacos;
	}

  public String getFoto()
  {
    return foto;
  }

  public void setFoto(String foto)
  {
    this.foto = foto;
  }

  public String getMaps()
  {
    return maps;
  }

  public void setMaps(String maps)
  {
    this.maps = maps;
  }






	public String toString()
  {
		return id + " - " + "RESTAURANTE "+ nombre + " - " + direccion + " (" + localidad + ")" ;
	}
}
