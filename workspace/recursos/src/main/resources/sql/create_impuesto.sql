/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 14-nov-2017
 */
create table IMPUESTO 
(
    ID_IMPUESTO integer not null GENERATED ALWAYS AS IDENTITY( START WITH 1, INCREMENT BY 1),
    IVA VARCHAR(1) DEFAULT '0',
    ICE VARCHAR(4) DEFAULT '4',
    IRBPNR  VARCHAR(1) DEFAULT '0',
    primary key(ID_IMPUESTO)
)
