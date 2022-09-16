/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.FechaCaducidadData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReporteFechaCaducidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.FechaCaducidadResult;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public interface LoteSeviceIf extends ServiceAbstractIf<Lote>{
    
    public Lote grabar(Lote entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public Lote editar(Lote entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException;
    public void editarSinTransaccion(Lote entity) throws ServicioCodefacException, RemoteException;
    public ReportDataAbstract reporteFechaCaducidad(Sucursal sucursal,Bodega bodega,Date fechaReferencia) throws ServicioCodefacException, RemoteException;
    public boolean existenLotesIngresados(Empresa empresa) throws ServicioCodefacException, RemoteException ;
    public Integer reporteFechaCaducidadTotal(Sucursal sucursal,Bodega bodega,Date fechaReferencia) throws ServicioCodefacException, RemoteException;
    public Lote buscarPorProductoYFechaCaducidad(Producto producto,java.sql.Date fechaVencimiento) throws ServicioCodefacException, RemoteException;
    
}
