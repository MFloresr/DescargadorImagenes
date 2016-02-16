import javax.swing.*;
import java.awt.*;

public class App {
    private JPanel DescargadorView;
    private JTextField url;
    private JList imagenes;
    private JButton descargar;
    private JLabel nombreurl;
    private JLabel listaImagenes;

    private void createUIComponents() {
        DescargadorView = new JPanel();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Descargador Imagenes");
        frame.setContentPane(new App().DescargadorView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        Toolkit miPantalla=Toolkit.getDefaultToolkit();
        Dimension tamanopantalla=miPantalla.getScreenSize();
        int alturapantalla=tamanopantalla.height;
        int anchurapantalla= tamanopantalla.width;
        frame.setSize(anchurapantalla/2,alturapantalla/2);
        frame.setLocation(anchurapantalla/4,alturapantalla/4);
    }
}
