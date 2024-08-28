/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.pcierrecaja;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.Utils.Utils;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.DefaultTableCellRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mycompany.Utils.Configuracion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ba0100069x
 */
public class frmMovimientos extends javax.swing.JDialog {
    public Utils oUtilidad = new Utils();
    public Configuracion config=new Configuracion();
    private String sAccion = "N";
    private final  String sBotonesPath = config.getPropiedad("PathBotones");
    private FrmPrincipal mainForm;
    
    /**
     * Creates new form frmMovimientos
     */
    public frmMovimientos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCustomComponents();
        setLocationRelativeTo(null);
        this.mainForm = (FrmPrincipal) parent;
        oUtilidad.DibujaImagenToogleButton(tbSalir, "Salir", this.sBotonesPath, "Salir");
        oUtilidad.DibujaImagenToogleButton(tbNuevo, "Nuevo", sBotonesPath, "Nuevo");
        oUtilidad.DibujaImagenToogleButton(tbConsultar, "Buscar", sBotonesPath, "Buscar");
        oUtilidad.DibujaImagenToogleButton(tbGrabar, "Guardar", sBotonesPath, "Guardar");
        oUtilidad.DibujaImagenToogleButton(tbEliminar, "Delete", sBotonesPath, "Eliminar");
        oUtilidad.DibujaImagenToogleButton(tbLimpiar, "Limpiar", sBotonesPath, "Limpiar");

        oUtilidad.DibujaImagenToogleButton(tbAgregar, "Agregar", sBotonesPath, "Agregar");
        oUtilidad.DibujaImagenToogleButton(tbQuitar, "Quitar", sBotonesPath, "Quitar");
        
