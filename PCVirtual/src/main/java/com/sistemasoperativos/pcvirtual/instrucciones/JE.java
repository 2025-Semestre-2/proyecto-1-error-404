/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.instrucciones;

import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.Map;

/**
 *
 * @author andre
 */
public class JE extends JMP implements Instruccion{

    public JE(Conversor conversor, int peso) {
        super(conversor, peso);
    }

    @Override
    public boolean EjecutarInstruccion(String instruccion, Map<String, String> registros) throws Exception {
        Registros = registros;
        if(AplicarPeso())
            return false;
        Desestructurar(instruccion);
        String banderaBits = Registros.get("00111");
        int bandera = ConversorAsignado.ConvertirBitsAInteger(banderaBits);
        if(bandera == 1){
            String siguienteInstruccion = Registros.get("00000");
            int siguienteInstruccionEntero = ConversorAsignado.ConvertirBitsAInteger(siguienteInstruccion);
            int desplazamiento = ConversorAsignado.ConvertirBitsAInteger(Param1);
            int direccionDesplazada = siguienteInstruccionEntero + desplazamiento;
            String bitsDesplazamiento = ConversorAsignado.ConvertirIntegerABits(direccionDesplazada);
            Registros.put("00000", bitsDesplazamiento);
            return false;
        }
        return true;
    }
}
