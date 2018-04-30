<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='java.util.ArrayList'%>
<%@ page import='java.util.Date'%>
<%@ page import='proyectoFinal.*' %>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Página principal</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog'>

      <img src='img/logo_apaisado.jpg' alt='CallEat_logo' width="25%">

      <%Usuarios usuario = (Usuarios)request.getAttribute("usuario");%>

      <p color='white'>Bienvenido <%=usuario.getNombre()%>

      <%DBManager manager = new DBManager();%>

      <%if(usuario.getGestorDe()!=0){%>
        <%Restaurantes restaurante = manager.getRestaurante(usuario.getGestorDe());%>
        , gestor del restaurante <a href='viewRestaurante?id=<%=restaurante.getId()%>'><%=restaurante.getNombre()%></a>
      <%}%>

      </p>

      <hr>

      <h3>Restaurantes sugeridos en <%=usuario.getLocalidad()%></h3>

      <% List<Restaurantes> listaRestaurantesCercanos = (List<Restaurantes>)request.getAttribute("listaRestaurantesCercanos"); %>

      <% List<Localidades> listaLocalidades = (List<Localidades>)request.getAttribute("listaLocalidades");%>

      <% List<Especialidades> listaEspecialidades = (List<Especialidades>)request.getAttribute("listaEspecialidades");%>

      <% for (Restaurantes restaurante: listaRestaurantesCercanos) {%>
        <a href='viewRestaurante?id=<%=restaurante.getId()%>' class="btn btn-default" role="button"><%=restaurante.getNombre()%></a>
      <%}%>

      <hr>

      <h2>Buscador de restaurantes  <span class="glyphicon glyphicon-search"></span></h2>
      <br>
      <form action='viewBusqueda'>
        <label>
          Nombre: <input type='text' maxlength="30" name='nombreRestaurante'>
        </label>
        <label>
          Localidad:
          <select name='localidadRestaurante' >
            Localidad:
            <option value='0'>Cualquiera</option>
            <% for (Localidades localidad: listaLocalidades) {%>
              <p><option value='<%=localidad.getId()%>'><%=localidad.getNombre()%></option></p>
            <%}%>
          </select>
        </label>

        <label>
          Especialidad:
          <select name='especialidadRestaurante'>

            <option value='0'>Cualquiera</option>
            <% for (Especialidades especialidad: listaEspecialidades) {%>
            <p><option value='<%=especialidad.getId()%>'><%=especialidad.getNombre()%></option></p>
            <%}%>
          </select>
        </label>

        </br></br>

        <label>
          <input type='checkbox' name='paraCeliacos' value='1'> Buscar sólo restaurantes con platos para celiacos
        </label>

        </br></br>

        <input type='submit' value='Buscar restaurantes'>
      </form>

      <hr>

      <h2>Mis pedidos</h2>
      <% List<Pedidos> listaPedidos = (List<Pedidos>)request.getAttribute("listaPedidos"); %>

      <div class="container">
        <ul class="list-group">
          <% for (Pedidos pedido: listaPedidos) {%>
          <li class="list-group-item"><a href='viewPedido?id=<%=pedido.getId()%>'><%=pedido.getFecha()%> - RESTAURANTE <%=pedido.getRestaurante()%> - <%=pedido.getPrecioTotal()%> € - <%=pedido.getEstado()%></a></li>
          <%}%>
        </ul>
      </div>

      <form action='cerrarSesion'>
        <input type='submit' value = 'Cerrar Sesión'>
      </form>
    </div>
  </body>

</html>