        // Aplicar el filtro para que solo se puedan introducir números
        ((AbstractDocument) txtDocumento.getDocument()).setDocumentFilter(new NumericDocumentFilter());
             
        
        pLimpiar("S");
        tbGrabar.setEnabled(false);
        lblUsuario.setText(mainForm.VP_Usuario);
    }

    // Método para inicializar componentes personalizados
    private void initCustomComponents() {
        // Configurar el modelo de la tabla
        String[] columnNames = {"Producto/Servicio", "Cantidad", "Precio Unitario", "SubTotal", "% IVA", "Valor IVA", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 3 && column != 4 && column != 5 && column != 6; // Solo la columna "Total" no es editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 1, 2, 3 -> Double.class;
                    case 4 -> Double.class;
                    case 5 -> Double.class;
                    case 6 -> Double.class;
                    default -> String.class;
                }; // Cantidad
                // Precio Unitario
                // SubTotal
                // Valor Iva
                // Total
            }
        };

        grdDatos.setModel(tableModel);
        //tableModel.addTableModelListener(e -> updateTotalGeneral());
        
        grdDatos.getColumnModel().getColumn(1).setCellEditor(new QuantityEditor());
        grdDatos.getColumnModel().getColumn(1).setCellRenderer(new QuantityRenderer());
        
        grdDatos.getColumnModel().getColumn(2).setCellEditor(new QuantityEditor());
        grdDatos.getColumnModel().getColumn(2).setCellRenderer(new QuantityRenderer());


        // Asignar el renderizador personalizado a las columnas numéricas
        for (int i = 1; i < columnNames.length; i++) {
            if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
                grdDatos.getColumnModel().getColumn(i).setCellRenderer(new DecimalFormatRenderer());
            }
        }

        // Ajustar el tamaño de las columnas
        grdDatos.getColumnModel().getColumn(0).setPreferredWidth(200); // Item
        grdDatos.getColumnModel().getColumn(1).setPreferredWidth(70);  // Cantidad
        grdDatos.getColumnModel().getColumn(2).setPreferredWidth(70); // Precio Unitario
        grdDatos.getColumnModel().getColumn(3).setPreferredWidth(70); // Total
        grdDatos.getColumnModel().getColumn(4).setPreferredWidth(70); // Descripción
        grdDatos.getColumnModel().getColumn(5).setPreferredWidth(70);  // Impuesto
        grdDatos.getColumnModel().getColumn(6).setPreferredWidth(70);  // Descuento
    
        // Agregar listener para actualizar los totales
        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 1 || column == 2) { // Solo si cambia Cantidad o Precio Unitario o % IVA
                Double quantity = (Double) tableModel.getValueAt(row, 1);
                Double unitPrice = (Double) tableModel.getValueAt(row, 2);
                Double porcIva = (Double) tableModel.getValueAt(row, 4);
                Double Subtotal = (quantity * unitPrice);
                Double ValorIva = (Subtotal * (porcIva / 100));
                Double Total = Subtotal + ValorIva;
                
                tableModel.setValueAt(Subtotal, row, 3);
                tableModel.setValueAt(ValorIva, row, 5);
                tableModel.setValueAt(Total, row, 6);
               
                updateTotalGeneral();
            }
        });
    }    
    
    // Método para actualizar el total general
    private void updateTotalGeneral() {
        DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();

        // Recalcular el total general
        double dSubTotal = 0.0;
        double dIva15 = 0.0;
        double dTotal = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            dSubTotal += (Double) tableModel.getValueAt(i, 3);
            dIva15 += (Double) tableModel.getValueAt(i, 5);
            dTotal += (Double) tableModel.getValueAt(i, 6);
        }
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        txtSubtotal.setText(formato.format(((Number) dSubTotal).doubleValue()));
        TxtIva15.setText(formato.format(((Number) dIva15).doubleValue()));
        txtTotal.setText(formato.format(((Number) dTotal).doubleValue()));
        
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
        tbEliminar = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        tbLimpiar = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        cmbTipoMovimiento = new javax.swing.JComboBox<>();
        txtDocumento = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        TxtIva15 = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        grdDatos = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        tbAgregar = new javax.swing.JToggleButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        tbQuitar = new javax.swing.JToggleButton();

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

        tbEliminar.setFocusable(false);
        tbEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbEliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(tbEliminar);
        jToolBar1.add(jSeparator2);

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

        jLabel2.setText("Documento:");

        jLabel3.setText("Fecha:");

        jLabel4.setText("Tipo Movimiento:");

        jLabel5.setText("Descripción:");

        lblUsuario.setText("Paúl Moncayo");

        cmbTipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingreso", "Egreso", " " }));

        txtDocumento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocumento.setText("jTextField1");

        lblFecha.setText("jLabel7");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel8.setText("Subtotal:");

        jLabel10.setText("Iva 15%");

        jLabel11.setText("Total:");

        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setText("jTextField1");
        txtSubtotal.setEnabled(false);

        TxtIva15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TxtIva15.setText("jTextField3");
        TxtIva15.setEnabled(false);

        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("jTextField4");
        txtTotal.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxtIva15)
                    .addComponent(txtTotal)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtDocumento))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtIva15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(lblUsuario)
                            .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblFecha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );

        grdDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto/Servicio", "Cantidad", "Valor", "Subtotal", "% Iva", "Valor IVA", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdDatos.setRowHeight(25);
        grdDatos.setShowGrid(true);
        grdDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(grdDatos);
        if (grdDatos.getColumnModel().getColumnCount() > 0) {
            grdDatos.getColumnModel().getColumn(0).setResizable(false);
            grdDatos.getColumnModel().getColumn(0).setPreferredWidth(250);
        }

        jToolBar2.setRollover(true);

        tbAgregar.setToolTipText("");
        tbAgregar.setFocusable(false);
        tbAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAgregarActionPerformed(evt);
            }
        });
        jToolBar2.add(tbAgregar);
        jToolBar2.add(jSeparator8);

        tbQuitar.setToolTipText("");
        tbQuitar.setFocusable(false);
        tbQuitar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbQuitar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbQuitarActionPerformed(evt);
            }
        });
        jToolBar2.add(tbQuitar);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tbSalirActionPerformed

    private void tbNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbNuevoActionPerformed
        // TODO add your handling code here:
        pLimpiar("S");
        sAccion = "N";
        tbGrabar.setEnabled(true);
        tbAgregar.setEnabled(true);
        tbQuitar.setEnabled(true);          
    }//GEN-LAST:event_tbNuevoActionPerformed

    private void tbConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbConsultarActionPerformed
        // TODO add your handling code here:
        sAccion = "C";
        if (txtDocumento.getText().equals("") || sAccion.equals("N")) {
                return;
            }
        txtDocumento.setEnabled(false);
        pConsultar();
        
    }//GEN-LAST:event_tbConsultarActionPerformed

    private void tbGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbGrabarActionPerformed
        Connection conn = null;
        try
        {
            DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();
            ObjectMapper mapper = new ObjectMapper();
            
            String SubtotalText = txtSubtotal.getText();
            SubtotalText = SubtotalText.replace(",", ".");
            Double dSubtotal = Double.valueOf(SubtotalText);

            String IvaText = TxtIva15.getText();
            
            
            String TotalText = txtTotal.getText();
            
            


            //Se crea el cnodo raiz del Json
            ObjectNode FacturaJson = mapper.createObjectNode();
            //FacturaJson.put("documento", txtDocumento.getText());
            FacturaJson.put("caja", mainForm.PV_Caja.toString());
            FacturaJson.put("tipomovimiento", Character.toString(cmbTipoMovimiento.getSelectedItem().toString().charAt(0)));
            FacturaJson.put("fecha", lblFecha.getText());
            FacturaJson.put("observacion", txtDescripcion.getText());
            FacturaJson.put("subtotal", Double.valueOf(txtSubtotal.getText().replace(",", ".")));
            FacturaJson.put("iva", Double.valueOf(TxtIva15.getText().replace(",", ".")));
            FacturaJson.put("total", Double.valueOf(txtTotal.getText().replace(",", ".")));

            // Crear un array JSON para los detalles de la factura
            ArrayNode detailsArray = FacturaJson.putArray("detalle");
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                ObjectNode detalle = mapper.createObjectNode();
                detalle.put("secuencia", i+1);
                detalle.put("producto", tableModel.getValueAt(i, 0).toString());
                detalle.put("cantidad", (Double) tableModel.getValueAt(i, 1));        
                detalle.put("valor", (Double) tableModel.getValueAt(i, 2));
                detalle.put("subtotal", (Double) tableModel.getValueAt(i, 3));
                detalle.put("porcentajeiva", (Double) tableModel.getValueAt(i, 4));
                detalle.put("valoriva", (Double) tableModel.getValueAt(i, 5));
                detalle.put("total", (Double) tableModel.getValueAt(i, 6));
                detailsArray.add(detalle);
            }        
            
            String jsonString = mapper.writeValueAsString(FacturaJson);

            sAccion = "G";
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            conn = DriverManager.getConnection(dbURL);
            conn.setAutoCommit(false); //XXX
            CallableStatement callableStatement = null;
            Statement statement = null;

            statement = conn.createStatement();
            statement.execute("USE dbVentas");

            String sql = "{call sp_vt_grabadocumento (?, ?, ?, ?, ?)}";
            callableStatement = conn.prepareCall(sql);

            // Establecer los parámetros de entrada y salida
            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);  // Parámetro de retorno
            callableStatement.registerOutParameter("o_msgerr", java.sql.Types.VARCHAR);  // Parámetro de salida
            callableStatement.setString("i_opc", sAccion);
            callableStatement.setString("i_usuario", lblUsuario.getText());
            callableStatement.setString("i_Json", jsonString);
            callableStatement.setInt("o_documento", 0);
            callableStatement.registerOutParameter("o_documento", java.sql.Types.SMALLINT);  // Parámetro de salida

            // Ejecutar el stored procedure
            callableStatement.execute();
            
            // Obtener el valor de retorno
            int returnValue = callableStatement.getInt(1);      

            //Obtener paametros de salida
            String sMsgErr = callableStatement.getString("o_msgerr");
            if (!(sMsgErr == null)) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, sMsgErr, "Movimientos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            txtDocumento.setText(String.valueOf(callableStatement.getInt("o_documento")));
            
            JOptionPane.showMessageDialog(null, "Movimiento grabado correctamente con Secuencia Nro. " + txtDocumento.getText(), "Movimientos", JOptionPane.INFORMATION_MESSAGE);
            
            //pLimpiar();
            
            tbGrabar.setEnabled(false);
            tbEliminar.setEnabled(true);
            tbAgregar.setEnabled(false);
            tbQuitar.setEnabled(false);
            
            txtDocumento.setEnabled(false);
            
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "Movimientos", JOptionPane.ERROR_MESSAGE);
            } catch (JsonProcessingException ex) {  
            Logger.getLogger(frmMovimientos.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }//GEN-LAST:event_tbGrabarActionPerformed

    private void tbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbLimpiarActionPerformed
        pLimpiar("S");
    }//GEN-LAST:event_tbLimpiarActionPerformed

    private void tbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAgregarActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();
        tableModel.addRow(new Object[]{"", 0.0, 0.0, 0.0, 15.0, 0.0, 0.0});
    }//GEN-LAST:event_tbAgregarActionPerformed

    private void tbQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbQuitarActionPerformed
        int selectedRow = grdDatos.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();
            tableModel.removeRow(selectedRow);
        }        // TODO add your handling code here:
        updateTotalGeneral();
    }//GEN-LAST:event_tbQuitarActionPerformed

    private void tbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbEliminarActionPerformed
        // TODO add your handling code here:
        Connection conn = null;

        try
        {
            sAccion = "E";
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            conn = DriverManager.getConnection(dbURL);
            conn.setAutoCommit(false); //XXX
            CallableStatement callableStatement = null;
            Statement statement = null;

            statement = conn.createStatement();
            statement.execute("USE dbVentas");

            String sql = "{call sp_vt_grabadocumento (?, ?, ?, ?, ?)}";
            callableStatement = conn.prepareCall(sql);

            // Establecer los parámetros de entrada y salida
            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);  // Parámetro de retorno
            callableStatement.registerOutParameter("o_msgerr", java.sql.Types.VARCHAR);  // Parámetro de salida
            callableStatement.setString("i_opc", sAccion);
            callableStatement.setString("i_usuario", lblUsuario.getText());
            callableStatement.setString("i_Json", null);
            callableStatement.setInt("o_documento", Integer.parseInt(txtDocumento.getText()));
            callableStatement.registerOutParameter("o_documento", java.sql.Types.SMALLINT);  // Parámetro de salida

            // Ejecutar el stored procedure
            callableStatement.execute();

            // Obtener el valor de retorno
            int returnValue = callableStatement.getInt(1);      

            //Obtener paametros de salida
            String sMsgErr = callableStatement.getString("o_msgerr");
            if (!(sMsgErr == null)) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, sMsgErr, "Movimientos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            txtDocumento.setText(String.valueOf(callableStatement.getInt("o_documento")));
            JOptionPane.showMessageDialog(null, "Movimiento eliminado correctamente.", "Movimientos", JOptionPane.INFORMATION_MESSAGE);

            pLimpiar("S");

            tbGrabar.setEnabled(false); 
            tbAgregar.setEnabled(false);
            tbQuitar.setEnabled(false);       
            conn.commit();  //XXX
        
        }
        catch (SQLException e) 
            {
                if (conn != null) {
                    try {
                        conn.rollback();    
                    } catch (SQLException ex) {
                    }
                }                
                JOptionPane.showMessageDialog(null, e.getMessage(), "Movimientos", JOptionPane.ERROR_MESSAGE);
            }          
        
    }//GEN-LAST:event_tbEliminarActionPerformed

    private void pLimpiar(String opcion){
        String sFecha = "";
        if (opcion.equals("S")) {
            Date objDate = new Date(); // Current System Date and time is assigned to objDate
            String strDateFormat = "yyyy/MM/dd"; //Date format is Specified
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
            sFecha = objSDF.format(objDate);
            txtDocumento.setText("");
            txtDocumento.setEnabled(true);
        }
        cmbTipoMovimiento.setSelectedIndex(-1);
        lblFecha.setText(sFecha);
        txtDescripcion.setText("");
        txtSubtotal.setText("");
        TxtIva15.setText("");
        txtTotal.setText("");        
        DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();
        tableModel.setRowCount(0);
        sAccion = "N";    
        tbGrabar.setEnabled(false);
        tbEliminar.setEnabled(false);
        
        tbAgregar.setEnabled(false);
        tbQuitar.setEnabled(false);       
        initCustomComponents();
        
    }
    
    private void pConsultar() {
        try
        {
            pLimpiar("N");
            sAccion = "C";
            String dbURL = mainForm.VP_URLConexion; //"jdbc:sqlserver://ba0100069x\\SQLEXPRESS;database=dbVentas;user=sa;password=84+-blaster32;encrypt=true;trustServerCertificate=true;ssl=require;Connect Timeout=0";
            Connection conn = null;
            conn = DriverManager.getConnection(dbURL);
            String sql = "{call sp_vt_grabadocumento (?, ?, ?, ?, ?)}";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, sAccion);
            ps.setString(3, "");
            ps.setString(4, null);
            ps.setInt(5, Integer.parseInt(txtDocumento.getText()));
            ResultSet rs = ps.executeQuery();
            
            if (rs.isBeforeFirst()) {
                while(rs.next()) {
                    lblUsuario.setText(rs.getString("ct_usuario"));
                    int iIndex = -1;
                    for (int i = 0; i < cmbTipoMovimiento.getItemCount(); i++) {
                        if (Character.toString(cmbTipoMovimiento.getItemAt(i).charAt(0)).equals(rs.getString("ct_tipomov"))) {
                         iIndex = i;
                         break;
                        }   
                    }
                    cmbTipoMovimiento.setSelectedIndex(iIndex);                    
                    lblFecha.setText(rs.getString("ct_fecha"));                    
                    txtDescripcion.setText(rs.getString("ct_observacion"));
                    txtSubtotal.setText((Double.valueOf(rs.getString("ct_subtotal"))).toString());
                    TxtIva15.setText((Double.valueOf(rs.getString("ct_iva"))).toString());
                    txtTotal.setText((Double.valueOf(rs.getString("ct_total"))).toString());
                    
                    DecimalFormat formato = new DecimalFormat("#,##0.00");
                    txtSubtotal.setText(formato.format(((Number) Double.valueOf(rs.getString("ct_subtotal"))).doubleValue()));
                    TxtIva15.setText(formato.format(((Number) Double.valueOf(rs.getString("ct_iva"))).doubleValue()));
                    txtTotal.setText(formato.format(((Number) Double.valueOf(rs.getString("ct_total"))).doubleValue()));
                    
                }
                
                if (ps.getMoreResults()) {
                    rs = ps.getResultSet();
                    while(rs.next()) {
                        DefaultTableModel tableModel = (DefaultTableModel) grdDatos.getModel();
                        tableModel.addRow(new Object[]{rs.getString("dt_producto"), Double.valueOf(rs.getString("dt_cantidad")), Double.valueOf(rs.getString("dt_valor")), Double.valueOf(rs.getString("dt_subtotal")), Double.valueOf(rs.getString("dt_porcentajeIva")), Double.valueOf(rs.getString("dt_valoriva")), Double.valueOf(rs.getString("dt_total"))});
                    }                    
                }
                disableEditingAfterLoad();
            }
            else {
                JOptionPane.showMessageDialog(null, "Documento a consultar no existe. O se encuentra eliminado", "Movimientos", JOptionPane.INFORMATION_MESSAGE);
                pLimpiar("S");
            }
            
            ps.close();
            rs.close();
            conn.close();
            tbGrabar.setEnabled(false);     
            tbEliminar.setEnabled(true);     
            tbAgregar.setEnabled(false);      
            tbQuitar.setEnabled(false);      
            
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Movimientos", JOptionPane.ERROR_MESSAGE);
        }        

    }
    
    // Este método se llama después de cargar los datos en la tabla
    public void disableEditingAfterLoad() {
        // Obtener el modelo actual de la tabla
        TableModel model = grdDatos.getModel();

        // Crear un nuevo modelo no editable basado en los datos existentes
        NonEditableTableModel nonEditableModel = new NonEditableTableModel(
            getDataFromTableModel(model),
            getColumnNamesFromTableModel(model)
        );

        // Establecer el nuevo modelo en la tabla
        grdDatos.setModel(nonEditableModel);
    }    
    
    private Object[][] getDataFromTableModel(TableModel model) {
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                data[i][j] = model.getValueAt(i, j);
            }
        }
        return data;
    }

    private Object[] getColumnNamesFromTableModel(TableModel model) {
        int columnCount = model.getColumnCount();
        Object[] columnNames = new Object[columnCount];

        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = model.getColumnName(i);
        }
        return columnNames;
    }    
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
            java.util.logging.Logger.getLogger(frmMovimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMovimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMovimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMovimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmMovimientos dialog = new frmMovimientos(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtIva15;
    private javax.swing.JComboBox<String> cmbTipoMovimiento;
    private javax.swing.JTable grdDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JToggleButton tbAgregar;
    private javax.swing.JToggleButton tbConsultar;
    private javax.swing.JToggleButton tbEliminar;
    private javax.swing.JToggleButton tbGrabar;
    private javax.swing.JToggleButton tbLimpiar;
    private javax.swing.JToggleButton tbNuevo;
    private javax.swing.JToggleButton tbQuitar;
    private javax.swing.JToggleButton tbSalir;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

/*CLASES ADIICONALES DE EXTENSION*/
    
class QuantityEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField;
    
    public QuantityEditor() {
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setText("");
    }
    
    @Override
    public Object getCellEditorValue() {
        try {
            return Double.valueOf(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText(value != null ? (value.toString() != "0.0" ? value.toString() : "") : "");
        return textField;
    }
}

class QuantityRenderer extends DefaultTableCellRenderer {
        public QuantityRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        protected void setValue(Object value) {
            if (value != null) {
                setText(String.valueOf(value));
            } else {
                setText("");
            }

        }    

    }

class DecimalFormatRenderer extends DefaultTableCellRenderer {
    private final DecimalFormat formato = new DecimalFormat("#,##0.00");

    public DecimalFormatRenderer() {
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
        @Override
        protected void setValue(Object value) {
            if (value != null) {
                setText(formato.format(((Number) value).doubleValue()));
            } else {
                setText("");
            }

        }      
    
}

public class NonEditableTableModel extends DefaultTableModel {

    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Retornar false para hacer que todas las celdas no sean editables
        return false;
    }
}

class NumericDocumentFilter extends DocumentFilter {
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches("\\d*")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches("\\d*")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }
}

}
