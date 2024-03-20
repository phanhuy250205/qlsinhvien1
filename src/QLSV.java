
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class QLSV extends javax.swing.JFrame {

    ArrayList<student> list = new ArrayList<>();
    DefaultTableModel model;
    int index = 0;
    private Connection conn;
    String imageName = null;

    public QLSV() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL_ĐT;user=sa;password=thanhtu99");
        } catch (Exception e) {
            System.out.println(e);
        }
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        list = getListStudent();
        model = (DefaultTableModel) tblQLSV.getModel();
        loadDbToTable();
    }

    public void upImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\image\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), image.SCALE_SMOOTH));
        lblImage.setIcon(icon1);
    }

    public boolean check() {
        if (txtId.getText().equals("") || txtName.getText().equals("") || txtEmail.getText().equals("")
                || txtPhone.getText().equals("") || txtAddress.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save");
            return false;
        } else if (!(txtEmail.getText()).matches("\\w+@\\w+\\.\\w+\\.\\w+")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng email");
            txtEmail.requestFocus();
            return false;
        } else if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính");
            return false;
        }
        return true;
    }

    public void loadDbToTable() {
        try {
            model.setRowCount(0);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM STUDENTS");
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getBoolean(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                model.addRow(row);
            }
            tblQLSV.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillTable() {
        model.setRowCount(0);
        for (student s : list) {
            Object[] row = new Object[]{s.getMasv(), s.getName(), s.getEmail(), s.getPhone(), s.isGender(), s.getAddress(), s.getImage()};
            model.addRow(row);
        }
    }

    public void showDetail(int index) {
        txtId.setText(list.get(index).getMasv());
        txtName.setText(list.get(index).getName());
        txtEmail.setText(list.get(index).getEmail());
        txtPhone.setText(list.get(index).getPhone());
        if (list.get(index).isGender() == true) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtAddress.setText(list.get(index).getAddress());
        upImage(list.get(index).getImage());
    }

    public boolean deleteStudent() {
        try {
            String ma = txtId.getText();
            String sql = "DELETE FROM STUDENTS WHERE MASV = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateStudent() {
        try {
            String ma = txtId.getText();
            String sql = "UPDATE STUDENTS SET HOTEN = ?, EMAIL = ?, SODT = ?, GIOITINH = ?, DIACHI = ?, HINH = ? WHERE MASV = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            boolean gt;
            if (rdoNam.isSelected()) {
                gt = true;
            } else {
                gt = false;
            }
            ps.setBoolean(4, gt);
            ps.setString(5, txtAddress.getText());
            ps.setString(6, imageName);
            ps.setString(7, txtId.getText());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean saveStudent(student sv) {
        String sql = "INSERT INTO STUDENTS VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getMasv());
            ps.setString(2, sv.getName());
            ps.setString(3, sv.getEmail());
            ps.setString(4, sv.getPhone());
            ps.setBoolean(5, sv.isGender());
            ps.setString(6, sv.getAddress());
            ps.setString(7, imageName);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<student> getListStudent() {

        String sql = "SELECT * FROM STUDENTS";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                student sv = new student();
                sv.setMasv(rs.getString(1));
                sv.setName(rs.getString(2));
                sv.setEmail(rs.getString(3));
                sv.setPhone(rs.getString(4));
                sv.setGender(rs.getBoolean(5));
                sv.setAddress(rs.getString(6));
                sv.setImage(rs.getString(7));
                list.add(sv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQLSV = new javax.swing.JTable();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 51));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("Quản Lý Sinh Viên"), this, org.jdesktop.beansbinding.BeanProperty.create("title"));
        bindingGroup.addBinding(binding);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Quản Lý Sinh Viên");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã SV:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Họ Tên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số ĐT:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Giới Tính:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Địa Chỉ:");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );

        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\new.png")); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\save.png")); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\delete.png")); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\update.png")); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate))
                .addGap(21, 21, 21))
        );

        tblQLSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Email", "Số ĐT", "Giới tính", "Địa chỉ", "Hình"
            }
        ));
        tblQLSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLSVMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblQLSV);

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                        .addComponent(txtPhone)
                                        .addComponent(txtEmail)
                                        .addComponent(txtName)
                                        .addComponent(txtId))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoNu)))
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(jLabel1)))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtId.setText(null);
        txtName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtAddress.setText(null);
        buttonGroup1.clearSelection();
        lblImage.setIcon(null);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (check()) {
            student sv = new student();
            sv.setMasv(txtId.getText());
            sv.setName(txtName.getText());
            sv.setEmail(txtEmail.getText());
            sv.setPhone(txtPhone.getText());
            boolean gt;
            if (rdoNam.isSelected()) {
                gt = true;
            } else {
                gt = false;
            }
            sv.setGender(gt);
            sv.setAddress(txtAddress.getText());
            sv.setImage(imageName);
            if (saveStudent(sv)) {
                JOptionPane.showMessageDialog(rootPane, "Lưu thành công!");
                list.add(sv);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Lỗi");
            }
            fillTable();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblQLSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLSVMouseClicked
        try {
            index = tblQLSV.getSelectedRow();
            showDetail(index);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_tblQLSVMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        index = tblQLSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng để xóa!");
        } else {
            this.deleteStudent();
            list.remove(index);
            loadDbToTable();
            this.btnNewActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        index = tblQLSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng cần update!");
        } else {
            list.remove(index);
            this.updateStudent();
            student sv = new student();
            sv.setMasv(txtId.getText());
            sv.setName(txtName.getText());
            sv.setEmail(txtEmail.getText());
            sv.setPhone(txtPhone.getText());
            boolean gt;
            if (rdoNam.isSelected()) {
                gt = true;
            } else {
                gt = false;
            }
            sv.setGender(gt);
            sv.setAddress(txtAddress.getText());
            sv.setImage(imageName);
            list.add(sv);
            fillTable();
            JOptionPane.showMessageDialog(this, "Update thành công!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed

    }//GEN-LAST:event_btnImageActionPerformed

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        JFileChooser file = new JFileChooser("src\\image\\");
        int kq = file.showOpenDialog(file);
        if (kq == JFileChooser.APPROVE_OPTION) {
            imageName = file.getSelectedFile().getName();
            upImage(imageName);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn ảnh...");
        }
    }//GEN-LAST:event_lblImageMouseClicked

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
            java.util.logging.Logger.getLogger(QLSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLSV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblQLSV;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
