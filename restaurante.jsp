<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='proyectoFinal.*' %>

<% Usuarios usuario = (Usuarios)request.getAttribute("usuario");%>
<% Restaurantes restaurante = (Restaurantes)request.getAttribute("restaurante");%>
<% List<Platos> carta = (List<Platos>)request.getAttribute("carta");%>

<html>
  <head>
    <title>CallEat - Restaurante <%= restaurante.getNombre() %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog' class='col-sm-12'>


      <div class='col-sm-12'>

      <img src='img/logo_apaisado.jpg' alt='CallEat_logo' width="25%">

        <h3>Bienvenid@, <%=usuario.getNombre()%></h3>
        <%if(usuario.getGestorDe()==restaurante.getId()){%>
          Eres el gestor de este restaurante
        <%}%>

        <br>

        <div class="jumbotron">
          <h1>RESTAURANTE <%= restaurante.getNombre() %></h1>
          <p>Restaurante <%=restaurante.getEspecialidad()%> situado en <%=restaurante.getLocalidad()%></p>
        </div>

        <div class='col-sm-8'>
          <img src="<%=restaurante.getFoto()%>" class="img" alt="maria" width="600" height="300">
        </div>

        <div class='col-sm-4 right'>
          <h4><span class="glyphicon glyphicon-road"></span><strong> Dirección: </strong><%=restaurante.getDireccion()%></h4>
          <h4><span class="glyphicon glyphicon-envelope"></span><strong> Correo Electrónico: </strong><a href='mailto:<%=restaurante.getCorreoElectronico()%>'><%=restaurante.getCorreoElectronico()%></a></h4>
          <h4><span class="glyphicon glyphicon-home"></span><strong> Localidad: </strong><%=restaurante.getLocalidad()%></h4>
          <h4><span class="glyphicon glyphicon-phone-alt"></span><strong> Teléfono: </strong><%=restaurante.getTelefono()%></h4>
          <h4><span class="glyphicon glyphicon-cutlery"></span><strong> Especialidad: </strong><%=restaurante.getEspecialidad()%></h4>
          <% if(restaurante.getParaCeliacos() == 1) {%>
            <h4><strong>Este restaurante incluye en su carta platos para celiacos</strong></h4>
          <%}%>
        </div>

      </div>


      <div class='col-sm-12'>

      <h2>CARTA</h2>

        <form action='addToCart'>
          <% for (Platos plato: carta) {%>
            <div class="checkbox">
              <%if(usuario.getGestorDe()!=restaurante.getId()){%>
                <label><input type='checkbox' name='platosPedido' value='<%=plato.getId()%>'> <%=plato.getNombre()%> - <%=plato.getPrecio()%> euros</label><br>
              <%}else{%>
                <label> <%=plato.getNombre()%> - <%=plato.getPrecio()%> euros</label><br>
              <%}%>
            </div>
          <%}%>
          <input type='hidden' name='idRestaurante' value='<%=restaurante.getId()%>'>

          </br>

          <%if(usuario.getGestorDe()!=restaurante.getId()){%>
              <br>
              <input type='submit' value = 'Hacer pedido' >
          <%}%>
        </form>

      </div>

      <%if(usuario.getGestorDe()==restaurante.getId()){%>

        <form action='viewModificarCarta'>
          <input type='hidden' name='id' value='<%=restaurante.getId()%>'>
          <input type='submit' value = 'Modificar Carta'>
        </form>

      <%}%>

      <%if(usuario.getGestorDe()==restaurante.getId())
      {%>
        <hr>
        <h2>Pedidos para el restaurante <%=restaurante.getNombre()%></h2>
        <% List<Pedidos> listaPedidosRestaurante = (List<Pedidos>)request.getAttribute("listaPedidosRestaurante"); %>

        <div class="container">
          <ul class="list-group">


          <% for (Pedidos pedido: listaPedidosRestaurante) {%>
            <%if(pedido.getEstado().equals("entregado")){%>
              <li class="list-group-item"><a href='viewPedido?id=<%=pedido.getId()%>'><%=pedido.getFecha()%> - <%=pedido.getPrecioTotal()%> € - <%=pedido.getEstado()%></a></li>
            <%}else{%>
              <li class="list-group-item"><strong><a href='viewPedido?id=<%=pedido.getId()%>'><%=pedido.getFecha()%> - <%=pedido.getPrecioTotal()%> € - <%=pedido.getEstado()%></a></strong></li>
            <%}%>
          <%}%>

          <%if(listaPedidosRestaurante.size()==0){%>
            <p>-- No existen pedidos por el momento --</p>
          <%}%>


          </ul>
        </div>
      <%}%>

      <hr>

      <br>

      <div class='col-sm-12'>
        <iframe src='<%=restaurante.getMaps()%>' width="1200" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
      </div>

      <br>

      <form action='viewCliente'>
        <input type='submit' value = 'Volver a la pantalla principal'>
      </form>

      <form action='cerrarSesion'>
        <input type='submit' value = 'Cerrar Sesión'>
      </form>
    </div>
  </body>

</html>
