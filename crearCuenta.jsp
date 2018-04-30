<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page import='java.util.List' %>
<%@ page import='proyectoFinal.*' %>

<% List<Localidades> listaLocalidades = (List<Localidades>)request.getAttribute("listaLocalidades");%>

<html>
  <head>
    <meta charset="UTF-8">
    <title>CallEat - Crear cuenta</title>
    <link rel="stylesheet" href="estilosUnlog.css" type="text/css">
  </head>

  <body>
    <div id='recuadro'>

      <h1>¡¡BIENVENID@!!<h1>

      <h3>Por favor, introduce tus datos</h3>
      <form action = 'crearCuenta' id='formularioRegistro'>

        <label>
          Usuario: <input type = 'text' name= 'usuario' maxlength="20" required>
        </label>

        </br>

        <label>
          Contraseña: <input type = 'password' name= 'contrasena' maxlength="20" required>
        </label>

        </br>

        <label>
          Nombre: <input type = 'text' name= 'nombre' maxlength="20" required>
        </label>

        </br>

        <label>
          Apellidos: <input type = 'text' name= 'apellidos' maxlength="30" required>
        </label>

        </br>

        <label>
          Correo Electrónico: <input type = 'email' name= 'correoElectronico' maxlength="30" required>
        </label>

        </br>

        <label>
          Telefono: <input type = 'number' name= 'telefono' maxlength="9" min='600000000' max='999999999' required>
        </label>

        </br>

        <label>
          Direccion: <input type = 'text' name= 'direccion' maxlength="30" required>
        </label>

        </br>

        <label for='localidad'>
          Localidad:
          <select name='localidad'>
            <% for (Localidades localidad: listaLocalidades) {%>
            <p><option value='<%=localidad.getNombre()%>'><%=localidad.getNombre()%></option></p>
            <%}%>
          </select>
        </label>

        </br></br>

        <input type='submit' value='Registrarse'>
      </form>

      <form action = 'index.html'>
        <input type='submit' value='Volver'>
      </form>
    </div>
  </body>

</html>
