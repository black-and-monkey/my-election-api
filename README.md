#REQ

Mockup 
https://app.moqups.com/pud88lakxZQ1Y0sRQHOKASMYZeoL0Tol/edit/page/ae8fe8eb0

## Perfil Admin

### CRUD CRV
* Número
* Departamento
* Localidad
* Abrir CRV, marcar que la CRV puede comenzar a recibir votos
* Cierre CRV, marcar que la CRV ya no puede recibir votos
* Notas

Preveamos alta masiva con inserts o upload de txt

### CRUD Usuarios
* mail
* pass
* CRV
* Habilitado
Preveamos alta masiva con inserts o upload de txt

### Admin votos registrados
* Marcar cómo borrado algún voto


### Consultas
* Detalle de votos registrados
* Detalle de cantidad de votos recibidos por CRV

  * Hora, Departamento, Localidad, CRV, CI
  * Departamento, Localidad, Cantidad
  
Detalle de CRVs
  * Fecha Hora Apertura
  * Fecha Hora Cierre
  * Número
  * Departamento
  * Localidad
  * Cant Votos
  * Notas


## Perfil de CRV (Comisión Receptora de Votos)

* Login con user y pass
* Botón de apertura de mesa / después de abierta se transforma en cierre de mesa
* Registro de voto

  * Fecha Hora (autom)
  * Marca de user que lo hizo (autom)
  * Del user toma la CRV (autom)
  * Nro. Cédula (válida dígito verificador)
  * Fecha Nacimiento (válida que la fecha sea >=6/11/1991  y <=5/11/2008)
  * Nombre Completo

* Consulta de lista ordinal

  * Detalle ordenado por fecha y hora con el listado de quienes votaron en la CRV (Numerador autoincremental, Hora, CI, Nombre Completo)

* Ingresar nota
* Cierre de mesa, al cerrar la mesa permite dejar unas notas adicionales de cierre

