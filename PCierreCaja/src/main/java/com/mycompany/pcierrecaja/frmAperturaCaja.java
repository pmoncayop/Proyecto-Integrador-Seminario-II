package com.mycompany.pcierrecaja;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;

import com.mycompany.Utils.Utils;
import com.mycompany.Bases.Caja;
import com.mycompany.Utils.Configuracion;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.text.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
        
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

/**
 *
 * @author ba0100069x
 */
public class frmAperturaCaja extends javax.swing.JDialog {
    private final FrmPrincipal mainForm;
    private static final NumberFormat numberFormat = DecimalFormat.getNumberInstance(Locale.US);
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    
    public Utils oUtilidad = new Utils();
    private ArrayList<Caja> oListaCaja = new ArrayList<>();
    private String sAccion = "N";
    public Configuracion config=new Configuracion();
    private final  String sBotonesPath =config.getPropiedad("PathBotones"); //"src\\main\\java\\Iconos\\";
    
    
    class DecimalDocumentFilter extends DocumentFilter {
        private static final String DECIMAL_REGEX = "\\d*(\\.\\d{0,2})?";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, string);

            if (isValidDecimal(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);

            if (isValidDecimal(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.delete(offset, offset + length);

            if (isValidDecimal(sb.toString())) {
                super.remove(fb, offset, length);
            }
        }

        private boolean isValidDecimal(String text) {
            return text.matches(DECIMAL_REGEX);
        }
    }    
 
    /**
     * Creates new form frmAperturaCaja
     * @param parent
     * @param modal
     */
   
    public frmAperturaCaja(java.awt.Frame parent, boolean modal)  {
        super(parent, modal);
        
        initComponents();
        setLocationRelativeTo(null);
        this.mainForm = (FrmPrincipal) parent;
        
        //Dibuja los botones
        //String sBotonesPath = "src\\main\\java\\Iconos\\";
        oUtilidad.DibujaImagenToogleButton(tbSalir, "Salir", this.sBotonesPath, "Salir");
        oUtilidad.DibujaImagenToogleButton(tbNuevo, "Nuevo", sBotonesPath, "Nuevo");
        oUtilidad.DibujaImagenToogleButton(tbConsultar, "Buscar", sBotonesPath, "Buscar");
        oUtilidad.DibujaImagenToogleButton(tbGrabar, "Guardar", sBotonesPath, "Guardar");
        oUtilidad.DibujaImagenToogleButton(tbLimpiar, "Limpiar", sBotonesPath, "Limpiar");
        
        
        // Crear un PlainDocument y aplicar el DecimalDocumentFilter
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DecimalDocumentFilter());
        txtMonto.setDocument(doc);
        pLimpiar();
        sAccion = "N";
        pCargarUsuarios();
        pCargarCajas();
    }
    
