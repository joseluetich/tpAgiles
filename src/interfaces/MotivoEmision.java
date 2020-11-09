package src.interfaces;

import javax.swing.*;

public class MotivoEmision extends JFrame {
    private JPanel motivoEmisionPanel;
    private JRadioButton extravíoRadioButton;
    private JRadioButton roboRadioButton;
    private JRadioButton deterioroRadioButton;
    private JButton emitirCopiaButton;
    private JButton atrásButton;

    public MotivoEmision() {
        add(motivoEmisionPanel);
        setTitle("Motivos de Emisión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(550, 350);
    }
}
