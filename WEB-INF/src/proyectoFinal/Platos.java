package proyectoFinal;

public class Platos
{
  private int id;
	private String nombre;
  private String restaurante;
  private float precio;

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

  public String getRestaurante()
  {
		return restaurante;
	}

	public void setRestaurante(String restaurante)
  {
		this.restaurante = restaurante;
	}

  public float getPrecio()
  {
  	return precio;
	}

	public void setPrecio(float precio)
  {
		this.precio = precio;
	}

}
