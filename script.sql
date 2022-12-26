

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
ORDER BY nombre_receptor, fecha_emision;
