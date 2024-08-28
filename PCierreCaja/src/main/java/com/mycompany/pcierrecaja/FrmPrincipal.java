package com.mycompany.pcierrecaja;
import com.mycompany.Bases.Caja;
import com.mycompany.Utils.Configuracion;
import com.mycompany.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ba0100069x
 */
public class FrmPrincipal extends javax.swing.JFrame {
    public Configuracion config=new Configuracion();
    private final  String sBotonesPath = config.getPropiedad("PathBotones"); //"src\\main\\java\\Iconos\\";
    private final  String sPathConfig =config.getPropiedad("PathReportes");// "src\\main\\java\\com\\mycompany\\pcierrecaja\\";
    File dir = new File(sBotonesPath);
    File[] lista = dir.listFiles();
    public Utils oUtilidad = new Utils();
    public ArrayList<Caja> oListaCaja = new ArrayList<>();
    
    public String VP_URLConexion = "";
    public String bandera_login = "N";
    public String VP_Usuario = "";
    public Integer PV_Caja = 0;

    public class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        // Constructor que acepta la ruta de la imagen
        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Dibujar la imagen de fondo
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }    
    
    // Clase ResizablePanel
    class ResizablePanel extends JPanel {
        private Dimension preferredSize;

        public ResizablePanel(Dimension preferredSize) {
            this.preferredSize = preferredSize;
        }

        @Override
        public Dimension getPreferredSize() {
            return preferredSize;
        }
    }    
    
    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal()  {
        initComponents();
        // Crear el panel de fondo
        //BackgroundPanel backgroundPanel = new BackgroundPanel(this.sBotonesPath + "Fondo.jpg");
       net.sf.jasperreports.engine.util.JRFontUtil.getFontFamilyNames();
        // Configurar el JFrame
        //setContentPane(backgroundPanel);
        setLayout(new BorderLayout());
        //Dibuja los botones
        oUtilidad.DibujaImagenToogleButton(tbSalir, "Salir_32", this.sBotonesPath, "Salir");
        oUtilidad.DibujaImagenToogleButton(tbApertura, "Apertura_32", this.sBotonesPath, "Aperura de Caja");
        oUtilidad.DibujaImagenToogleButton(tbFactura, "Movimientos_32", this.sBotonesPath, "Registro de Movimientos");
        oUtilidad.DibujaImagenToogleButton(tbCierre, "Cierre_32", this.sBotonesPath, "Cierre de Caja");
        // Crear un nuevo JPanel para agregar en la parte superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 0, 0, 0)); // Color semi-transparente
        topPanel.setPreferredSize(new Dimension(200, 100)); // Establecer tamaño preferido
        topPanel.setLayout(new FlowLayout()); // Puedes elegir el layout que prefieras
        //Agregar el Toobal al toppanel
        topPanel.add(ToolMenu, BorderLayout.WEST);
        ToolMenu.setFloatable(false); // Hacer que el JToolBar no sea movible
        // Añadir el topPanel al JFrame en la posición NORTE
        add(topPanel, BorderLayout.WEST);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        //            Properties prop = new Properties();
//            InputStream input = null;
//            
//            // Ruta al archivo de configuración
//           // input = new FileInputStream(sPathConfig + "Config.properties");
//            
//            // Cargar las propiedades desde el archivo
//            prop.load(input);

this.VP_URLConexion = config.getPropiedad("db.url")  ;//config.getProperty("db.url");
new frmLogin(this, true).setVisible(true);
this.HabilitaMenus();
//        if ("S".equals(bandera_login))     {
//            jMenu2.setEnabled(true);
//            jMenu3.setEnabled(true);
//            jMenu4.setEnabled(true);
//            
//            tbFactura.setEnabled(true);
//            tbCierre.setEnabled(true);
//            
//            if (VP_Usuario.equals("SYSADMIN")) {
//                mnuMantCaja.setEnabled(true);
//                mnuApertura.setEnabled(true);
//                tbApertura.setEnabled(true);
//            }
//        }
    }
    
    public final void HabilitaMenus() {
        
        jMenu2.setEnabled(false);
        jMenu3.setEnabled(false);
        jMenu4.setEnabled(false);
        tbApertura.setEnabled(false);
        tbFactura.setEnabled(false);
        tbCierre.setEnabled(false);
        
        mnuMantCaja.setEnabled(false);
        mnuApertura.setEnabled(false);
        
        if ("S".equals(bandera_login))     {
            jMenu2.setEnabled(true);
            jMenu3.setEnabled(true);
            jMenu4.setEnabled(true);
            
            tbFactura.setEnabled(true);
            tbCierre.setEnabled(true);
            
            if (VP_Usuario.equals("SYSADMIN")) {
                mnuMantCaja.setEnabled(true);
                mnuApertura.setEnabled(true);
                tbApertura.setEnabled(true);
            }
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        ToolMenu = new javax.swing.JToolBar();
        tbSalir = new javax.swing.JToggleButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        tbApertura = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        tbFactura = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        tbCierre = new javax.swing.JToggleButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuConectar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuDesconectar = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuMantCaja = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnuApertura = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MnuMovimientos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        MnuCierre = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuReporteCierre = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control de Caja");
        setName("frmPrincipal"); // NOI18N

        ToolMenu.setBorder(null);
        ToolMenu.setRollover(true);

        tbSalir.setToolTipText("Salir");
        tbSalir.setFocusable(false);
        tbSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbSalirActionPerformed(evt);
            }
        });
        ToolMenu.add(tbSalir);
        ToolMenu.add(jSeparator7);

        tbApertura.setToolTipText("Aperura de Caja");
        tbApertura.setFocusable(false);
        tbApertura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbApertura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbApertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAperturaActionPerformed(evt);
            }
        });
        ToolMenu.add(tbApertura);
        ToolMenu.add(jSeparator4);

        tbFactura.setToolTipText("Registro de Movimientos");
        tbFactura.setFocusable(false);
        tbFactura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbFactura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbFacturaActionPerformed(evt);
            }
        });
        ToolMenu.add(tbFactura);
        ToolMenu.add(jSeparator5);

        tbCierre.setToolTipText("Cierre de Caja");
        tbCierre.setFocusable(false);
        tbCierre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbCierre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbCierreActionPerformed(evt);
            }
        });
        ToolMenu.add(tbCierre);
        ToolMenu.add(jSeparator6);

        jMenu1.setText("Sistema");

        jMenuConectar.setText("Conectar");
        jMenuConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConectarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuConectar);
        jMenu1.add(jSeparator3);

        jMenuDesconectar.setText("Desconectar");
        jMenuDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDesconectarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuDesconectar);
        jMenu1.add(jSeparator8);

        jMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Mantenimientos");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        mnuMantCaja.setText("Cajas");
        mnuMantCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMantCajaActionPerformed(evt);
            }
        });
        jMenu2.add(mnuMantCaja);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Transacciones");

        mnuApertura.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mnuApertura.setText("Apertura de Caja");
        mnuApertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAperturaActionPerformed(evt);
            }
        });
        jMenu3.add(mnuApertura);
        jMenu3.add(jSeparator1);

        MnuMovimientos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MnuMovimientos.setText("Registro Movimientos");
        MnuMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuMovimientosActionPerformed(evt);
            }
        });
        jMenu3.add(MnuMovimientos);
        jMenu3.add(jSeparator2);

        MnuCierre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MnuCierre.setText("Cierre de Caja");
        MnuCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuCierreActionPerformed(evt);
            }
        });
        jMenu3.add(MnuCierre);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        jMenuReporteCierre.setText("Cierre de Caja");
        jMenuReporteCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuReporteCierreActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuReporteCierre);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 486, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ToolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAperturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAperturaActionPerformed
        // TODO add your handling code here:
        new frmAperturaCaja(this, false).setVisible(true);
    }//GEN-LAST:event_mnuAperturaActionPerformed

    private void tbAperturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAperturaActionPerformed
        // TODO add your handling code here:
        new frmAperturaCaja(this, false).setVisible(true);
    }//GEN-LAST:event_tbAperturaActionPerformed

    private void tbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_tbSalirActionPerformed

    private void mnuMantCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMantCajaActionPerformed
        new frmCaja(this, false).setVisible(true);
    }//GEN-LAST:event_mnuMantCajaActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void MnuMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuMovimientosActionPerformed
        // TODO add your handling code here:
        new frmMovimientos(this, false).setVisible(true);
    }//GEN-LAST:event_MnuMovimientosActionPerformed

    private void jMenuConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConectarActionPerformed
        // TODO add your handling code here:
        jMenuDesconectar.setEnabled(true);
        jMenuConectar.setEnabled(false);
        new frmLogin(this, false).setVisible(true);
       
    }//GEN-LAST:event_jMenuConectarActionPerformed

    private void jMenuDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDesconectarActionPerformed
        // TODO add your handling code here:
        jMenu2.setEnabled(false);
        jMenu3.setEnabled(false);
        jMenu4.setEnabled(false);
        tbApertura.setEnabled(false);
        tbFactura.setEnabled(false);
        tbCierre.setEnabled(false);
        jMenuDesconectar.setEnabled(false);
        jMenuConectar.setEnabled(true);
        
        bandera_login = "N";
        VP_Usuario = "";
        PV_Caja = 0;

    }//GEN-LAST:event_jMenuDesconectarActionPerformed

    private void tbFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbFacturaActionPerformed
        // TODO add your handling code here:
        new frmMovimientos(this, false).setVisible(true);
    }//GEN-LAST:event_tbFacturaActionPerformed

    private void tbCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbCierreActionPerformed
        // TODO add your handling code here:
        new frmCierreCaja(this, false).setVisible(true);
    }//GEN-LAST:event_tbCierreActionPerformed

    private void MnuCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuCierreActionPerformed
        // TODO add your handling code here:
        new frmCierreCaja(this, false).setVisible(true);
    }//GEN-LAST:event_MnuCierreActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenuReporteCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuReporteCierreActionPerformed
        // TODO add your handling code here:
        new frmRptCierre(this, false).setVisible(true);
    }//GEN-LAST:event_jMenuReporteCierreActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MnuCierre;
    private javax.swing.JMenuItem MnuMovimientos;
    private javax.swing.JToolBar ToolMenu;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuConectar;
    private javax.swing.JMenuItem jMenuDesconectar;
    private javax.swing.JMenuItem jMenuReporteCierre;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JMenuItem mnuApertura;
    private javax.swing.JMenuItem mnuMantCaja;
    private javax.swing.JToggleButton tbApertura;
    private javax.swing.JToggleButton tbCierre;
    private javax.swing.JToggleButton tbFactura;
    private javax.swing.JToggleButton tbSalir;
    // End of variables declaration//GEN-END:variables
}
