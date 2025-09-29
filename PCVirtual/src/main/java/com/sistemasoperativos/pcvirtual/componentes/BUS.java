/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

import java.util.Map;

/**
 * Interfaz para el BUS de la computadora virtual.
 *
 * @author Andrew López Herrera
 */
public interface BUS {
    public void EscribirDatoRAM(String direccion, String dato) throws Exception;
    public String LeerDatoRAM(String direccion) throws Exception;
    public void AsignarRegistrosCPU(Map<String, String> registros) throws Exception;
    public Map<String, String> ObtenerRegistrosCPU() throws Exception;
    public void EjecutarInstruccionCPU() throws Exception;
}
