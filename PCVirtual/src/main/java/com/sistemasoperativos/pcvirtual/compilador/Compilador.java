/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.compilador;

import java.util.List;

/**
 *
 * @author andrewdeni
 */
public interface Compilador {
    public List<String> Compilar(String direccion) throws Exception;
}
