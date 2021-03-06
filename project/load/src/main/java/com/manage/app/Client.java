package com.manage.app;

import java.io.*;
import java.net.Socket;


import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JTextField tf1, tf2, tf3;
    JLabel jl1;
    JButton b1,b2,b3;
    JFrame f;
    JTable j;
    static Socket theSocket;
    static BufferedReader theInputStream,theInputStream2;
    static PrintStream theOutputStream,theOutputStream2;
    static String rec = null;

    Client() {

        f = new JFrame();
        tf1 = new JTextField();
        tf1.setBounds(50, 150, 150, 20);
        tf2 = new JTextField();
        tf2.setBounds(50, 200, 150, 20);
        tf3 = new JTextField();
        tf3.setBounds(50, 250, 150, 20);

        b1 = new JButton("Write");
        b1.setBounds(50, 300, 100, 50);
        b2 = new JButton("Exit");
        b2.setBounds(170, 300, 100, 50);
    
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        String[] rows= rec.split(":");
        String[][] data=new String[rows.length][3];
        int ind=0;
        for(ind=0;ind<rows.length;ind++){
            data[ind]=rows[ind].split(";");
        }

        String[] columnNames = { "Name", "Roll Number", "Age" };
         j = new JTable(data, columnNames);
         j.setBounds(100, 350, 300, 300);

        JScrollPane sp = new JScrollPane(j);
        f.add(tf1);
        f.add(tf2);
        f.add(tf3);
        f.add(b1);
        f.add(b2);
        f.add(sp);
        f.setSize(700, 700);
        f.setVisible(true);
    }
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        theSocket = null;
        theInputStream = null;
        theOutputStream = null;
        theSocket = new Socket("127.0.0.1", 6000);
        theOutputStream = new PrintStream(theSocket.getOutputStream());
        theInputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
        theOutputStream.println("read");
        rec = theInputStream.readLine(); 
        new Client();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        String argument="";
        if(e.getSource()==b1){
            String argument1=tf1.getText();
            String argument2=tf2.getText();
            String argument3=tf3.getText();
            argument = argument1+";"+argument2+";"+argument3;
            try{
                theSocket = new Socket("127.0.0.1", 6000);
                theOutputStream = new PrintStream(theSocket.getOutputStream());
                theInputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
                theOutputStream.println(argument);
                rec = theInputStream.readLine();    
            }
            catch(Exception e1){
               
            }
            try{
                theSocket = new Socket("127.0.0.1", 6000);
                theOutputStream = new PrintStream(theSocket.getOutputStream());
                theInputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
                theOutputStream.println("read");
                rec = theInputStream.readLine(); 
            }
            catch(Exception e2)
            {
    
            }
        }else if(e.getSource()==b2){
            
        }
        
        try {
            if (theSocket != null) {
                theSocket.close();
            }
            if (theInputStream != null) {
                theInputStream.close();
            }

            if (theOutputStream != null) {
                theOutputStream.close();
            }

        } catch (IOException e1) {
        }
        
        f.dispose();
        if(e.getSource()!=b2)
        new Client();
    }

}
