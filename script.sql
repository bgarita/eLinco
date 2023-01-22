

SELECT
	encabezado.numero_receptor,
	company.nombre_receptor,
	tipo_documento,
	fecha_emision,
	comprobante,
	total_exento * tipo_cambio AS exento,
	total_gravado * tipo_cambio AS gravado,
	total_venta * tipo_cambio AS subtotal,
	total_descuentos * tipo_cambio AS descuento,
	total_venta_neta * tipo_cambio AS venta_neta,
	total_impuesto * tipo_cambio AS impuesto,
	total_comprobante * tipo_cambio AS total_comprobante,
	IFNULL((SELECT SUM(montoIVA * encabezado.tipo_cambio) FROM detalle 
	WHERE clave = encabezado.clave AND codigo_tarifa = '02'),0) AS 'IVA 1%',
	IFNULL((SELECT SUM(montoIVA * encabezado.tipo_cambio) FROM detalle 
	WHERE clave = encabezado.clave AND codigo_tarifa = '03'),0) AS 'IVA 2%',
	IFNULL((SELECT SUM(montoIVA * encabezado.tipo_cambio) FROM detalle 
	WHERE clave = encabezado.clave AND codigo_tarifa IN('04', '06') ),0) AS 'IVA 4%',
	IFNULL((SELECT SUM(montoIVA * encabezado.tipo_cambio) FROM detalle 
	WHERE clave = encabezado.clave AND codigo_tarifa = '07'),0) AS 'IVA 8%',
	IFNULL((SELECT SUM(montoIVA * encabezado.tipo_cambio) FROM detalle 
	WHERE clave = encabezado.clave AND codigo_tarifa = '08'),0) AS 'IVA 13%'
FROM encabezado
INNER JOIN company ON encabezado.numero_receptor = company.numero_receptor
WHERE  year(encabezado.fecha_emision) =  2022 
AND month(encabezado.fecha_emision) =  11 
AND If('302880569' = 'todos', encabezado.numero_receptor = encabezado.numero_receptor, encabezado.numero_receptor = '302880569')
ORDER BY nombre_receptor, fecha_emision;



SELECT
	encabezado.numero_receptor,
	company.nombre_receptor,
	tipo_documento,
	fecha_emision,
	comprobante,
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
WHERE year(encabezado.fecha_emision) = 2022
AND MONTH(encabezado.fecha_emision) = 08
GROUP BY detalle.clave;