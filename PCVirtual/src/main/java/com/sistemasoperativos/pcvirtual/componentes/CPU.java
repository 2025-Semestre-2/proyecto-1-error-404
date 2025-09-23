/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

/**
 * Interfaz para la Unidad Central de Procesamiento (CPU) de la computadora virtual.
 *
 * @author Andrew López Herrera
 */
public interface CPU {
    public void EjecutarInstruccion() throws Exception;
    public boolean EscribirRegistro(String informacion)throws Exception;
    public String LeerRegistro(String informacion) throws Exception;
    public void AsignarBUS(BUS busAsignado) throws Exception;
}
