<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='java.util.ArrayList'%>
<%@ page import='java.util.Date'%>
<%@ page import='proyectoFinal.*' %>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Pedido</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog'>

      <%Pedidos pedido = (Pedidos)request.getAttribute("pedido"); %>
      <%Usuarios usuario = (Usuarios)request.getAttribute("usuario");%>
      <%String idRestaurante = (String)request.getAttribute("idRestaurante");%>

      <%if(usuario.getGestorDe()==Integer.parseInt(idRestaurante)){%>
        <h3>Estos son los detalles del pedido del usuario <%=pedido.getUsuario()%></h3>
      <%}else{%>
        <h3>Estos son los detalles de tu pedido, <%=usuario.getNombre()%></h3>
      <%}%>

      <p> Dirección: <%=pedido.getDireccion()%> </p>
      <p> Localidad: <%=pedido.getLocalidad()%> </p>
      <p> Restaurante: <%=pedido.getRestaurante()%> </p>
      <div class="table-responsive">
      <table class="table">
          <thead>
              <tr>
                  <th>PLATO</th>
                  <th>PRECIO</th>
              </tr>
          </thead>
          <tbody>
              <% for (Platos plato: pedido.getPlatos()) {%>
                  <tr>
                      <td><%=plato.getNombre()%></td>
                      <td><%=plato.getPrecio()%></td>
                  </tr>
              <%}%>
          </tbody>
          <tfoot>
              <tr>
                  <td>TOTAL: <%=pedido.getPrecioTotal()%></td>
                  <td></td>
              </tr>

          </tfoot>
      </table>
    </div>
      <p><strong>Estado: <%=pedido.getEstado()%></strong></p>

      <%if(usuario.getGestorDe()==Integer.parseInt(idRestaurante) && (!pedido.getEstado().equals("entregado"))){%>
        <form action=modificarPedido>
          <input type='hidden' name='id' value=<%=pedido.getId()%>>
          <input type='submit' value = 'Avanzar estado del pedido'>
        </form>
      <%}%>

      <form action=viewRestaurante>
        <input type='hidden' name='id' value=<%=idRestaurante%>>
        <input type='submit' value = 'Volver a la pantalla del restaurante'>
        </form>

      <form action='viewCliente'>
        <input type='submit' value = 'Volver a la pantalla principal'>
      </form>


      <form action='cerrarSesion'>
        <input type='submit' value = 'Cerrar Sesión'>
      </form>

    </div>
  </body>

</html>
