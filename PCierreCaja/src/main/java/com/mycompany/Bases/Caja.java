/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Bases;

/**
 *
 * @author ba0100069x
 */
public class Caja {
    public int idCaja;
    public String Usuario;
    public String Observacion;
    public double Monto;
    
    
    public Caja(){
        
    }
    
    public Caja(int iID, String sUsuario, String sObservacion, double dMonto) {
        this.idCaja = iID;
        this.Usuario  = sUsuario;
        this.Observacion = sObservacion;
        this.Monto = dMonto;
    }
    
}
