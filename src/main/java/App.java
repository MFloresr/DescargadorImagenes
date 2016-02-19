import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class App {
    private JTextField url;
    private JButton descargar;
    private JLabel nombreurl;
    private JLabel listaImagenes;
    private JList list1;
    private JPanel DescargadorView;
    private DefaultListModel imagenes = new DefaultListModel();


    private void createUIComponents() {
        url = new JTextField("");
        nombreurl= new JLabel("URL");
        listaImagenes= new JLabel("Lista Imagenes");
    }
    private void $$$setupUI$$$() {
        createUIComponents();
        //list1 =new JList();
        descargar.setText("Descargar");
    }

    public App(){
        descargar.addActionListener(new descargarimagenes());
    }

    private class descargarimagenes implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(url.getText().equals("")){
                url.setText("sin url");//mostrar dialeg
            }else{
                Document doc = null;
                try {
                    doc = Jsoup.connect(url.getText()).get();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                for (Element image : images) {
                    imagenes.addElement(image.attr("src"));
                    System.out.println("src : " + image.attr("src"));
                    //System.out.println("height : " + image.attr("height"));
                    //System.out.println("width : " + image.attr("width"));
                    //System.out.println("alt : " + image.attr("alt"));
                }
                list1.setModel(imagenes);
            }



            JScrollPane scrollPane = new JScrollPane();
            JList list = new JList();
            scrollPane.setViewportView(list);


        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora-Xpres 2.0");
        frame.setContentPane(new App().DescargadorView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        Toolkit miPantalla=Toolkit.getDefaultToolkit();
        Dimension tamanopantalla=miPantalla.getScreenSize();
        int alturapantalla=tamanopantalla.height;
        int anchurapantalla= tamanopantalla.width;

        frame.setLocation(anchurapantalla/4,alturapantalla/4);
    }
}
