/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import static editorimag.EditorImag.gestor_img;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author jonay
 */
public class Diferencia_dos_imagenes {

    private BufferedImage img1;
    private BufferedImage img2;

    public Diferencia_dos_imagenes(BufferedImage tmp1) {
        img1 = tmp1;
//        img2 = tmp2;
        
        elegirImagenDos();
        
//        if(comprobarDimensiones()){
//            
//        }
//        else{
//            ventanaError();
//        }
        guardarImg1();
        guardarImg2();
    }

    private boolean comprobarDimensiones() {
        if ((img1.getWidth() == img2.getWidth()) && (img1.getHeight() == img2.getHeight())) {
            return true;
        }
        else{
            return false;
        }
    }
    
    private void ventanaError(){
        final JFrame error = new JFrame();
        error.setTitle("Error");
        error.setSize(300, 150);
        error.setResizable(false);
        error.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        error.add(panel);
        
        JLabel Error = new JLabel("Las dimensiones de las imágenes deben ser iguales");
        JButton Aceptar = new JButton("Aceptar");

        Aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                error.dispose();
            }
        });

        panel.add(Error);
        panel.add(Aceptar);

        error.setVisible(true);
    }
    
    private void elegirImagenDos(){
        final JFrame elegir = new JFrame();
        elegir.setTitle("Elige imagen para comparar");
        elegir.setSize(300, 150);
        elegir.setResizable(false);
        elegir.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        elegir.add(panel);
        
        JLabel Imagen = new JLabel("Elige la segunda imágen:");
        final JSpinner Dat_imagen = new JSpinner();
        JButton Aceptar = new JButton("Aceptar");
        
        Aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int i = (int) Dat_imagen.getValue();
                elegir.dispose();
                img2 = gestor_img.getBufferedImage(i);
            }
        });

        panel.add(Imagen);
        panel.add(Dat_imagen);
        panel.add(Aceptar);

        elegir.setVisible(true);
    }
    
    private void guardarImg1(){
        BufferedImage r1 = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        for(int i = 0; i < img1.getWidth(); i++){
            for(int j = 0; j < img1.getHeight(); j++){
                r1.setRGB(i, j, img1.getRGB(i, j));
            }
        }
        gestor_img.anadirImagen(r1);
    }
    
    private void guardarImg2(){
        BufferedImage r2 = new BufferedImage(img2.getWidth(), img2.getHeight(), img2.getType());
        for(int i = 0; i < img2.getWidth(); i++){
            for(int j = 0; j < img2.getHeight(); j++){
                r2.setRGB(i, j, img2.getRGB(i, j));
            }
        }
        gestor_img.anadirImagen(r2);
    }

}
