/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

import com.sistemasoperativos.pcvirtual.componentes.BUS;
import com.sistemasoperativos.pcvirtual.componentes.CPU;
import com.sistemasoperativos.pcvirtual.componentes.RAM;
import java.util.Map;

/**
 * Implementación del BUS de la computadora virtual.
 *
 * @author Andrew López Herrera
 */
public class BUSModelo1 implements BUS {
    private RAM RAMInstalada;
    private CPU CPUInstalado;

    /**
     * Constructor del BUSModelo1.
     *
     * @param ram La RAM instalada en la computadora virtual.
     * @param cpu La CPU instalada en la computadora virtual.
     */
    public BUSModelo1(RAM ram, CPU cpu) {
        this.RAMInstalada = ram;
        this.CPUInstalado = cpu;
    }

    /**
     * Escribe un dato en la RAM en la dirección especificada.
     *
     * @param direccion La dirección en la RAM donde se escribirá el dato.
     * @param dato El dato a escribir en la RAM.
     * @throws Exception Si la RAM no está disponible o si ocurre un error al escribir el dato.
     */
    @Override
    public void EscribirDatoRAM(String direccion, String dato) throws Exception {
        if (RAMInstalada == null){
            throw new Exception("RAM no está disponible");
        }
        RAMInstalada.EscribirDato(direccion, dato);
    }

    /**
     * Lee un dato de la RAM en la dirección especificada.
     *
     * @param direccion La dirección en la RAM desde donde se leerá el dato.
     * @return El dato leído de la RAM.
     * @throws Exception Si la RAM no está disponible o si ocurre un error al leer el dato.
     */
    @Override
    public String LeerDatoRAM(String direccion) throws Exception {
        if (RAMInstalada == null){
            throw new Exception("RAM no está disponible");
        }
        return RAMInstalada.LeerDato(direccion);
    }

    /**
     * Escribe un dato en la CPU.
     *
     * @param informacion La información a escribir en la CPU.
     * @throws Exception Si la CPU no está disponible o si ocurre un error al escribir el dato.
     */
    @Override
    public void AsignarRegistrosCPU(Map<String, String> registros) throws Exception {
        if (CPUInstalado == null){
            throw new Exception("CPU no está disponible");
        }
        CPUInstalado.CambiarRegistros(registros);
    }

    /**
     * Lee un dato de la CPU.
     *
     * @param informacion La información a leer de la CPU.
     * @return El dato leído de la CPU.
     * @throws Exception Si la CPU no está disponible o si ocurre un error al leer el dato.
     */
    @Override
    public Map<String, String> ObtenerRegistrosCPU() throws Exception {
        if (CPUInstalado == null){
            throw new Exception("CPU no está disponible");
        }
        return CPUInstalado.ObtenerRegistros();
    }

    /**
     * Ejecuta una instrucción en la CPU.
     *
     * @throws Exception Si la CPU no está disponible o si ocurre un error al ejecutar la instrucción.
     */
    @Override
    public void EjecutarInstruccionCPU() throws Exception{
        if (CPUInstalado != null) {
            CPUInstalado.EjecutarInstruccion();
        }
    }
}
