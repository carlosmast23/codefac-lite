/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoPrecioDataTable implements Serializable{
        public Producto producto;
        public BigDecimal costoCalculo;
        
        public BigDecimal costoPromedio;
        public BigDecimal costoUltimo;
        public BigDecimal costoUltimoOriginal;
        
        public BigDecimal porcentajePvp1;
        public BigDecimal porcentajePvp2;
        public BigDecimal porcentajePvp3;
        public BigDecimal porcentajePvp4;
        public BigDecimal porcentajePvp5;
        public BigDecimal porcentajePvp6;
        
        
        public BigDecimal pvp1;
        public BigDecimal pvp2;
        public BigDecimal pvp3;
        public BigDecimal pvp4;
        public BigDecimal pvp5;
        public BigDecimal pvp6;

    public ProductoPrecioDataTable(Producto producto) {
        this.producto = producto;
    }
        
        

    public ProductoPrecioDataTable(Producto producto, BigDecimal costoUltimo) {
        this.producto = producto;
        this.costoUltimo = costoUltimo;
        this.costoUltimoOriginal=costoUltimo;
    }
        
        

        public ProductoPrecioDataTable(Producto producto,BigDecimal costoCalculo,BigDecimal costoPromedio,BigDecimal costoUltimo, BigDecimal porcentajePvp1, BigDecimal porcentajePvp2, BigDecimal porcentajePvp3, BigDecimal porcentajePvp4, BigDecimal porcentajePvp5, BigDecimal porcentajePvp6) {
            this.producto = producto;
            this.costoCalculo = costoCalculo;
            this.costoPromedio=costoPromedio;
            this.costoUltimo=costoUltimo;
            this.costoUltimoOriginal=costoUltimo;
            this.porcentajePvp1 = porcentajePvp1;
            this.porcentajePvp2 = porcentajePvp2;
            this.porcentajePvp3 = porcentajePvp3;
            this.porcentajePvp4 = porcentajePvp4;
            this.porcentajePvp5 = porcentajePvp5;
            this.porcentajePvp6 = porcentajePvp6;
        }
        
        public BigDecimal calcularPrecio(BigDecimal porcentajePvp)
        {
            BigDecimal porcentajeCalculo=porcentajePvp.divide(new BigDecimal("100"),2, RoundingMode.HALF_UP).add(BigDecimal.ONE);
            return porcentajeCalculo.multiply(costoCalculo);
        }
        
        public void recalcularValoresDesdePorcentajes(BigDecimal costo)
        {
            pvp1=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp1,pvp1);
            pvp2=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp2,pvp2);
            pvp3=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp3,pvp3);
            pvp4=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp4,pvp4);
            pvp5=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp5,pvp5);
            pvp6=UtilidadesPorcentajes.agregarUtilidadPrecio(costo,porcentajePvp6,pvp6);
            
        }
        
        
        public BigDecimal calcularPvp1()
        {
            return calcularPrecio(porcentajePvp1);
        }
        
        public BigDecimal calcularPvp2()
        {
            return calcularPrecio(porcentajePvp2);
        }
        
        public BigDecimal calcularPvp3()
        {
            return calcularPrecio(porcentajePvp3);
        }
        
        public BigDecimal calcularPvp4()
        {
            return calcularPrecio(porcentajePvp4);
        }
        public BigDecimal calcularPvp5()
        {
            return calcularPrecio(porcentajePvp5);
        }
        
        public BigDecimal calcularPvp6()
        {
            return calcularPrecio(porcentajePvp6);
        }

        public BigDecimal getCostoCalculo() {
            return costoCalculo;
        }

        public void setCostoCalculo(BigDecimal costoCalculo) {
            this.costoCalculo = costoCalculo;
        }

        public BigDecimal getCostoPromedio() {
            return costoPromedio;
        }

        public void setCostoPromedio(BigDecimal costoPromedio) {
            this.costoPromedio = costoPromedio;
        }

        public BigDecimal getCostoUltimo() {
            return costoUltimo;
        }

        public void setCostoUltimo(BigDecimal costoUltimo) {
            this.costoUltimo = costoUltimo;
        }

        
     
}
