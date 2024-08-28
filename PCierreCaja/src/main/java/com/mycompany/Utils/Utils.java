/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Utils;

//import com.sun.tools.javac.Main;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
/**
 *
 * @author ba0100069x
 */
public class Utils {

    public void DibujaImagenButton(JButton boton, String ImagenPNG,String imagePath, String sToolTipText) {
//        try {
//            // Cargar la imagen desde el recurso
//            URL imgURL = Utils.class.getResource(imagePath + ImagenPNG + ".png");
//            if (imgURL != null) {
//                Image img = ImageIO.read(imgURL);
//                // Redimensionar la imagen si es necesario
//                ImageIcon icon = new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
//                // Asignar el icono al botón
//                boton.setIcon(icon);
//                boton.setToolTipText(sToolTipText);
//            } else {
//                System.out.println("No se pudo encontrar la imagen: " + imagePath);
//            }
//        } catch (IOException e) {
//            System.out.println("No se pudo cargar la imagen: " + imagePath);
//        }        

        ImageIcon icon = new ImageIcon(imagePath + ImagenPNG + ".png");
        boton.setIcon(icon);        
        boton.setToolTipText(sToolTipText);

    }

    public void DibujaImagenToogleButton(JToggleButton boton, String ImagenPNG,String imagePath, String sToolTipText) {
            ImageIcon icon = new ImageIcon(imagePath + ImagenPNG + ".png");
            boton.setIcon(icon);        
            boton.setToolTipText(sToolTipText);
    }
    
}