    private void pCargarCajas() {
        try
        {
            cmbCaja.removeAllItems();
            
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;database=dbVentas;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            Connection conn = null;
            conn = DriverManager.getConnection(dbURL);
            
            String sql = "{call sp_vt_mantenimiento_cajas (?, ?, ?, ?, ?, ?)}";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, "A");
            ps.setString(3, "");
            ps.setString(4, "");
            ps.setString(5, "");
            ps.setInt(6, 0);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cmbCaja.addItem(rs.getString("caja"));
            }
            //if (cmbCaja.getItemCount() > 0) {
                cmbCaja.setSelectedIndex(-1);
            //}
            ps.close();
            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Apertura de Cajas", JOptionPane.ERROR_MESSAGE);
        }        
    }
     
    private static void formatTextField(JTextField textField) {
        try {
            String text = textField.getText();
            if (!text.isEmpty()) {
                Number number = decimalFormat.parse(text);
                String formattedText = decimalFormat.format(number);
                textField.setText(formattedText);
            }
        } catch (ParseException e) {
         //   e.printStackTrace();
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

        jToolBar1 = new javax.swing.JToolBar();
        tbSalir = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        tbNuevo = new javax.swing.JToggleButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        tbConsultar = new javax.swing.JToggleButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        tbGrabar = new javax.swing.JToggleButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        tbLimpiar = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        cmbCaja = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        DCFecha = new com.toedter.calendar.JDateChooser();
        cmbUsuarios = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Apertura de Caja");

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

        tbNuevo.setToolTipText("");
        tbNuevo.setFocusable(false);
        tbNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(tbNuevo);
        jToolBar1.add(jSeparator5);

        tbConsultar.setToolTipText("");
        tbConsultar.setFocusable(false);
        tbConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbConsultar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbConsultarActionPerformed(evt);
            }
        });
        jToolBar1.add(tbConsultar);
        jToolBar1.add(jSeparator7);

        tbGrabar.setToolTipText("");
        tbGrabar.setFocusable(false);
        tbGrabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbGrabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbGrabarActionPerformed(evt);
            }
        });
        jToolBar1.add(tbGrabar);
        jToolBar1.add(jSeparator6);

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

        jLabel1.setText("Caja ID:");

        jLabel2.setText("Usuario:");

        jLabel3.setText("Observación:");

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane1.setViewportView(txtObservacion);

        jLabel4.setText("Monto Inicial:");

        txtMonto.setColumns(10);
        txtMonto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMontoFocusLost(evt);
            }
        });

        cmbCaja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Fecha Proceso:");

        DCFecha.setDateFormatString("yyyy MM dd");

        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbCaja, 0, 271, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addGap(3, 3, 3)
                                .addComponent(DCFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addComponent(cmbUsuarios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(cmbCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(DCFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addComponent(cmbUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(88, 88, 88))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tbSalirActionPerformed

    private void tbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbNuevoActionPerformed
        // TODO add your handling code here:
         pLimpiar();

         sAccion = "N";

    }//GEN-LAST:event_tbNuevoActionPerformed

    public int pObtenerSecuenciaID() {
        int iContador = 0;
        iContador = oListaCaja.size() + 1;
//        for (Caja oCaja : oListaCaja) {
//            iContador++;
//        }
        return iContador;
    }    
    
    private void txtMontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoFocusLost
        // TODO add your handling code here:
        formatTextField(txtMonto);        
    }//GEN-LAST:event_txtMontoFocusLost

    private void tbGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbGrabarActionPerformed
        Connection conn = null;    
        try
            {
                if (cmbCaja.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione la caja que desea aperturar", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if (cmbUsuarios.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccionae usuario que tiene asignado la caja", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (txtObservacion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese una observación para el registro", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (Double.parseDouble(txtMonto.getText()) == 0) {
                    JOptionPane.showMessageDialog(null, "El monto de apertura debe ser mayor a cero", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                Date objDate = DCFecha.getDate();
                if (!(isValidDate(objDate))) {
                    JOptionPane.showMessageDialog(null, "La fecha ingresada no es valida", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                String sFecha = objSDF.format(objDate);
                java.util.Date parsedDate = objSDF.parse(sFecha);
                Timestamp dFecha = new Timestamp(parsedDate.getTime());
                
                
                sAccion = "G";
                String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
                conn = DriverManager.getConnection(dbURL);
                conn.setAutoCommit(false); //XXX
                
                CallableStatement callableStatement = null;
                Statement statement = null;

                statement = conn.createStatement();
                statement.execute("USE dbVentas");

                String sql = "{call sp_vt_apertura_caja (?, ?, ?, ?, ?, ?, ?)}";
                callableStatement = conn.prepareCall(sql);

                String sCaja = cmbCaja.getSelectedItem().toString();
                Integer iCaja = Integer.valueOf(sCaja.substring(sCaja.indexOf("-") + 1).trim());
                
                
                // Establecer los parámetros de entrada y salida
                callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);  // Parámetro de retorno
                callableStatement.registerOutParameter("o_msgerr", java.sql.Types.VARCHAR);  // Parámetro de salida
                callableStatement.setString("i_opc", sAccion);
                callableStatement.setInt("i_idcaja", iCaja);
                callableStatement.setTimestamp("i_fchproceso", dFecha);
                callableStatement.setString("i_usuario", cmbUsuarios.getSelectedItem().toString());
                callableStatement.setString("i_observacion", txtObservacion.getText());
                callableStatement.setDouble("i_monto", Double.parseDouble(txtMonto.getText()));
                // Ejecutar el stored procedure
                
                callableStatement.execute();


                // Obtener el valor de retorno
                //int returnValue = callableStatement.getInt("@RETURN_VALUE");      
                //Obtener paametros de salida
                String sMsgErr = callableStatement.getString("o_msgerr");
                if (sMsgErr == null) {
                    JOptionPane.showMessageDialog(null, "Apertura de Caja realizado con éxito", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, sMsgErr, "Apertura de Cajas", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();    
                    return;
                }
                    
                pLimpiar();
                conn.commit();  //XXX
                conn.close();
            
            }
            catch (SQLException e)
            {
                if (conn != null) {
                    try {
                        conn.rollback();    
                    } catch (SQLException ex) {
                    }
                }                
                JOptionPane.showMessageDialog(null, e.getMessage(), "Apertura de Cajas", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                Logger.getLogger(frmAperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
            }        

    }//GEN-LAST:event_tbGrabarActionPerformed

    private void pConsultar() {
        try
        {
            if (cmbCaja.getSelectedItem().toString().trim().equals("")) {
                return;
            }
            
            Date objDate = DCFecha.getDate();
            if (!(isValidDate(objDate))) {
                JOptionPane.showMessageDialog(null, "La fecha ingresada no es valida", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            cmbUsuarios.setSelectedIndex(-1);
            txtObservacion.setText("");
            txtMonto.setText("0.00");            
            
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;database=dbVentas;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            Connection conn = null;
            conn = DriverManager.getConnection(dbURL);
            
            String sCaja = cmbCaja.getSelectedItem().toString();
            Integer iCaja = Integer.valueOf(sCaja.substring(sCaja.indexOf("-") + 1).trim());
            
            String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
            String sFecha = objSDF.format(objDate);
            java.util.Date parsedDate = objSDF.parse(sFecha);
            Timestamp dFecha = new Timestamp(parsedDate.getTime());            
            
            
            
            String sql = "{call sp_vt_apertura_caja (?, ?, ?, ?, ?, ?, ?)}";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, sAccion);
            ps.setInt(3, iCaja);
            ps.setTimestamp(4, dFecha);
            ps.setString(5, null);
            ps.setString(6, null);
            ps.setInt(7, 0);
            ResultSet rs = ps.executeQuery();
            
                if (rs.isBeforeFirst()) {
                    while(rs.next()) {
                        //txtUsuario.setText(rs.getString("pa_usuario"));
                        
                        int iIndex = -1;
                        for (int i = 0; i < cmbUsuarios.getItemCount(); i++) {
                            if (cmbUsuarios.getItemAt(i).equals(rs.getString("pa_usuario"))) {
                             iIndex = i;
                             break;
                            }   
                        }
                        cmbUsuarios.setSelectedIndex(iIndex);
                        txtObservacion.setText(rs.getString("pa_observacion"));
                        txtMonto.setText((Double.valueOf(rs.getString("pa_monto"))).toString());
                    }                    
                }
                else {
                    JOptionPane.showMessageDialog(null, "Apertura de Caja no existe", "Apertura de Cajas", JOptionPane.INFORMATION_MESSAGE);
                }

            
            ps.close();
            rs.close();
            conn.close();
            tbGrabar.setEnabled(false);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Apertura de Cajas", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(frmAperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
        }        

    }
    
    private void tbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbLimpiarActionPerformed
        // TODO add your handling code here:
        pLimpiar();
//        txtID.setEnabled(true);
//        txtID.requestFocus();
    }//GEN-LAST:event_tbLimpiarActionPerformed

    private void tbConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbConsultarActionPerformed
        // TODO add your handling code here:
        sAccion = "C";
//        if (txtID.getText().equals("") || sAccion.equals("N")) {
//            return;
//        }        
//        txtID.setEnabled(false);
        pConsultar();        
        //txtID.setEnabled(false);
    }//GEN-LAST:event_tbConsultarActionPerformed

    private void pLimpiar(){
        
        cmbCaja.setSelectedIndex(-1);
        cmbUsuarios.setSelectedIndex(-1);
        txtObservacion.setText("");
        txtMonto.setText("0.00");
        
        
        Date objDate = new Date(); // Current System Date and time is assigned to objDate
        //String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
        //SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        //String sFecha = objSDF.format(objDate);
        
        DCFecha.setDate(objDate);
        
        sAccion = "N";    
        
        tbGrabar.setEnabled(true);
    }

    private boolean isValidDate(Date Fecha) {
        if (Fecha != null) {
        } else {
            return false;
        }
        return true;
    }
    
//private boolean isValidDate(Date fecha) {
//    String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
//    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
//    objSDF.setLenient(false); // Disable lenient parsing
//    try {
//        String sFecha = objSDF.format(fecha);    
//        Date date = objSDF.parse(sFecha);
//    } catch (ParseException e) {
//        return false;
//    }
//    return true;
//}    
    
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
            java.util.logging.Logger.getLogger(frmAperturaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAperturaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAperturaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAperturaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmAperturaCaja dialog = new frmAperturaCaja(new javax.swing.JFrame(), true);
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
    
    private void pCargarUsuarios() {
        try
        {
            cmbUsuarios.removeAllItems();

            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;database=dbVentas;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            Connection conn = null;
            conn = DriverManager.getConnection(dbURL);

            String sql = "{call sp_se_consulta_usuarios}";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cmbUsuarios.addItem(rs.getString("us_codigo"));
            }
            cmbUsuarios.setSelectedIndex(-1);

            ps.close();
            rs.close();
            conn.close();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Apertura de Cajas", JOptionPane.ERROR_MESSAGE);
        }        
    }
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DCFecha;
    private javax.swing.JComboBox<String> cmbCaja;
    private javax.swing.JComboBox<String> cmbUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToggleButton tbConsultar;
    private javax.swing.JToggleButton tbGrabar;
    private javax.swing.JToggleButton tbLimpiar;
    private javax.swing.JToggleButton tbNuevo;
    private javax.swing.JToggleButton tbSalir;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables


}
