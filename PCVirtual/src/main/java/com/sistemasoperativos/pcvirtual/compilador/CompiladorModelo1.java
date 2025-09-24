/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.compilador;

import java.io.FileReader;
import java.util.List;

/**
 *
 * @author andrewdeni
 */
public class CompiladorModelo1 implements Compilador{
    
    
    public CompiladorModelo1(){
        
    }

    @Override
    public List<String> Compilar(String direccion) throws Exception {
        MiLexer lexer = new MiLexer(new FileReader(direccion));
        Parser parser = new Parser(lexer);
        if(parser.VerificarSiHayError()){
            throw new Exception(parser.ObtenerMensajeError());
        }
        return parser.getInstruccionesBinarias();
    }
    
}
