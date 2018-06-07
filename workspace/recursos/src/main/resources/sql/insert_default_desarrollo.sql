/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 7/06/2018
 */

/**
* Usuario root creado unicamente para el modo desarrollo para hacer pruebas
*/
INSERT INTO USUARIO(NICK,CLAVE,ESTADO)
     VALUES ('root', '1234','A');

/**
* Seteando al usuario para pruebas un perfil
*/
INSERT INTO PERFIL_USUARIO(NICK,PERFIL_ID,FECHA_CREACION)
    VALUES('root',1,CURRENT_DATE);