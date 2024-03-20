
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class QLĐSV extends javax.swing.JFrame {

    ArrayList<grade> list = new ArrayList<>();
    DefaultTableModel model;
    private Connection conn;
    int index = 0;

    public QLĐSV() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        model = (DefaultTableModel) tblQLĐSV.getModel();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL_ĐT;user=sa;password=thanhtu99");
        } catch (Exception e) {
            System.out.println(e);
        }
        list = getListGrade();
        loadDbToTable();
        loadDataToCbo();
    }

    public void loadDataToCbo() {
        try {
            String sql = "SELECT MASV FROM STUDENTS";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cboMasv.addItem(rs.getString("MASV"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean selectItem() {
        try {
            String sql = "SELECT * FROM STUDENTS WHERE MASV = ?";
            String ma = (String) cboMasv.getSelectedItem();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txtId.setText(ma);
                lblName.setText(rs.getString("HOTEN"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<grade> getListGrade() {
        try {
            String sql = "SELECT TOP 3 STUDENTS.MASV,HOTEN,TIENGANH,TINHOC,GDTC,(TIENGANH+TINHOC+GDTC)/3 AS TBM FROM STUDENTS JOIN GRADE\n"
                    + " ON STUDENTS.MASV = GRADE.MASV ORDER BY TBM DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grade sv = new grade();
                sv.setMasv(rs.getString("MASV"));
                sv.setName(rs.getString("HOTEN"));
                sv.setAnh(Double.parseDouble(rs.getString("TIENGANH")));
                sv.setTin(Double.parseDouble(rs.getString("TINHOC")));
                sv.setGdtc(Double.parseDouble(rs.getString("GDTC")));
                sv.setAvg(Double.parseDouble(rs.getString("TBM")));
                list.add(sv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void fillTable() {
        model.setRowCount(0);
        for (grade sv : list) {
            sv.masv = txtId.getText();
            sv.name = lblName.getText();
            Object[] row = new Object[]{sv.masv, sv.name, sv.anh, sv.tin, sv.gdtc, sv.getAvg()};
            model.addRow(row);
        }
    }

    public void showDetail(int index) {
        lblName.setText(list.get(index).getName());
        txtId.setText(list.get(index).getMasv());
        txtEnglish.setText(String.valueOf(list.get(index).getAnh()));
        txtIt.setText(String.valueOf(list.get(index).getTin()));
        txtGdtc.setText(String.valueOf(list.get(index).getGdtc()));
        lblAvg.setText(String.valueOf(list.get(index).getAvg()));
    }

    public void loadDbToTable() {
        try {
            model.setRowCount(0);
            String sql = "SELECT TOP 3 STUDENTS.MASV,HOTEN,TIENGANH,TINHOC,GDTC,(TIENGANH+TINHOC+GDTC)/3 AS TBM FROM STUDENTS JOIN GRADE\n"
                    + " ON STUDENTS.MASV = GRADE.MASV ORDER BY TBM DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("MASV"));
                row.add(rs.getString("HOTEN"));
                row.add(Double.parseDouble(rs.getString("TIENGANH")));
                row.add(Double.parseDouble(rs.getString("TINHOC")));
                row.add(Double.parseDouble(rs.getString("GDTC")));
                row.add(Double.parseDouble(rs.getString("TBM")));
                model.addRow(row);
            }
            tblQLĐSV.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean deleteGrade() {
        try {
            String ma = txtId.getText();
            String sql = "DELETE FROM GRADE WHERE MASV = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateGrade() {

        try {
            String ma = txtId.getText();
            String sql = "UPDATE GRADE SET TIENGANH = ?, TINHOC = ?, GDTC = ? WHERE MASV = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtEnglish.getText());
            ps.setString(2, txtIt.getText());
            ps.setString(3, txtGdtc.getText());
            ps.setString(4, ma);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cboMasv = new javax.swing.JComboBox();
        btnSubmit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtEnglish = new javax.swing.JTextField();
        txtIt = new javax.swing.JTextField();
        txtGdtc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblAvg = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQLĐSV = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("Quản Lý Điểm Sinh Viên"), this, org.jdesktop.beansbinding.BeanProperty.create("title"));
        bindingGroup.addBinding(binding);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        cboMasv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mã sinh viên" }));

        btnSubmit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/submit.png"))); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(cboMasv, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSubmit)
                .addGap(101, 101, 101))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cboMasv, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Quản Lý Điểm Sinh Viên");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Họ tên SV:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Mã SV:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tiếng anh:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tin học:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Giáo dục TC:");

        txtId.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Điểm TB:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtId)
                    .addComponent(txtEnglish)
                    .addComponent(txtIt)
                    .addComponent(txtGdtc, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addComponent(lblAvg, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEnglish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtIt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtGdtc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblAvg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
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

        btnFirst.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\first.png")); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\back.png")); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\next.png")); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Desktop\\Icon\\last.png")); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        tblQLĐSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Tiếng anh", "Tin học", "GDTC", "Điểm TB"
            }
        ));
        tblQLĐSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLĐSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblQLĐSV);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("3 sinh viên có điểm cao nhất:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate))
                                .addGap(0, 11, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(11, 11, 11)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSave)
                        .addGap(13, 13, 13)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        cboMasv.setSelectedIndex(0);
        lblName.setText(null);
        txtId.setText(null);
        txtEnglish.setText(null);
        txtIt.setText(null);
        txtGdtc.setText(null);
        lblAvg.setText(null);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        grade sv = new grade();
        sv.name = lblName.getText();
        sv.anh = Double.parseDouble(txtEnglish.getText());
        sv.tin = Double.parseDouble(txtIt.getText());
        sv.gdtc = Double.parseDouble(txtGdtc.getText());
        lblAvg.setText(String.valueOf(sv.getAvg()));
        list.add(sv);
        try {
            String sql = "INSERT INTO GRADE(MASV, TIENGANH, TINHOC, GDTC) VALUES ( ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtId.getText());
            ps.setDouble(2, sv.anh);
            ps.setDouble(3, sv.tin);
            ps.setDouble(4, sv.gdtc);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Lưu thành công!");
        } catch (Exception e) {
            System.out.println(e);
        }
        fillTable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        index = tblQLĐSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng cần xóa");
        } else {
            this.deleteGrade();
            list.remove(index);
            fillTable();
            this.btnNewActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Đã xóa thành công!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        index = tblQLĐSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hàng cần cập nhật");
        } else {
            list.remove(index);
            this.updateGrade();
            grade sv = new grade();
            sv.anh = Double.parseDouble(txtEnglish.getText());
            sv.tin = Double.parseDouble(txtIt.getText());
            sv.gdtc = Double.parseDouble(txtGdtc.getText());
            lblAvg.setText(String.valueOf(sv.getAvg()));
            list.add(sv);
            fillTable();
            JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblQLĐSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLĐSVMouseClicked
        index = tblQLĐSV.getSelectedRow();
        this.showDetail(index);
    }//GEN-LAST:event_tblQLĐSVMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        showDetail(0);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        index--;
        if (index < 0) {
            index = 0;
        }
        showDetail(index);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        index++;
        if (index >= getListGrade().size()) {
            index = getListGrade().size() - 1;
        }
        showDetail(index);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        index = getListGrade().size() - 1;
        showDetail(index);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        this.selectItem();
    }//GEN-LAST:event_btnSubmitActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(QLĐSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLĐSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLĐSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLĐSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>     
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLĐSV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cboMasv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvg;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblQLĐSV;
    private javax.swing.JTextField txtEnglish;
    private javax.swing.JTextField txtGdtc;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIt;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
