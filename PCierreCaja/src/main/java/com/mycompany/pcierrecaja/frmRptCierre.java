package com.mycompany.pcierrecaja;
import com.mycompany.Utils.Configuracion;
import com.mycompany.Utils.Utils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

/**
 *
 * @author ba0100069x
 */



public class frmRptCierre extends javax.swing.JDialog {
    public Configuracion config=new Configuracion();
    public Utils oUtilidad = new Utils();
    private final  String sBotonesPath =config.getPropiedad("PathBotones");
            //"src\\main\\java\\Iconos\\";
    private final  String sPathReportes = config.getPropiedad("PathReportes");
            //"src\\main\\java\\com\\mycompany\\Reportes\\";
    private final FrmPrincipal mainForm;
    /**
     * Creates new form frmRptCierre
     */
    public frmRptCierre(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.mainForm = (FrmPrincipal) parent;
        setLocationRelativeTo(null);
        oUtilidad.DibujaImagenToogleButton(tbSalir, "Salir", this.sBotonesPath, "Salir");
        oUtilidad.DibujaImagenToogleButton(tbImprimir, "Reportes", this.sBotonesPath, "Imprimir");
        oUtilidad.DibujaImagenToogleButton(tbLimpiar, "Limpiar", sBotonesPath, "Limpiar");
        pCargarCajas();
        pLimpiar();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        tbSalir = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        tbImprimir = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        tbLimpiar = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbCaja = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        DCFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        DCFechaFinal = new com.toedter.calendar.JDateChooser();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar1.setRollover(true);

        tbSalir.setToolTipText("");
        tbSalir.setFocusable(false);
        tbSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbSalirActionPerformed(evt);
            }
        });
        jToolBar1.add(tbSalir);
        jToolBar1.add(jSeparator4);

        tbImprimir.setToolTipText("");
        tbImprimir.setFocusable(false);
        tbImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbImprimirActionPerformed(evt);
            }
        });
        jToolBar1.add(tbImprimir);
        jToolBar1.add(jSeparator5);

        tbLimpiar.setToolTipText("");
        tbLimpiar.setFocusable(false);
        tbLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbLimpiarActionPerformed(evt);
            }
        });
        jToolBar1.add(tbLimpiar);
        jToolBar1.add(jSeparator1);

        jLabel1.setText("Caja:");

        cmbCaja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Fecha Inicial:");

        DCFechaInicial.setDateFormatString("yyyy MM dd");

        jLabel4.setText("Fecha Final:");

        DCFechaFinal.setDateFormatString("yyyy MM dd");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(DCFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DCFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(DCFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(DCFechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tbSalirActionPerformed

    private void tbImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbImprimirActionPerformed
        try {                                           
            // TODO add your handling code here:
            Connection conn = null;
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;user=sa;password=84+-blaster32;database=dbVentas;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            conn = DriverManager.getConnection(dbURL);
            
            
            String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
            Date objDateIni = DCFechaInicial.getDate();
            Date objDateFin = DCFechaFinal.getDate();
            
            String sCaja = cmbCaja.getSelectedItem().toString();
            Integer iCaja = 0;
            if (!sCaja.equals("Todas")) {
                iCaja = Integer.valueOf(sCaja.substring(sCaja.indexOf("-") + 1).trim());
            }
            
            if (!(isValidDate(objDateIni))) {
                JOptionPane.showMessageDialog(null, "La fecha inicial no es valida", "Reporte de Cierre", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if (!(isValidDate(objDateFin))) {
                JOptionPane.showMessageDialog(null, "La fecha final no es valida", "Reporte de Cierre", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if (objDateFin.compareTo(objDateIni) < 0) {
                JOptionPane.showMessageDialog(null, "La fecha final debe ser mayor o igual a la fecha inicial", "Reporte de Cierre", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String sFechaIni = objSDF.format(objDateIni);
            String sFechaFin = objSDF.format(objDateFin);
            
            //Codigo para generación de reporte
            JasperReport reporte = null;
            try {
                reporte = (JasperReport) JRLoader.loadObjectFromFile(sPathReportes + "\\rptCierreDetalle.jasper");
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("pCaja", iCaja);
                parametros.put("pFechaIni", sFechaIni);
                parametros.put("pFechaFin", sFechaFin);
                JasperPrint jasperprint = null;
                jasperprint = JasperFillManager.fillReport(reporte, parametros, conn);
                if (jasperprint != null) {
                    JasperViewer.viewReport(jasperprint, false);
                }
            } catch (JRException e) {                
                JOptionPane.showMessageDialog(null, e.getMessage(), "Reporte de Cierre Caja", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmRptCierre.class.getName()).log(Level.SEVERE, null, ex);                
        }          
    }//GEN-LAST:event_tbImprimirActionPerformed

    private void tbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbLimpiarActionPerformed
        // TODO add your handling code here:
        pLimpiar();
    }//GEN-LAST:event_tbLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(frmRptCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRptCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRptCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRptCierre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmRptCierre dialog = new frmRptCierre(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    

    /**************************************************************/
    /*                      Metodos Privados                      */
    /**************************************************************/
    private void pCargarCajas() {
        try
        {
//            Date objDate = DCFechaInicial.getDate(); // Current System Date and time is assigned to objDate
//            String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
//            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
//            String sFecha = objSDF.format(objDate);    
//            java.util.Date parsedDate = objSDF.parse(sFecha);
//            Timestamp dFecha = new Timestamp(parsedDate.getTime());            
            
            
            cmbCaja.removeAllItems();
            cmbCaja.addItem("Todas");
            
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;database=dbVentas;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            Connection conn = null;
            conn = DriverManager.getConnection(dbURL);
            
            String sql = "{call sp_vt_mantenimiento_cajas (?, ?, ?, ?, ?, ?, ?)}";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, "A");
            ps.setString(3, "");
            ps.setString(4, "");
            ps.setString(5, "");
            ps.setInt(6, 0);
            ps.setTimestamp(7, null);                
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cmbCaja.addItem(rs.getString("caja"));
            }
            
            ps.close();
            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Cierre de Caja", JOptionPane.ERROR_MESSAGE);
        }        
    }

    private boolean isValidDate(Date Fecha) {
        if (Fecha != null) {
        } else {
            return false;
        }
        return true;
    }    
    
    
    private void pLimpiar(){
        
        cmbCaja.setSelectedIndex(0);
        Date objDate = new Date(); // Current System Date and time is assigned to objDate
        DCFechaInicial.setDate(objDate);
        DCFechaFinal.setDate(objDate);
        
    }    
       

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DCFechaFinal;
    private com.toedter.calendar.JDateChooser DCFechaInicial;
    private javax.swing.JComboBox<String> cmbCaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToggleButton tbImprimir;
    private javax.swing.JToggleButton tbLimpiar;
    private javax.swing.JToggleButton tbSalir;
    // End of variables declaration//GEN-END:variables
}
