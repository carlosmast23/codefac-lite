INSERT INTO PRODUCTO(NOMBRE,VALOR_UNITARIO,ESTADO) 
VALUES ('Matricula',85,'A'),
('Pension',100,'A'),
('Certificado',50,'A'),
('Excursion',50,'A')
;


INSERT INTO NIVEL(NOMBRE,DESCRIPCION,ORDEN,ESTADO) 
VALUES ('Segundo grado','primer grado',5,'A'),
('Primer grado','primer grado',4,'A'),
('Basica','basica',3,'A'),
('Inicia1 2','Inicial 2',2,'A'),
('Inicia1 1','Inicial 1',1,'A')
;

INSERT INTO PERIODO (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO) 
VALUES 
('2017 enero - 2018 junio', '2017-01-01', '2017-06-30', 'A'),
('2017 julio - 2018 diciembre', '2017-07-01', '2017-12-24', 'A'),
('2018 enero - 2018 junio', '2018-01-01', '2018-06-30', 'A'),
('2018 julio - 2018 diciembre', '2018-07-01', '2018-12-24', 'A')
;

INSERT INTO AULA (NOMBRE, UBICACION, ESTADO) 
VALUES ('Aula 1', 'Primer piso', 'A'),
('Aula 2', 'Primer piso', 'A'),
('Aula 3', 'Segundo piso', 'A')
;

INSERT INTO CLIENTE (NOMBRES, APELLIDOS, TIPO_OPERADOR, RAZON_SOCIAL, NOMBRE_LEGAL, TIPO_IDENTIFICACION, IDENTIFICACION, TIPO_CLIENTE, DIRECCION, TELEFONO_CONVENCIONAL, EXTENSION_TELEFONO, TELEFONO_CELULAR, CORREO_ELECTRONICO, ACTIVIDAD_COMERCIAL, ESTADO) 
VALUES 
('Carlos', 'Perez', 'C', 'Carlos Perez', NULL, '04', '1719468108001', 'CLIENTE', 'Sangolqui', '022087044', '0', '0983528439', 'jcp_computer@hotmail.com', NULL, 'A'),
('Juan', 'Loya', 'C', 'JUAN Loya', NULL, '04', '1724218951001', 'CLIENTE', 'Conocoto', '022087065', '0', '0983528439', 'juancarlos100pl@hotmail.com', NULL, 'A')
;


INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Afganistán','AFGANA','AFG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Albania','ALBANESA','ALB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Alemania','ALEMANA','DEU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Andorra','ANDORRANA','AND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Angola','ANGOLEÑA','AGO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('AntiguayBarbuda','ANTIGUANA','ATG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ArabiaSaudita','SAUDÍ','SAU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Argelia','ARGELINA','DZA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Argentina','ARGENTINA','ARG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Armenia','ARMENIA','ARM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Aruba','ARUBEÑA','ABW');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Australia','AUSTRALIANA','AUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Austria','AUSTRIACA','AUT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Azerbaiyán','AZERBAIYANA','AZE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bahamas','BAHAMEÑA','BHS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bangladés','BANGLADESÍ','BGD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Barbados','BARBADENSE','BRB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Baréin','BAREINÍ','BHR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bélgica','BELGA','BEL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Belice','BELICEÑA','BLZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Benín','BENINÉSA','BEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bielorrusia','BIELORRUSA','BLR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Birmania','BIRMANA','MMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bolivia','BOLIVIANA','BOL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('BosniayHerzegovina','BOSNIA','BIH');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Botsuana','BOTSUANA','BWA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Brasil','BRASILEÑA','BRA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Brunéi','BRUNEANA','BRN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bulgaria','BÚLGARA','BGR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('BurkinaFaso','BURKINÉS','BFA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Burundi','BURUNDÉSA','BDI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bután','BUTANÉSA','BTN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CaboVerde','CABOVERDIANA','CPV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Camboya','CAMBOYANA','KHM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Camerún','CAMERUNESA','CMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Canadá','CANADIENSE','CAN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Catar','CATARÍ','QAT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chad','CHADIANA','TCD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chile','CHILENA','CHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('China','CHINA','CHN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chipre','CHIPRIOTA','CYP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CiudaddelVaticano','VATICANA','VAT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Colombia','COLOMBIANA','COL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Comoras','COMORENSE','COM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CoreadelNorte','NORCOREANA','PRK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CoreadelSur','SURCOREANA','KOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CostadeMarfil','MARFILEÑA','CIV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CostaRica','COSTARRICENSE','CRI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Croacia','CROATA','HRV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Cuba','CUBANA','CUB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Dinamarca','DANÉSA','DNK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Dominica','DOMINIQUÉS','DMA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ecuador','ECUATORIANA','ECU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Egipto','EGIPCIA','EGY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ElSalvador','SALVADOREÑA','SLV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('EmiratosÁrabesUnidos','EMIRATÍ','ARE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eritrea','ERITREA','ERI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eslovaquia','ESLOVACA','SVK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eslovenia','ESLOVENA','SVN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('España','ESPAÑOLA','ESP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('EstadosUnidos','ESTADOUNIDENSE','USA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Estonia','ESTONIA','EST');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Etiopía','ETÍOPE','ETH');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Filipinas','FILIPINA','PHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Finlandia','FINLANDÉSA','FIN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Fiyi','FIYIANA','FJI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Francia','FRANCÉSA','FRA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gabón','GABONÉSA','GAB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gambia','GAMBIANA','GMB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Georgia','GEORGIANA','GEO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gibraltar','GIBRALTAREÑA','GIB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ghana','GHANÉSA','GHA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Granada','GRANADINA','GRD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Grecia','GRIEGA','GRC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Groenlandia','GROENLANDÉSA','GRL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guatemala','GUATEMALTECA','GTM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guineaecuatorial','ECUATOGUINEANA','GNQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guinea','GUINEANA','GIN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guinea-Bisáu','GUINEANA','GNB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guyana','GUYANESA','GUY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Haití','HAITIANA','HTI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Honduras','HONDUREÑA','HND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Hungría','HÚNGARA','HUN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('India','HINDÚ','IND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Indonesia','INDONESIA','IDN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irak','IRAQUÍ','IRQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irán','IRANÍ','IRN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irlanda','IRLANDÉSA','IRL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Islandia','ISLANDÉSA','ISL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasCook','COOKIANA','COK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasMarshall','MARSHALÉSA','MHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasSalomón','SALOMONENSE','SLB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Israel','ISRAELÍ','ISR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Italia','ITALIANA','ITA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Jamaica','JAMAIQUINA','JAM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Japón','JAPONÉSA','JPN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Jordania','JORDANA','JOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kazajistán','KAZAJA','KAZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kenia','KENIATA','KEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kirguistán','KIRGUISA','KGZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kiribati','KIRIBATIANA','KIR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kuwait','KUWAITÍ','KWT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Laos','LAOSIANA','LAO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Lesoto','LESOTENSE','LSO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Letonia','LETÓNA','LVA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Líbano','LIBANÉSA','LBN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Liberia','LIBERIANA','LBR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Libia','LIBIA','LBY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Liechtenstein','LIECHTENSTEINIANA','LIE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Lituania','LITUANA','LTU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Luxemburgo','LUXEMBURGUÉSA','LUX');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Madagascar','MALGACHE','MDG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malasia','MALASIA','MYS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malaui','MALAUÍ','MWI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Maldivas','MALDIVA','MDV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malí','MALIENSE','MLI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malta','MALTÉSA','MLT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Marruecos','MARROQUÍ','MAR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Martinica','MARTINIQUÉS','MTQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mauricio','MAURICIANA','MUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mauritania','MAURITANA','MRT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('México','MEXICANA','MEX');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Micronesia','MICRONESIA','FSM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Moldavia','MOLDAVA','MDA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mónaco','MONEGASCA','MCO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mongolia','MONGOLA','MNG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Montenegro','MONTENEGRINA','MNE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mozambique','MOZAMBIQUEÑA','MOZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Namibia','NAMIBIA','NAM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nauru','NAURUANA','NRU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nepal','NEPALÍ','NPL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nicaragua','NICARAGÜENSE','NIC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Níger','NIGERINA','NER');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nigeria','NIGERIANA','NGA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Noruega','NORUEGA','NOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('NuevaZelanda','NEOZELANDÉSA','NZL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Omán','OMANÍ','OMN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PaísesBajos','NEERLANDÉSA','NLD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Pakistán','PAKISTANÍ','PAK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Palaos','PALAUANA','PLW');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Palestina','PALESTINA','PSE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Panamá','PANAMEÑA','PAN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PapúaNuevaGuinea','PAPÚ','PNG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Paraguay','PARAGUAYA','PRY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Perú','PERUANA','PER');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Polonia','POLACA','POL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Portugal','PORTUGUÉSA','PRT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PuertoRico','PUERTORRIQUEÑA','PRI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ReinoUnido','BRITÁNICA','GBR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaCentroafricana','CENTROAFRICANA','CAF');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaCheca','CHECA','CZE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicadeMacedonia','MACEDONIA','MKD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicadelCongo','CONGOLEÑA','COG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaDemocráticadelCongo','CONGOLEÑA','COD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaDominicana','DOMINICANA','DOM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaSudafricana','SUDAFRICANA','ZAF');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ruanda','RUANDÉSA','RWA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Rumanía','RUMANA','ROU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Rusia','RUSA','RUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Samoa','SAMOANA','WSM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanCristóbalyNieves','CRISTOBALEÑA','KNA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanMarino','SANMARINENSE','SMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanVicenteylasGranadinas','SANVICENTINA','VCT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SantaLucía','SANTALUCENSE','LCA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SantoToméyPríncipe','SANTOTOMENSE','STP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Senegal','SENEGALÉSA','SEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Serbia','SERBIA','SRB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Seychelles','SEYCHELLENSE','SYC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SierraLeona','SIERRALEONÉSA','SLE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Singapur','SINGAPURENSE','SGP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Siria','SIRIA','SYR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Somalia','SOMALÍ','SOM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SriLanka','CEILANÉSA','LKA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suazilandia','SUAZI','SWZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SudándelSur','SURSUDANÉSA','SSD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Sudán','SUDANÉSA','SDN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suecia','SUECA','SWE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suiza','SUIZA','CHE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Surinam','SURINAMESA','SUR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tailandia','TAILANDÉSA','THA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tanzania','TANZANA','TZA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tayikistán','TAYIKA','TJK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('TimorOriental','TIMORENSE','TLS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Togo','TOGOLÉSA','TGO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tonga','TONGANA','TON');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('TrinidadyTobago','TRINITENSE','TTO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Túnez','TUNECINA','TUN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Turkmenistán','TURCOMANA','TKM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Turquía','TURCA','TUR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tuvalu','TUVALUANA','TUV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ucrania','UCRANIANA','UKR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uganda','UGANDÉSA','UGA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uruguay','URUGUAYA','URY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uzbekistán','UZBEKA','UZB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Vanuatu','VANUATUENSE','VUT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Venezuela','VENEZOLANA','VEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Vietnam','VIETNAMITA','VNM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Yemen','YEMENÍ','YEM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Yibuti','YIBUTIANA','DJI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Zambia','ZAMBIANA','ZMB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Zimbabue','ZIMBABUENSE','ZWE');

INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (2, 53, 'C001', 'X001', '1717171717', 'jperez@gmail.com', 'JUAN', 'PEREZ', 'M', '1991-05-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (2, 53, 'C002', 'X002', '1616161616', 'dperez@gmail.com', 'DIEGO', 'PEREZ', 'F', '1991-06-22', '022087044', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (3, 53, 'C004', 'X004', '1313131313', 'asuarez@gmail.com', 'ALFONSO', 'SUAREZ', 'M', '1991-11-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (3, 53, 'C005', 'X005', '1212121212', 'csuarez@gmail.com', 'CARMEN', 'SUAREZ', 'F', '1991-08-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (3, 53, 'C006', 'X007', '1414141414', 'clopez@gmail.com', 'CARLOS', 'LOPEZ', 'M', '1990-03-02', '022087014', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (PERSONA_ID, NACIONALIDAD_ID, CODSISTEMA, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (3, 53, 'C007', 'X007', '1313131313', 'lgarcia@gmail.com', 'LUIS', 'GARCIA', 'M', '1991-01-02', '022123014', '0983528439', NULL, NULL, NULL, 'n', NULL, 'n', NULL, NULL, 'A');


INSERT INTO CATEGORIA_PRODUCTO (NOMBRE, DESCRIPCION, IMAGEN_PATH, ESTADO) 
	VALUES ('Ninguna', 'defecto', NULL, NULL);
INSERT INTO ROOT.CATEGORIA_PRODUCTO (NOMBRE, DESCRIPCION, IMAGEN_PATH, ESTADO) 
	VALUES ('Rubros institucionales', 'Rubros institucionales', NULL, 'A');
INSERT INTO ROOT.CATEGORIA_PRODUCTO (NOMBRE, DESCRIPCION, IMAGEN_PATH, ESTADO) 
	VALUES ('Rubros externos', 'Rubros externos', NULL, 'A');


INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, TIPO_COD,MODULO_COD, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID) 
	VALUES (2, 'MA', 'Matriculas','INVS', NULL, 2, NULL, NULL);
INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, TIPO_COD,MODULO_COD, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID) 
	VALUES (2, 'NA', 'pensiones','INVS', NULL, 2, NULL, NULL);
INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, TIPO_COD,MODULO_COD, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID) 
	VALUES (2, 'NA', 'suplementarias','INVS', NULL, 2, NULL, NULL);


INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, TIPO_RUBRO, VALOR) 
	VALUES ('Matricula p2018', 0, 1, NULL, 1, NULL, 500.00);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, TIPO_RUBRO, VALOR) 
	VALUES ('pension enero -junio 2018', 0, 1, NULL, 2, NULL, 150.00);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, TIPO_RUBRO, VALOR) 
	VALUES ('ingles', 0, 1, NULL, 3, NULL, 60.00);


INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO) 
	VALUES ('PRIMERO A', 1, 1, 1, 'A');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO) 
	VALUES ('BASICA A', 1, 1, 3, 'A');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO) 
	VALUES ('INICIA 1 A', 1, 1, 5, 'A');


INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID) 
	VALUES (2, 1);
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID) 
	VALUES (1, 1);
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID) 
	VALUES (3, 2);
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID) 
	VALUES (4, 3);
