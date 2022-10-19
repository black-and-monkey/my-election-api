CREATE OR REPLACE VIEW votos_por_departamento
AS SELECT crv.departamento,
    count(voto.id) AS cantidadvotos
   FROM my_election.crv crv
     LEFT JOIN my_election.vote_registration voto ON voto.crv_id::text = crv.id::text
  GROUP BY crv.departamento
  ORDER BY crv.departamento;


CREATE OR REPLACE VIEW votos_por_departamento_localidad
AS SELECT crv.departamento,
          crv.localidad,
    count(voto.id) AS cantidadvotos
   FROM my_election.crv crv
     LEFT JOIN my_election.vote_registration voto ON voto.crv_id::text = crv.id::text
  GROUP BY crv.departamento, crv.localidad
  ORDER BY crv.departamento, crv.localidad;


CREATE OR REPLACE VIEW detalle_votos as
select 
voto.*,
crv.departamento,
crv.localidad
from 
my_election.crv crv
join my_election.vote_registration voto on voto.crv_id = crv.id 
