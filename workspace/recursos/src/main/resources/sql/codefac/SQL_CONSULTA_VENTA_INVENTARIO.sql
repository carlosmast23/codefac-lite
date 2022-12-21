/*Query que me permite obtener los productos que no tienen inventario y adicional el resto con kardex*/
SELECT DISTINCT P.ID_PRODUCTO,K.ID AS ID_KARDEX FROM PRODUCTO P LEFT JOIN KARDEX K ON K.PRODUCTO_ID = P.ID_PRODUCTO LEFT JOIN LOTE L ON L.PRODUCTO_ID =P.ID_PRODUCTO WHERE K.ID IS NULL OR K.ID IN 
(
	/*Query que me permite obtener todos los productos disponibles*/
	SELECT k.PRODUCTO_ID FROM PRODUCTO P LEFT JOIN KARDEX K ON K.PRODUCTO_ID = P.ID_PRODUCTO LEFT JOIN LOTE L ON L.PRODUCTO_ID =P.ID_PRODUCTO WHERE K.ESTADO ='A' AND P.ESTADO ='A' AND L.ESTADO ='A' AND STOCK > 0
)
OR K.ID IN
(
	/*Todo este segmento de query me permite obtener un unico registro cuando no tiene stock para poder mostrar*/
	SELECT P.ID_KARDEX FROM 
	(
		/*Ese query me permite obtener un unico dato todos los lotes que no tienen inventario*/
		SELECT DISTINCT P.ID_PRODUCTO ,MAX(K.ID) AS ID_KARDEX FROM PRODUCTO P LEFT JOIN KARDEX K ON K.PRODUCTO_ID = P.ID_PRODUCTO LEFT JOIN LOTE L ON L.ID =K.LOTE_ID WHERE K.ESTADO ='A' AND P.ESTADO ='A' AND L.ESTADO ='A' AND K.STOCK <= 0 GROUP BY P.ID_PRODUCTO
	) P 
	WHERE 
	P.ID_PRODUCTO NOT IN 
		(
			/* Este query me permite obtener todos los productos con kardex que tienen stock positivo*/
			SELECT P.ID_PRODUCTO FROM PRODUCTO P LEFT JOIN KARDEX K ON K.PRODUCTO_ID = P.ID_PRODUCTO LEFT JOIN LOTE L ON L.PRODUCTO_ID =P.ID_PRODUCTO WHERE K.ESTADO ='A' AND P.ESTADO ='A' AND L.ESTADO ='A' AND STOCK > 0		
		)
) ORDER BY K.ID DESC