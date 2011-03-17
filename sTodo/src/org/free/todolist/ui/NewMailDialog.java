package org.free.todolist.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.free.todolist.data.SimpleTextMail;
import org.free.todolist.mail.MailSender;
import org.free.todolist.model.TodoItem;

public class NewMailDialog extends JDialog {

	private static final long serialVersionUID = 4560359832031644448L;
	private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JEditorPane epContent;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labCC;
    private javax.swing.JLabel labContent;
    private javax.swing.JLabel labInfor;
    private javax.swing.JLabel labSendTo;
    private javax.swing.JLabel labSubject;
    private javax.swing.JTextField tfCC;
    private javax.swing.JTextField tfSendTo;
    private javax.swing.JTextField tfSubject;

    private JFrame parent;
    private TodoItem item;
    
    public NewMailDialog(JFrame parent, String title, TodoItem item) {
        super(parent, title, true);
        this.parent = parent;
        this.item = item;
    	initComponents();
    }

    private void initComponents() {

        labSendTo = new javax.swing.JLabel();
        tfSendTo = new javax.swing.JTextField();
        labInfor = new javax.swing.JLabel();
        labCC = new javax.swing.JLabel();
        tfCC = new javax.swing.JTextField();
        labSubject = new javax.swing.JLabel();
        tfSubject = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        epContent = new javax.swing.JEditorPane();
        labContent = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setName("Form"); // NOI18N

        labSendTo.setText("Send to:"); // NOI18N
        labSendTo.setName("labSendTo"); // NOI18N

        tfSendTo.setText("kmustlinux@gmail.com"); // NOI18N
        tfSendTo.setName("tfSendTo"); // NOI18N

        labInfor.setFont(new Font("vendana", Font.ITALIC, 10)); // NOI18N
        labInfor.setForeground(new Color(255, 51, 51)); // NOI18N
        labInfor.setText("If you have more than one contact, separate them by ';'."); // NOI18N
        labInfor.setName("labInfor"); // NOI18N

        labCC.setText("CC to:"); // NOI18N
        labCC.setName("labCC"); // NOI18N

        tfCC.setText("kmustunix@gmail.com"); // NOI18N
        tfCC.setName("tfCC"); // NOI18N

        labSubject.setText("Subject:"); // NOI18N
        labSubject.setName("labSubject"); // NOI18N

        tfSubject.setText("This is a note"); // NOI18N
        tfSubject.setName("tfSubject"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        epContent.setName("epContent"); // NOI18N
        jScrollPane1.setViewportView(epContent);


        epContent.setText(wrapMail(item));
		
        labContent.setText("Content:"); // NOI18N
        labContent.setName("labContent"); // NOI18N

        btnSubmit.setIcon(new ImageIcon("imgs/submit.gif"));
        btnSubmit.setText("Submit"); // NOI18N
        btnSubmit.setName("btnSubmit"); // NOI18N

        btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SimpleTextMail stMail = new SimpleTextMail();
				stMail.setSendTo(tfSendTo.getText());
				stMail.setCcTo(tfCC.getText());
				stMail.setSubject(tfSubject.getText());
				stMail.setContent(wrapMail(item));
				
				MailSender sender = new MailSender(stMail);
				boolean st = sender.send();
				if(st){
					JOptionPane.showMessageDialog(
							NewMailDialog.this, "mail is sent", "mail is sent!!", JOptionPane.INFORMATION_MESSAGE);
					NewMailDialog.this.setVisible(false);
				}
				
			}
        });
        
        btnCancel.setIcon(new ImageIcon("imgs/cancel.gif"));
        btnCancel.setText("Cancel"); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        
        btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				NewMailDialog.this.setVisible(false);
			}
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labSendTo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labCC, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labSubject, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labContent, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(tfCC, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(tfSubject, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(tfSendTo, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(labInfor))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSubmit)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labSendTo)
                    .addComponent(tfSendTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(labInfor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labCC)
                    .addComponent(tfCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labSubject)
                    .addComponent(tfSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labContent)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSubmit))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    private String wrapMail(TodoItem item){
    	StringBuffer content = new StringBuffer();
    	
    	content.append("Hi, \nThis is a note, created on"+new java.util.Date()+"\n");
    	content.append("Description : "+item.getDesc()+"\n");
    	content.append("Timeout : "+item.getTimeout()+"\n");
    	content.append("Period : "+item.getPeriod()+"\n");
    	content.append("Note : "+item.getNote());
    	
    	return content.toString();
    }
    
    
    public static void main(String[] args){
    	JFrame frame = new JFrame();
    	
    	JButton btnShowMail = new JButton("new a mail");
    	btnShowMail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		    	NewMailDialog mailDlg = new NewMailDialog(null, "send mail", null);
		    	mailDlg.setVisible(true);		
			}
    	});
    	
    	frame.add(btnShowMail, BorderLayout.CENTER);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(200,200);
    	frame.setVisible(true);
    	
    }
}
