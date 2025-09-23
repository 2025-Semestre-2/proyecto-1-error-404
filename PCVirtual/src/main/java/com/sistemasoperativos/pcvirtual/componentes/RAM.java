/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

/**
 * Interfaz para la Memoria de Acceso Aleatorio (RAM) de la computadora virtual.
 *
 * @author Andrew López Herrera
 */
public interface RAM {
    public Boolean EscribirDato(String direccion, String dato) throws Exception;
    public String LeerDato(String direccion) throws Exception;
}
