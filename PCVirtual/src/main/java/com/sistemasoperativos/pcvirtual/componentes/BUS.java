/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

/**
 * Interfaz para el BUS de la computadora virtual.
 *
 * @author Andrew López Herrera
 */
public interface BUS {
    public void EscribirDatoRAM(String direccion, String dato) throws Exception;
    public String LeerDatoRAM(String direccion) throws Exception;
    public void EscribirDatoCPU(String informacion) throws Exception;
    public String LeerDatoCPU(String informacion) throws Exception;
    public void EjecutarInstruccionCPU() throws Exception;
}
