package com.sistemasoperativos.pcvirtual.controlador;

import com.sistemasoperativos.pcvirtual.compilador.AdmnistradorProgramasNuevos;
import com.sistemasoperativos.pcvirtual.componentes.Almacenamiento;
import com.sistemasoperativos.pcvirtual.componentes.AlmacenamientoModelo1;
import com.sistemasoperativos.pcvirtual.componentes.BUS;
import com.sistemasoperativos.pcvirtual.componentes.BUS2;
import com.sistemasoperativos.pcvirtual.componentes.BUSModelo1;
import com.sistemasoperativos.pcvirtual.componentes.BUSModelo2;
import com.sistemasoperativos.pcvirtual.componentes.BUSPantalla;
import com.sistemasoperativos.pcvirtual.componentes.CPU;
import com.sistemasoperativos.pcvirtual.componentes.CPUModelo2;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import com.sistemasoperativos.pcvirtual.componentes.RAM;
import com.sistemasoperativos.pcvirtual.componentes.RAMModelo1;
import com.sistemasoperativos.pcvirtual.instrucciones.Add;
import com.sistemasoperativos.pcvirtual.instrucciones.Dec;
import com.sistemasoperativos.pcvirtual.instrucciones.Inc;
import com.sistemasoperativos.pcvirtual.instrucciones.Instruccion;
import com.sistemasoperativos.pcvirtual.instrucciones.Int;
import com.sistemasoperativos.pcvirtual.instrucciones.JE;
import com.sistemasoperativos.pcvirtual.instrucciones.JMP;
import com.sistemasoperativos.pcvirtual.instrucciones.JNE;
import com.sistemasoperativos.pcvirtual.instrucciones.Load;
import com.sistemasoperativos.pcvirtual.instrucciones.Mov;
import com.sistemasoperativos.pcvirtual.instrucciones.Pop;
import com.sistemasoperativos.pcvirtual.instrucciones.Push;
import com.sistemasoperativos.pcvirtual.instrucciones.Store;
import com.sistemasoperativos.pcvirtual.instrucciones.Sub;
import com.sistemasoperativos.pcvirtual.instrucciones.Swap;
import com.sistemasoperativos.pcvirtual.instrucciones.CMP;
import com.sistemasoperativos.pcvirtual.instrucciones.Param;
import com.sistemasoperativos.pcvirtual.procesos.BCP;
import com.sistemasoperativos.pcvirtual.procesos.GestorProcesos;
import com.sistemasoperativos.pcvirtual.procesos.Planificador;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Controlador de la PC virtual.
 *
 * Nota: el método CrearInstrucciones() llena el mapa de instrucciones con las
 * claves (mnemonicos) usadas en el proyecto. Actualmente coloca valores
 * {@code null} como "placeholders"; reemplaza esos {@code null} por
 * instancias concretas de clases que implementen {@link Instruccion} en tu
 * proyecto (por ejemplo: new LoadInstruction(...)).
 *
 * Esto evita que la clase dependa directamente de la implementación concreta
 * de cada instrucción y permite inicializar el mapa desde aquí.
 *
 * Si quieres, puedo ayudarte a implementar las clases concretas de cada
 * instrucción (LOAD, STORE, MOV, ...). Dime cuáles quieres primero.
 *
 * @author andrewdeni
 */
public class Controlador {
    
    private AdmnistradorProgramasNuevos AdministradorProgramas;
    private BUS2 BUSAsignado;
    private GestorProcesos Gestor;

    public Controlador(){
        AdministradorProgramas = null;
    }

    public void CrearPC(int tamanoRAM, int tamanoAlmacenamiento){
        int direccionEscrituraAlmacenamiento = 0;
        Conversor conversor = new Conversor();
        RAM ram = new RAMModelo1(tamanoRAM);
        Almacenamiento almacenamiento = new AlmacenamientoModelo1(
            tamanoAlmacenamiento, conversor, direccionEscrituraAlmacenamiento);
        Map<String, Instruccion> instrucciones = new HashMap<>();
        Map<String, String> registros = CrearRegistros();
        CPU cpu = new CPUModelo2(instrucciones, registros);
        LinkedList<String> direccionesProgramas = new LinkedList();
        LinkedList<String> nombresProgramas = new LinkedList();
        Planificador planificador = new Planificador(direccionesProgramas, nombresProgramas);
        BUS2 bus = new BUSModelo2(ram, cpu, planificador, almacenamiento);
        BUSPantalla busPantalla = null;
        CrearInstrucciones(instrucciones, bus, conversor, busPantalla);
        AdministradorProgramas = new AdmnistradorProgramasNuevos(nombresProgramas, direccionesProgramas, bus);
        BUSAsignado = bus;
        Gestor = new GestorProcesos(planificador);
    }

