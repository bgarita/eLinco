-- Borrar facturas (FE)
SELECT * FROM detalle d
JOIN impuesto i ON i.detalle_id = d.id
WHERE d.clave IN (
  '50609092500310173305000300101010000160063199999999',
  '50604092500310190511500100002010000001350100041734',
  '50627082500310190511500100002010000001320100041733',
  '50618082500310173305000300101010000158245199999999',
  '50621082500310173305000300101010000158501199999999'
);
-- Borrar los impuestos.
START TRANSACTION;
DELETE i
FROM detalle d
JOIN impuesto i ON i.detalle_id = d.id
WHERE d.clave IN (
  '50609092500310173305000300101010000160063199999999',
  '50604092500310190511500100002010000001350100041734',
  '50627082500310190511500100002010000001320100041733',
  '50618082500310173305000300101010000158245199999999',
  '50621082500310173305000300101010000158501199999999'
);

-- Borrar el detalle
DELETE d
FROM detalle d 
WHERE d.clave IN(
	'50609092500310173305000300101010000160063199999999',
	'50604092500310190511500100002010000001350100041734',
	'50627082500310190511500100002010000001320100041733',
	'50618082500310173305000300101010000158245199999999',
	'50621082500310173305000300101010000158501199999999'
	);

-- Borrar el encabezado
DELETE e
FROM encabezado e 
WHERE e.clave IN(
	'50609092500310173305000300101010000160063199999999',
	'50604092500310190511500100002010000001350100041734',
	'50627082500310190511500100002010000001320100041733',
	'50618082500310173305000300101010000158245199999999',
	'50621082500310173305000300101010000158501199999999'
	);

-- COMMIT;
-- ROLLBACK;

SELECT * FROM detalle;



-- Select para el reporte
SELECT
	encabezado.numero_receptor,
	company.nombre_receptor,
	tipo_documento,
	fecha_emision,
	encabezado.numero_consecutivo AS comprobante,
	total_exento * tipo_cambio AS exento,
	total_gravado * tipo_cambio AS gravado,
	total_venta * tipo_cambio AS subtotal,
	total_descuentos * tipo_cambio AS descuento,
	total_venta_neta * tipo_cambio AS venta_neta,
	total_impuesto * tipo_cambio AS impuesto,
	total_comprobante * tipo_cambio AS total_comprobante,
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '01' AND tarifa = 1),0)) * tipo_cambio AS 'IVA1',
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '01' AND tarifa = 13),0)) * tipo_cambio AS 'IVA13',
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '01' AND tarifa NOT IN(1,13)),0)) * tipo_cambio AS 'IVA_',
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '02'),0)) * tipo_cambio AS ISC,
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '03'),0)) * tipo_cambio AS IUC,
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto = '99'),0)) * tipo_cambio AS Otros,
	sum(IFNULL((SELECT SUM(monto) from impuesto WHERE detalle_id = detalle.id AND codigo_impuesto NOT IN('01', '02', '03', '99')),0)) * tipo_cambio AS Otros2	
FROM detalle 
INNER JOIN encabezado ON detalle.clave = encabezado.clave
INNER JOIN company ON encabezado.numero_receptor = company.numero_receptor
WHERE year(encabezado.fecha_emision) >=  2022
AND month(encabezado.fecha_emision) >=  1
-- AND If($P{p_receptor} = 'todos', encabezado.numero_receptor = encabezado.numero_receptor, encabezado.numero_receptor = $P{p_receptor})
-- AND If($P{p_emisor} = 'todos', encabezado.numero_emisor = encabezado.numero_emisor, encabezado.numero_emisor = $P{p_emisor})
-- AND If($P{p_tipoDoc} = 'todos', encabezado.tipo_documento = encabezado.tipo_documento, encabezado.tipo_documento = $P{p_tipoDoc})
-- AND If($P{p_nombreCom} = 'Todos', encabezado.nombre_comercial_receptor = encabezado.nombre_comercial_receptor, encabezado.nombre_comercial_receptor = $P{p_nombreCom})
GROUP BY detalle.clave
ORDER BY nombre_receptor, fecha_emision;

SELECT * FROM encabezado;