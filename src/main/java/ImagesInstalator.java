import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ImagesInstalator {
    final static boolean shouldFill = true;
    //final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    final private DefaultListModel<String> imagenes = new DefaultListModel<String>();
    private JButton descargar;
    private JTextField entraurl;
    private JLabel textimagenes;
    private JLabel texturl;
    private JScrollPane scrollPane;
    private JList listaurl;
    private static final String folderPath = "imagenes";

    public void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady =50;
            c.insets = new Insets(50,20,0,20);
        }
        texturl = new JLabel("URL");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(texturl, c);

        entraurl = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        pane.add(entraurl, c);

        descargar = new JButton("Descargar");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 4;
        c.ipady =20;
        pane.add(descargar, c);

        textimagenes = new JLabel("Imagenes");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        pane.add(textimagenes,c);

        textimagenes = new JLabel(" ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(textimagenes,c);

        scrollPane = new JScrollPane();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 3;
        c.ipady =150;

        pane.add(scrollPane,c);

        listaurl = new JList(imagenes);
        scrollPane.add(listaurl);
    }

    private Document conectarlink() {
        Document doc = null;
        try {
            doc = Jsoup.connect(entraurl.getText()).get();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private void comprovarcarpeta(){
        File dir = new File(folderPath);

        // if the directory does not exist, create it
        if (!dir.exists()) {
            System.out.println("creando directorio: " + dir.getName());
            boolean result = false;

            try{
                dir.mkdir();
                result = true;
            } catch(SecurityException se){

            }
            if(result) {
                System.out.println("DIR creado");
            }
        }

    }

    public void getimagenes(String link, String nombrearchivo) throws Exception {
        URL url = new URL(link);
        String destino = folderPath + "/"+nombrearchivo;
        InputStream entrada = url.openStream();
        FileOutputStream salida = new FileOutputStream(destino);
        byte datos[]= new byte[1024];
        int leido = entrada.read(datos);
        while (leido > 0) {
            salida.write(datos,0,leido);
            leido = entrada.read(datos);
        }
        entrada.close();
        salida.close();
    }

    private class buscarURLs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (entraurl.getText().equals("")) {
                entraurl.setText("sin url");//mostrar dialeg
            } else {
                imagenes.clear();
                Document doc = conectarlink();
                Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
                comprovarcarpeta();
                for (Element image : images) {
                    imagenes.addElement(image.attr("src"));
                    String src = image.absUrl("src");
                    System.out.println("src : " + image.attr("src"));

                    String nombreimagen=src.substring(src.lastIndexOf('/')+1);
                    try {
                        getimagenes(src,nombreimagen);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                listaurl.setModel(imagenes);
                scrollPane.setViewportView(listaurl);
            }
        }
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Descargador Imagenes ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        descargar.addActionListener(new buscarURLs());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(700,500);
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanopantalla = miPantalla.getScreenSize();
        int alturapantalla = tamanopantalla.height;
        int anchurapantalla = tamanopantalla.width;
        frame.setLocation(anchurapantalla /4, alturapantalla /6);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImagesInstalator().createAndShowGUI();
            }
        });
    }
}