    /**
     * PC = 00000
     * AC = 00001
     * IR = 00010
     * AX = 00011
     * BX = 00100
     * CX = 00101
     * DX = 00110
     * CP = 00111
     * SP = 01000
     */
    private Map<String, String> CrearRegistros() {
        Map<String, String> registros = new HashMap<>();
        registros.put("PC", "00000000");
        registros.put("AC", "00000000");
        registros.put("IR", "00000000");
        registros.put("AX", "00000000");
        registros.put("BX", "00000000");
        registros.put("CX", "00000000");
        registros.put("DX", "00000000");
        registros.put("CP", "00000000");
        registros.put("SP", "00000000");
        return registros;
    }

    /**
     * Rellena el mapa de instrucciones con las claves (mnemonicos) usadas en la
     * VM. Por ahora se insertan valores {@code null} como marcadores — debes
     * reemplazarlos por instancias reales de las clases que implementen
     * {@link Instruccion} en tu proyecto.
     *
     * Tabla de opcodes (5 bits) — referencia:
     *
     * Instrucción   Binario   Decimal
     * --------------------------------
     * LOAD          00000     0
     * STORE         00001     1
     * MOV           00010     2
     * ADD           00011     3
     * SUB           00100     4
     * INC           00101     5
     * DEC           00110     6
     * SWAP          00111     7
     * INT           01000     8
     * JMP           01001     9
     * CMP           01010     10
     * JE            01011     11
     * JNE           01100     12
     * PARAM         01101     13
     * PUSH          01110     14
     * POP           01111     15
     *
     * (Códigos disponibles: 16–31)
     *
     * @param instrucciones mapa a llenar (clave: mnemonico, valor: instancia)
     */
    private void CrearInstrucciones(Map<String, Instruccion> instrucciones, BUS2 bus,
            Conversor conversor, BUSPantalla busPantalla){
        instrucciones.put("00000", new Load(conversor, 2, bus)); // LOAD
        instrucciones.put("00001", new Store(conversor, 2, bus)); // STORE
        instrucciones.put("00010", new Mov(conversor, 1)); // MOV
        instrucciones.put("00011", new Add(conversor, 3)); // ADD
        instrucciones.put("00100", new Sub(conversor, 3)); // SUB
        instrucciones.put("00101", new Inc(conversor, 1)); // INC
        instrucciones.put("00110", new Dec(conversor, 1)); // DEC
        instrucciones.put("00111", new Swap(conversor, 1)); // SWAP
        instrucciones.put("01000", new Int(conversor, 1, busPantalla, bus)); // INT
        instrucciones.put("01001", new JMP(conversor, 2)); // JMP
        instrucciones.put("01010", new CMP(conversor, 1)); // CMP
        instrucciones.put("01011", new JE(conversor, 2)); // JE
        instrucciones.put("01100", new JNE(conversor, 2)); // JNE
        instrucciones.put("01101", new Param(conversor, 2, bus)); // PARAM
        instrucciones.put("01110", new Push(conversor, 2, bus)); // PUSH
        instrucciones.put("01111", new Pop(conversor, 2, bus)); // POP
    }
    
    public void EjecutarInstruccion() throws Exception{
        BUSAsignado.EjecutarInstruccionCPU();
    }
    
    public void CargarPrograma(File archivo) throws Exception{
        AdministradorProgramas.CargarPrograma(archivo);
    }
    
    public Map<String, String> TraerRegistros() throws Exception{
        return BUSAsignado.ObtenerRegistrosCPU();
    }
    
    public BCP TraerBCPActual(){
        return Gestor.getProcesoActual();
    }
    
    public Map<String, String> TraerMemoriaActual(){
        return BUSAsignado.TraerMemoriaRAM();
    }
    
    public Map<String, String> TraerAlmacenamiento() {
        return BUSAsignado.TraerAlmacenamiento();
    }
}
