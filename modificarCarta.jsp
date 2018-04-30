<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='proyectoFinal.*' %>

<% Usuarios usuario = (Usuarios)request.getAttribute("usuario");%>
<% Restaurantes restaurante = (Restaurantes)request.getAttribute("restaurante");%>
<% List<Platos> carta = (List<Platos>)request.getAttribute("carta");%>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Restaurante <%= restaurante.getNombre() %></title>
    <link rel="stylesheet" href="estilosLog.css" type="text/css">
  </head>

  <body>

    <div id='recuadroLog'>
      <%if(usuario.getGestorDe()==restaurante.getId()){%>

        <h3>Bienvenido, <%=usuario.getNombre()%></h3>
        <%if(usuario.getGestorDe()==restaurante.getId()){%>
          Eres el gestor de este restaurante
        <%}%>
        <h1>RESTAURANTE <%= restaurante.getNombre() %></h1>
        <p>Dirección: <%=restaurante.getDireccion()%><p>
        <p>Correo Electrónico: <a href='mailto:<%=restaurante.getCorreoElectronico()%>'><%=restaurante.getCorreoElectronico()%></a><p>
        <p>Localidad: <%=restaurante.getLocalidad()%><p>
        <p>Teléfono: <%=restaurante.getTelefono()%><p>
        <p>Especialidad: <%=restaurante.getEspecialidad()%><p>
        <% if(restaurante.getParaCeliacos() == 1) {%>
          <p>Este restaurante incluye en su carta platos para celiacos<p>
        <%}%>

        <h2>CARTA</h2>

        <h4>Escribe el nuevo precio junto al plato o selecciona aquellos que quieras borrar de la carta</h4>

        <%int i=0;%>
        <form action='gestionCarta'>
          <label for='pedido'>
          <% for (Platos plato: carta) {%>

              <input type='checkbox' name='platosABorrarModificar' value='<%=plato.getId()%>'> <%=plato.getNombre()%> - <%=plato.getPrecio()%> euros <input type='number' step = '0.01' name='<%=plato.getId()%>' max='100'></br>

          <%}%>
          </label>
          <input type='hidden' name='idRestaurante' value='<%=restaurante.getId()%>'>

          </br>
          <input type='submit' name='modificarPrecio' value='Modificar Precio seleccionados'>
          <input type='submit' name='borrarSeleccionados' value ='Borrar seleccionados'>

        </form>

        <form action='viewRestaurante'>
          <input type='hidden' name='id' value='<%=restaurante.getId()%>'>
          <input type='submit' value = 'Volver al restaurante'>
        </form>


        <h4>¿Deseas añadir algún plato a la carta?</h4>

        <form action='gestionCarta'>
          <input type='hidden' name='idRestaurante' value='<%=restaurante.getId()%>'>
          Nombre del plato: <input type = 'text' name='nombrePlatoAnadir' maxlength="20" required>
          Precio: <input type = 'number' step = '0.01' name='precioPlatoAnadir' max="100" required>
          <input type='submit' name='addPlato' value = 'Añadir plato'>
        </form>

        <form action='viewCliente'>
          <input type='submit' value = 'Volver a la pantalla principal'>
        </form>

        <form action='cerrarSesion'>
          <input type='submit' value = 'Cerrar Sesión'>
        </form>
      <%}%>
    </div>
  </body>

</html>
