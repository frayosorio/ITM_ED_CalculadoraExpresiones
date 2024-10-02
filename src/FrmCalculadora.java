import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCalculadora extends JFrame {

    private JButton btnProcesar;
    private JButton btnEjecutar;
    private JLabel lblExpresion;
    private JTable tblVariables;
    private JTextField txtExpresion;
    private JTextField txtResultado;

    public FrmCalculadora() {
        lblExpresion = new JLabel();
        txtExpresion = new JTextField();
        tblVariables = new JTable();
        btnProcesar = new JButton();
        btnEjecutar = new JButton();
        txtResultado = new JTextField();

        setSize(600, 400);
        setTitle("Calculadora de Expresiones");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        lblExpresion.setText("Expresión aritmética:");
        lblExpresion.setBounds(10, 10, 200, 25);
        getContentPane().add(lblExpresion);

        txtExpresion.setBounds(210, 10, 200, 25);
        getContentPane().add(txtExpresion);
        
        btnProcesar.setText("Analizar Expresión");
        btnProcesar.setBounds(10, 50, 150, 25);
        getContentPane().add(btnProcesar);

        tblVariables = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(null, new String[]{});
        tblVariables.setModel(dtm);
        JScrollPane sp = new JScrollPane(tblVariables);
        sp.setBounds(200, 50, 300, 250);
        getContentPane().add(sp);

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.setBounds(10, 310, 150, 25);
        getContentPane().add(btnEjecutar);

        txtResultado.setBounds(210, 310, 150, 25);
        txtResultado.setEnabled(false);
        getContentPane().add(txtResultado);

        btnProcesar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnProcesarClick(evt);
            }
        });

        btnEjecutar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEjecutarClick(evt);
            }
        });
    }

    private void btnProcesarClick(ActionEvent evt) {

    }

    private void btnEjecutarClick(ActionEvent evt) {

    }

}
