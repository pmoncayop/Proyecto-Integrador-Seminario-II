/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.Utils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Properties;
import java.io.FileInputStream;

/**
 *
 * @author ba0100069x
 */
public class Configuracion {
        private Properties propiedades;
//    private static final Logger logs = LoggerFactory.getLogger(Configuracion.class);
    public Configuracion() {
        propiedades = new Properties();
        Load();

    }
    private void Load(){
        try {
            propiedades.load(new FileInputStream("Config.properties"));
        } catch (Exception e) {
            System.err.println("Error en lectura config " + e);
            //logs.error("error al cargar el properties:",e);
        }
    }
    public String getPropiedad(String clave) {
        Load();
        return propiedades.getProperty(clave);
    }
    public static String codificarBase64(String texto) {
        // Convertir el String a bytes y codificar
        return Base64.getEncoder().encodeToString(texto.getBytes());
    }

    public static String decodificarBase64(String textoCodificado) {
        // Decodificar y convertir el resultado de bytes a String
        byte[] bytesDecodificados = Base64.getDecoder().decode(textoCodificado);
        return new String(bytesDecodificados);
    }

}
