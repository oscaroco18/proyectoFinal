<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='java.util.ArrayList'%>
<%@ page import='java.util.Date'%>
<%@ page import='proyectoFinal.*' %>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Resultados de tu búsqueda</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog'>

      <img src='img/logo_apaisado.jpg' alt='CallEat_logo' width="25%">

      <% List<Restaurantes> listaRestaurantesBusqueda = (List<Restaurantes>)request.getAttribute("listaRestaurantesBusqueda"); %>

      <hr>

      <h2>Lista de restaurantes que coinciden con tus criterios de búsqueda:</h2>

      <div class="container">
        <ul class="list-group">
          <% for (Restaurantes restaurante: listaRestaurantesBusqueda) {%>
            <li class="list-group-item"><a href='viewRestaurante?id=<%=restaurante.getId()%>'> <%=restaurante.getNombre()%> - <%=restaurante.getDireccion()%> </a></li>
          <%}%>
        </ul>
      </div>

      <%if(listaRestaurantesBusqueda.size()==0){%>
        <p>- No existen resultados -</p>
      <%}%>

      <form action='viewCliente'>
        <input type='submit' value = 'Volver a la pantalla principal'>
      </form>

      <form action='cerrarSesion'>
        <input type='submit' value = 'Cerrar Sesión'>
      </form>
    </div>
  </body>

</html>
