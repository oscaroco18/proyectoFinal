package proyectoFinal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Date;

public class DBManager implements AutoCloseable {

  private Connection connection;

  public DBManager() throws SQLException, NamingException
  {
      connect();
  }

  private void connect() throws SQLException, NamingException
  {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource) envCtx.lookup("jdbc/proyectoFinal");
      connection = ds.getConnection();
  }

  /**
   * Close the connection to the database if it is still open.
   *
   */
  public void close() throws SQLException
  {
      if (connection != null) {
          connection.close();
      }
      connection = null;
  }

    /**
     * Retorna el objeto usuario que coincide con el usuario y contrasena dados
     * Útil para el inicio de sesión, cuyo servlet guarda dicho objeto usuario en la sesión
     */
    public Usuarios getUsuario (String usuario, String contrasena) throws SQLException
    {
      Usuarios user = null;

      String query = "SELECT Usuarios.id, Usuarios.usuario, Usuarios.contrasena, Usuarios.nombre, Usuarios.apellidos, Usuarios.direccion, Localidades.nombre,"+
      "Usuarios.telefono, Usuarios.correoElectronico, Usuarios.gestorDe FROM Usuarios INNER JOIN Localidades ON Usuarios.localidad = Localidades.id WHERE usuario = ? AND contrasena = ?";

      try (PreparedStatement  st = connection.prepareStatement(query))
      {
        // Se  insertan  los  valores  en la  consulta:
        st.setString (1, usuario);
        st.setString(2, contrasena);
        ResultSet rs = st.executeQuery();

        if(rs.next())
        {
          user = new Usuarios();
          user.setId(rs.getInt(1));
          user.setUsuario(rs.getString(2));
          user.setContrasena(rs.getString(3));
          user.setNombre(rs.getString(4));
          user.setApellidos(rs.getString(5));
          user.setDireccion(rs.getString(6));
          user.setLocalidad(rs.getString(7));
          user.setTelefono(rs.getInt(8));
          user.setCorreoElectronico(rs.getString(9));
          user.setGestorDe(rs.getInt(10));

        }
        return user;
      }
    }

    /**
     * Retorna la lista de localidades en la BD
     * Útil para el menú desplegable de crear cuenta
     */
    public List<Localidades> getLocalidades() throws SQLException
    {

      List<Localidades> lista = new ArrayList<Localidades>();

      String query = "SELECT Localidades.id,Localidades.nombre FROM Localidades";

       try (PreparedStatement  st = connection.prepareStatement(query))
        {
            // Se  insertan  los  valores  en la  consulta:
            ResultSet rs = st.executeQuery();

            while(rs.next())
            {
            Localidades localidad = new Localidades();
            localidad.setId(rs.getInt(1));
            localidad.setNombre(rs.getString(2));
            lista.add(localidad);
            }
            return lista;
        }
    }

    /**
     * Retorna la lista de especialidades de la BD
     * Útil para el menú desplegable a la hora de buscar restaurantes
     */
    public List<Especialidades> getEspecialidades() throws SQLException
    {

      List<Especialidades> lista = new ArrayList<Especialidades>();

      String query = "SELECT Especialidades.id,Especialidades.nombre FROM Especialidades";

       try (PreparedStatement  st = connection.prepareStatement(query))
       {
          // Se  insertan  los  valores  en la  consulta:
          ResultSet rs = st.executeQuery();

          while(rs.next())
          {
            Especialidades especialidad = new Especialidades();
            especialidad.setId(rs.getInt(1));
            especialidad.setNombre(rs.getString(2));
            lista.add(especialidad);
          }
          return lista;
        }
    }


    /**
     * Retorna un bool indicando si el nombre de usuario ya existe en la BD o está disponible
     * Útil para la creación de nueva cuenta, para no crear dos usuarios con el mismo nombre de usuario
     */
    public boolean getDisponibilidadUsuario(String usuario) throws SQLException
     {

      String query = "SELECT * FROM Usuarios WHERE usuario = ?";

      try (PreparedStatement  st = connection.prepareStatement(query))
      {
        // Se  insertan  los  valores  en la  consulta:
        st.setString (1, usuario);
        ResultSet rs = st.executeQuery();

        if(rs.next())
        {
          return true; //Si existe el usuario, devuelve TRUE. La cuenta NO se puede crear con ese nombre
        }
        else
        {
          return false; //Si NO existe el usuairo, devuelve FALSE (la cuenta se puede crear con ese nombre)
        }
      }
    }


    /**
     * Retorna un booleano indicando si ha sido posible insertar el usuario (TRUE) o no (FLASE)
     * Útil para crear una nueva cuenta de usuario (para insertarlo en la base de datos)
     */
     public int insertarUsuario(String usuario, String contrasena, String nombre, String apellidos, String direccion, String localidad, int telefono, String correoElectronico, String gestorDe) throws SQLException
     {

       String query = "INSERT INTO Usuarios (usuario,contrasena,nombre,apellidos,direccion,localidad,telefono,correoElectronico,gestorDe) VALUES (?, ?, ?, ?, ?, (SELECT id FROM Localidades WHERE nombre= ? ), ?, ?, (SELECT id FROM Restaurantes WHERE nombre= ? ))";

       try (PreparedStatement  st = connection.prepareStatement(query))
       {
         // Se  insertan  los  valores  en la  consulta:
         st.setString (1, usuario);
         st.setString (2, contrasena);
         st.setString (3, nombre);
         st.setString (4, apellidos);
         st.setString (5, direccion);
         st.setString (6, localidad);
         st.setInt (7, telefono);
         st.setString (8, correoElectronico);
         st.setString (9, gestorDe);

         int rs = st.executeUpdate();

         return rs;
       }
     }

     /**
      * Retorna una lista de Restaurantes existentes en una determinada ciudad que se le pasa por parámetro
      * Útil para listar los Resturantes cercanos al usuario (en su misma ciudad) en la vista principal
      */
     public List<Restaurantes> getListaRestaurantesCercanos (String localidad) throws SQLException
     {

        List<Restaurantes> lista = new ArrayList<Restaurantes>();

        String query = "SELECT Restaurantes.id, Restaurantes.nombre, Restaurantes.direccion, Restaurantes.correoElectronico,"+
         " Localidades.nombre, Restaurantes.telefono, Especialidades.nombre, Restaurantes.paraCeliacos, Restaurantes.foto, Restaurantes.maps FROM Restaurantes INNER JOIN Localidades ON"+
          " Restaurantes.localidad = Localidades.id INNER JOIN Especialidades ON Especialidades.id = Restaurantes.especialidad WHERE Localidades.nombre= ?";

        try (PreparedStatement  st = connection.prepareStatement(query))
        {
          // Se  insertan  los  valores  en la  consulta:
          st.setString (1, localidad);

          ResultSet rs = st.executeQuery();

          while(rs.next())
          {
            Restaurantes restaurante = new Restaurantes();
            restaurante.setId(rs.getInt(1));
            restaurante.setNombre(rs.getString(2));
            restaurante.setDireccion(rs.getString(3));
            restaurante.setCorreoElectronico(rs.getString(4));
            restaurante.setLocalidad(rs.getString(5));
            restaurante.setTelefono(rs.getInt(6));
            restaurante.setEspecialidad(rs.getString(7));
            restaurante.setParaCeliacos(rs.getInt(8));
            restaurante.setFoto(rs.getString(9));
            restaurante.setMaps(rs.getString(10));
            lista.add(restaurante);
          }
          return lista;
        }
      }

      /**
       * Retorna el objeto Restaurantes dada la id del restaurante
       * Útil para obtener información de dicho Restaurante
       */
      public Restaurantes getRestaurante (int id) throws SQLException
       {
        Restaurantes restaurante = null;

        String query = "SELECT Restaurantes.id, Restaurantes.nombre, Restaurantes.direccion, Restaurantes.correoElectronico, Localidades.nombre, Restaurantes.telefono,"+
        " Especialidades.nombre, Restaurantes.paraCeliacos, Restaurantes.foto, Restaurantes.maps FROM Restaurantes INNER JOIN Localidades ON Restaurantes.localidad=Localidades.id INNER JOIN "+
        "Especialidades ON Restaurantes.especialidad=Especialidades.id WHERE Restaurantes.id=?";

        try (PreparedStatement  st = connection.prepareStatement(query))
        {
        // Se  insertan  los  valores  en la  consulta:
          st.setInt(1, id);
          ResultSet rs = st.executeQuery();

          if(rs.next())
          {
            restaurante = new Restaurantes();
            restaurante.setId(rs.getInt(1));
            restaurante.setNombre(rs.getString(2));
            restaurante.setDireccion(rs.getString(3));
            restaurante.setCorreoElectronico(rs.getString(4));
            restaurante.setLocalidad(rs.getString(5));
            restaurante.setTelefono(rs.getInt(6));
            restaurante.setEspecialidad(rs.getString(7));
            restaurante.setParaCeliacos(rs.getInt(8));
            restaurante.setFoto(rs.getString(9));
            restaurante.setMaps(rs.getString(10));
          }
          return restaurante;
        }
      }

      /**
       * Retorna la lista de Pedidos de un usuario dado por parámetro
       * Útil para listar los pedidos al usuario en la vista principal
       */
      public List<Pedidos> getPedidos (String usuario) throws SQLException
       {
        List<Pedidos> listaPedidos = new ArrayList<Pedidos>();

        String query = "SELECT Pedidos.id, Pedidos.fecha,Usuarios.usuario, Restaurantes.nombre, Pedidos.precioTotal, Pedidos.estado FROM Pedidos INNER JOIN Usuarios ON Usuarios.id = Pedidos.usuario " +
        " INNER JOIN Restaurantes ON Pedidos.restaurante = Restaurantes.id WHERE Usuarios.usuario = ? ORDER BY id DESC";

        try (PreparedStatement  st = connection.prepareStatement(query))
        {
        // Se  insertan  los  valores  en la  consulta:
          st.setString(1, usuario);
          ResultSet rs = st.executeQuery();

          while(rs.next())
          {
            Pedidos pedido = new Pedidos();
            pedido.setId(rs.getInt(1));
            pedido.setFecha(rs.getString(2));
            pedido.setUsuario(rs.getString(3));
            pedido.setRestaurante(rs.getString(4));
            pedido.setPrecioTotal(rs.getFloat(5));
            pedido.setEstado(rs.getString("estado"));
            listaPedidos.add(pedido);
          }
          return listaPedidos;
        }
      }

      /**
       * Retorna una lista de Platos de la carta de un restaurante dado por parámetro
       * Útil para mostrar la carta de los restaurantes
       */
      public List<Platos> getCarta (String restaurante) throws SQLException
      {
        List <Platos> carta = new ArrayList<Platos>();

        String query = "SELECT Platos.id,Platos.nombre,Restaurantes.nombre,Platos.precio FROM Platos INNER JOIN Restaurantes ON Platos.restaurante=Restaurantes.id "   + " WHERE Restaurantes.nombre= ?";
        try (PreparedStatement  st = connection.prepareStatement(query))
        {
            // Se  insertan  los  valores  en la  consulta:
            st.setString(1, restaurante);
            ResultSet rs = st.executeQuery();

            while(rs.next())
            {
                Platos plato = new Platos();
                plato.setId(rs.getInt(1));
                plato.setNombre(rs.getString(2));
                plato.setRestaurante(rs.getString(3));
                plato.setPrecio(rs.getFloat(4));
                carta.add(plato);
            }
        }
        return carta;
    }


    /**
     * Retorna una lista de Restaurantes resultado de la búsqueda introducida (puede ser Lista vacía)
     * Útil para mostrar el resultado de la búsqueda del usuario
     */
    public List<Restaurantes> getBusquedaRestaurantes (String nombre, int localidad, int especialidad, boolean paraCeliacos) throws SQLException
    {
      List <Restaurantes> listaRestaurantes = new ArrayList<Restaurantes>();

      String query = "SELECT Restaurantes.id, Restaurantes.nombre, Restaurantes.direccion, Restaurantes.correoElectronico, Localidades.nombre, Restaurantes.telefono,"+
      " Especialidades.nombre, Restaurantes.paraCeliacos, Restaurantes.foto, Restaurantes.maps FROM Restaurantes INNER JOIN Especialidades ON Especialidades.id = Restaurantes.especialidad " +
      " INNER JOIN Localidades ON Localidades.id = Restaurantes.localidad ";

      if((nombre != null)||(localidad!= 0)||(especialidad != 0)||(paraCeliacos == true))
      {
        query = query + " WHERE ";
      }

      if(nombre != null)
      {
        query = query + " Restaurantes.nombre = ? ";
        if((localidad!= 0)||(especialidad != 0)||(paraCeliacos == true))
        {
          query = query + " AND ";
        }
      }

      if(localidad != 0)
      {
        query = query + " Restaurantes.localidad = ? ";
        if((especialidad != 0)||(paraCeliacos == true))
        {
          query = query + " AND ";
        }
      }

      if(especialidad != 0)
      {
        query = query + " Restaurantes.especialidad = ? ";
        if((paraCeliacos == true))
        {
          query = query + " AND ";
        }
      }

      if(paraCeliacos == true)
      {
        query = query + " Restaurantes.paraCeliacos = true";
      }

      try (PreparedStatement  st = connection.prepareStatement(query))
      {
          // Se  insertan  los  valores  en la  consulta:
          int i = 1;
          if(nombre != null)
          {
              st.setString(i, nombre);
              i++;
          }

          if(localidad != 0)
          {
            st.setInt(i, localidad);
            i++;
          }

          if(especialidad != 0)
          {
            st.setInt(i, especialidad);
            i++;
          }

          ResultSet rs = st.executeQuery();

          while(rs.next())
          {
            Restaurantes restaurante = new Restaurantes();
            restaurante.setId(rs.getInt(1));
            restaurante.setNombre(rs.getString(2));
            restaurante.setDireccion(rs.getString(3));
            restaurante.setCorreoElectronico(rs.getString(4));
            restaurante.setLocalidad(rs.getString(5));
            restaurante.setTelefono(rs.getInt(6));
            restaurante.setEspecialidad(rs.getString(7));
            restaurante.setParaCeliacos(rs.getInt(8));
            restaurante.setFoto(rs.getString(9));
            restaurante.setMaps(rs.getString(10));
            listaRestaurantes.add(restaurante);
          }
      }
      return listaRestaurantes;
  }

  /**
   * Retorna un bool indicando si el plato introducido ya existe para ese restaurante o no
   * Útil para no introducir dos veces el mismo plato en el mismo restaurante
   */
  public boolean verificarExistenciaPlato(int restaurante, String nombrePlato) throws SQLException
  {
    String query = "SELECT * FROM Platos WHERE Platos.nombre= ? AND Platos.restaurante= ? ";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
      st.setString(1, nombrePlato);
      st.setInt(2, restaurante);

      ResultSet rs = st.executeQuery();

      if(rs.next())
      {
        return true;
      }else
      {
        return false;
      }
    }
  }

  /**
   * Retorna un entero indicando las filas modificadas al insertar un plato (1 en caso correcto o 0 en caso fallido)
   * Útil para introducir Platps en la BD
   */
  public int insertarPlato(int restaurante, String nombrePlato, float precio) throws SQLException
   {

    String query = "INSERT INTO Platos (nombre, restaurante, precio) VALUES  (?, ?, ?)";

    int rs;
    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setString (1, nombrePlato);
      st.setInt(2, restaurante);
      st.setFloat (3, precio);

      rs = st.executeUpdate();
    }

    return rs;
  }

  /**
   * Retorna el objeto Plato cuyo id se le pasa por parámetro
   * Útil para obtener detalles del Plato
   */
  public Platos getPlato(int id) throws SQLException
  {
    Platos plato = null;

    String query = "SELECT Platos.id, Platos.nombre, Restaurantes.nombre, Platos.precio "+
    " FROM Platos INNER JOIN Restaurantes ON Platos.restaurante = Restaurantes.id WHERE Platos.id = ?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, id);
      ResultSet rs = st.executeQuery();

      if(rs.next())
      {
        plato = new Platos();
        plato.setId(rs.getInt(1));
        plato.setNombre(rs.getString(2));
        plato.setRestaurante(rs.getString(3));
        plato.setPrecio(rs.getFloat(4));

      }
      return plato;
    }
  }

  /**
   * Retorna un entero indicando las filas modificadas
   * Útil para borrar platos de la BD (en realidad, ponerlos a NULL para ese restaurante)
   */
  public int borrarPlato (int id) throws SQLException
  {

    String query = "UPDATE Platos SET restaurante = NULL WHERE id=?";
    /*
    * Se hace modificando la columna restaurante porque hay que eliminar el plato sólo de la carta del restautante,
    * Si se eliminase el plato completo, los pedidos con ese plato quedarían con nombre de plato NULL (suponiendo que pudiera ser NULL, de
    * lo contrario no dejaría realizar la operación)
    */
    try (PreparedStatement  st = connection.prepareStatement(query))
    {
      // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, id);
      return st.executeUpdate();
    }

  }

  /**
   * Retorna un entero indicando las filas modificadas
   * Útil para modificar el precio de un plato
   */
  public int modificarPlato(float precioNuevo, int id) throws SQLException
  {

    String query = "UPDATE Platos SET precio=? WHERE id=?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setFloat(1, precioNuevo);
      st.setInt(2, id);
      return st.executeUpdate();
    }

  }

  /**
   * Retorna un entero indicando las filas modificadas
   * Útil para iinsertar un pedido en la BD
   */
  public int insertarPedido(String usuario, String restaurante, String localidad, String direccion, float precioTotal) throws SQLException
  {

    String query = "INSERT INTO Pedidos (fecha, usuario, restaurante, localidad, direccion, precioTotal, estado) VALUES (now(),(SELECT id FROM Usuarios WHERE nombre=?), " +
    " (SELECT id FROM Restaurantes WHERE nombre=?),(SELECT id FROM Localidades WHERE nombre=?),?,?, 'encargado')";

    int rs;
    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setString (1, usuario);
      st.setString(2, restaurante);
      st.setString (3, localidad);
      st.setString (4, direccion);
      st.setFloat (5, precioTotal);
      rs = st.executeUpdate();

    }
    return rs;
  }

  /**
   * Retorna un entero indicando el id del último pedido dado un usuario
   * Útil para saber en qué pedido meter los platos
   */
  public int getIdUltimoPedido(int usuario)  throws SQLException
  {
    int idUltimoPedido=0;

    String query = "SELECT Pedidos.id FROM Pedidos WHERE Pedidos.usuario = ? ORDER BY id DESC";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, usuario);
      ResultSet rs = st.executeQuery();

      if(rs.next())
      {
        idUltimoPedido = rs.getInt(1);
      }
      else
      {
        idUltimoPedido = 0;
      }
      return idUltimoPedido;
    }
  }

  /**
   * Retorna un entero indicando las filas modificadas
   * Útil para insertar un plato (dado por parámetro) en un pedido (dado por parámetro)
   */
  public int insertarPlatoEnPedido(int idPlato, int idPedido) throws SQLException
  {

    String query = "INSERT INTO PlatosEnPedido (platos, pedido) VALUES (?,?)";

    int rs;
    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setInt (1, idPlato);
      st.setInt(2, idPedido);
      rs = st.executeUpdate();

    }
    return rs;
  }

  /**
   * Retorna una lista de platos para un pedido dado por parámetro
   * Útil para obtener los platos de un pedido
   */
  public List<Platos> getPlatosPedido(int idPedido) throws SQLException
  {

    List <Platos> listaPlatos = new ArrayList<Platos>();
    String query = "SELECT Platos.id, Platos.nombre,Restaurantes.nombre,Platos.precio FROM Platos INNER JOIN Restaurantes ON Platos.restaurante=Restaurantes.id " + "INNER JOIN PlatosEnPedido ON Platos.id=PlatosEnPedido.platos INNER JOIN Pedidos ON PlatosEnPedido.pedido=Pedidos.id WHERE Pedidos.id=?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
        // Se  insertan  los  valores  en la  consulta:
        st.setInt(1, idPedido);
        ResultSet rs = st.executeQuery();

        while(rs.next())
        {
            Platos plato = new Platos();
            plato.setId(rs.getInt(1));
            plato.setNombre(rs.getString(2));
            plato.setRestaurante(rs.getString(3));
            plato.setPrecio(rs.getFloat(4));
            listaPlatos.add(plato);
        }
    }
    return listaPlatos;
  }

  /**
   * Retorna el objeto Pedidos cuyo id único se ha dado por parámetro
   * Útil para obtener los datos de un pedido
   */
  public Pedidos getPedido(int id) throws SQLException
  {
    Pedidos pedido = null;
    List<Platos> platos = getPlatosPedido(id);

    String query = "SELECT Pedidos.id, Pedidos.fecha, Usuarios.usuario, Restaurantes.nombre, Pedidos.precioTotal, Pedidos.estado, Pedidos.direccion, Localidades.nombre "
    + " FROM Pedidos INNER JOIN Usuarios ON Pedidos.usuario=Usuarios.id INNER JOIN Restaurantes ON Pedidos.restaurante=Restaurantes.id "
    + " INNER JOIN Localidades ON Localidades.id = Pedidos.localidad WHERE Pedidos.id=?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
      // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, id);
      ResultSet rs = st.executeQuery();

      if(rs.next())
      {
        pedido = new Pedidos();
        pedido.setId(rs.getInt(1));
        pedido.setFecha(rs.getString(2));
        pedido.setUsuario(rs.getString(3));
        pedido.setRestaurante(rs.getString(4));
        pedido.setPrecioTotal(rs.getFloat(5));
        pedido.setEstado(rs.getString(6));
        pedido.setDireccion(rs.getString(7));
        pedido.setLocalidad(rs.getString(8));
        pedido.setPlatos(platos);
      }
      return pedido;
    }
  }

  /**
   * Retorna la lista de Pedidos de un determinado restaurante
   * Útil para mostrar los Pedidos del restaurante a los gestores
   */
  public List<Pedidos> getPedidosRestaurante (int idRestaurante) throws SQLException
  {
    List<Pedidos> listaPedidosRestaurante = new ArrayList<Pedidos>();

    String query = "SELECT Pedidos.id, Pedidos.fecha,Usuarios.usuario, Restaurantes.nombre, Pedidos.precioTotal, Pedidos.estado FROM Pedidos INNER JOIN Usuarios ON Usuarios.id = Pedidos.usuario " +
    " INNER JOIN Restaurantes ON Pedidos.restaurante = Restaurantes.id WHERE Pedidos.restaurante = ? ORDER BY id DESC";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
      // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, idRestaurante);
      ResultSet rs = st.executeQuery();

      while(rs.next())
      {
        Pedidos pedido = new Pedidos();
        pedido.setId(rs.getInt(1));
        pedido.setFecha(rs.getString(2));
        pedido.setUsuario(rs.getString(3));
        pedido.setRestaurante(rs.getString(4));
        pedido.setPrecioTotal(rs.getFloat(5));
        pedido.setEstado(rs.getString("estado"));
        listaPedidosRestaurante.add(pedido);
      }
      return listaPedidosRestaurante;
    }
  }

  /**
   * Retorna un entero que indica el id del restaurante según su nombre
   * Útil para
   */
  public int getIdRestaurante (String nombreRestaurante) throws SQLException
  {
    List<Pedidos> listaPedidosRestaurante = new ArrayList<Pedidos>();

    String query = "SELECT Restaurantes.id  FROM Restaurantes WHERE Restaurantes.nombre = ?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
    // Se  insertan  los  valores  en la  consulta:
      st.setString(1, nombreRestaurante);
      ResultSet rs = st.executeQuery();

      int idRestaurante = 0;
      if(rs.next())
      {
        idRestaurante = rs.getInt(1);
      }
      return idRestaurante;
    }
  }

  /**
   * Retorna un entero indicando cuántas filas han sido modificadas
   * Útil para avanzar el estado de un pedido
   */
  public int avanzarEstadoPedido (int idPedido) throws SQLException
  {

    String query = "UPDATE Pedidos SET Pedidos.estado=Pedidos.estado+1 WHERE Pedidos.id = ?";

    try (PreparedStatement  st = connection.prepareStatement(query))
    {
      // Se  insertan  los  valores  en la  consulta:
      st.setInt(1, idPedido);

      int filasModificadas = st.executeUpdate();

      return filasModificadas;
    }
  }

}
