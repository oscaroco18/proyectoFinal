<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='java.util.ArrayList'%>
<%@ page import='java.util.Date'%>
<%@ page import='proyectoFinal.*' %>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Confirmar Pedido</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog'>

      <img src='img/logo_apaisado.jpg' alt='CallEat_logo' width="25%">

      <hr>

      <%Usuarios usuario = (Usuarios)request.getAttribute("usuario");%>

      <%Pedidos pedido = (Pedidos)request.getAttribute("pedido");%>

      <h1>Estos son los detalles de tu pedido, <%=usuario.getNombre()%></h1>

      <h3>Restaurante: <%=pedido.getRestaurante()%></h3>

      <div class="table-responsive col-sm-12">
        <table id='tablaPedidos' class="table">
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

      <h4>Por favor, indica la dirección a la que quieres que llegue tu pedido</h4>
      </br>

      <form action='confirmarPedido'>
        <label>
            Ciudad: <%=pedido.getLocalidad()%> </br>
            Direccion: <input type = 'text' maxlength="30" name= 'direccionPedido'>
        </label>
        </br></br>
        <input type='submit' value = 'Confirmar'>
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
