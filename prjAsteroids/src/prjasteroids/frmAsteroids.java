package prjasteroids;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class frmAsteroids extends javax.swing.JFrame implements KeyListener {

    public frmAsteroids() {
        initComponents();

        for (int i = 0; i < 10; i++) {
            disparo[i] = new Objeto(0, 0, 0, 0.0, 0.0, 0.0, 0.0, false);
        }

        addKeyListener(this);
    }

    int decimas;
    int posBKasteroids = 0;

    int aceleracionNave;

    double distanciaColisionNave;

    //double distanciaColisionDisparo[] = new double[10];
    double distanciaColisionDisparo;

    boolean colision = false;

    int explosionColumnas = -100;
    int explosionFilas = 0;

    boolean disparado = false;

    Objeto nave = new Objeto(400, 300, 0, 0.0, 0.0, 0.0, 0.0, false);

    Objeto asteroide = new Objeto(100, 100, 0, 0.0, 0.0, 0.0, 0.0, false);

    Objeto disparo[] = new Objeto[10];

    Timer temporizador = new Timer(1, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            //fondo
            posBKasteroids++;
            if (posBKasteroids == 640) {
                posBKasteroids = 0;
            }

            nave.setCoorX(nave.getCoorX() + (int) nave.getAccX());
            nave.setCoorY(nave.getCoorY() + (int) nave.getAccY());

            //frenar nave
            if (nave.getImpulso() == false) {
                aceleracionNave = 5;

                if (nave.getAccX() > 0) {
                    nave.setAccX(nave.getAccX() - 0.3);
                }
                if (nave.getAccX() < 0) {
                    nave.setAccX(nave.getAccX() + 0.3);
                }
                if (nave.getAccY() > 0) {
                    nave.setAccY(nave.getAccY() - 0.3);
                }
                if (nave.getAccY() < 0) {
                    nave.setAccY(nave.getAccY() + 0.3);
                }
            } //acelerar nave
            else {
                if (aceleracionNave < 10) {
                    aceleracionNave++;
                }
                nave.setAccX(Math.cos(Math.toRadians(nave.getAngle() % 360)) * aceleracionNave);
                nave.setAccY(Math.sin(Math.toRadians(nave.getAngle() % 360)) * aceleracionNave);
            }

                //Para que los objetos siempre este dentro de la pantalla
            ReaparecerPantalla(nave);
            ReaparecerPantalla(asteroide);

            for (int i = 0; i < 10; i++) {

                //ReaparecerPantalla(disparo[i]);
            }

                //Rotacion Asteroide
            asteroide.setAngle(asteroide.getAngle() + 10);

                //distancia de colision
            distanciaColisionNave = Math.sqrt(Math.pow((nave.getCoorX() - asteroide.getCoorX()), 2) + Math.pow((nave.getCoorY() - asteroide.getCoorY()), 2));

//            if (disparado == true) {
//                for (int i = 0; i < 10; i++) {
//                    distanciaColisionDisparo[i] = Math.sqrt(Math.pow((disparo[i].getCoorX() - asteroide.getCoorX()), 2) + Math.pow((disparo[i].getCoorY() - asteroide.getCoorY()), 2));
//
//                    if (distanciaColisionNave < 45.0 || (distanciaColisionDisparo[i] < 45.0)) {
//                        colision = true;
//                    }
//
//                }
//
//            }

            if (colision == true) {
                if (explosionColumnas == 900) {
                    explosionColumnas = 0;
                    explosionFilas += 100;
                }
                explosionColumnas += 100;
            } else {
                asteroide.setCoorX(asteroide.getCoorX() + 1);
                asteroide.setCoorY(asteroide.getCoorY() + 1);
            }

            for (int i = 0; i < 10; i++) {

                if (disparado == true) {
                    disparo[i].setAccX(Math.cos(Math.toRadians(disparo[i].getAngle() % 360)) * 20);
                    disparo[i].setAccY(Math.sin(Math.toRadians(disparo[i].getAngle() % 360)) * 20);

                    disparo[i].setCoorX(disparo[i].getCoorX() + (int) disparo[i].getAccX());
                    disparo[i].setCoorY(disparo[i].getCoorY() + (int) disparo[i].getAccY());
                }

            }

            //repaint
            decimas++;
            if (decimas == 50) {
                decimas = 0;

                disparado = false;
            }

            repaint();
        }

    });

    public void ReaparecerPantalla(Objeto obj) {
        if (obj.getCoorX() < 0) {
            obj.setCoorX(800);
        }
        if (obj.getCoorX() > 800) {
            obj.setCoorX(0);
        }
        if (obj.getCoorY() < 0) {
            obj.setCoorY(600);
        }
        if (obj.getCoorY() > 600) {
            obj.setCoorY(0);
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        //cuando se suelta, ya no hay impulso
        if (e.getKeyCode() == 38) {
            nave.setImpulso(false);
        }
    }

    public void keyPressed(KeyEvent e) {
        //Impulso 
        if (e.getKeyCode() == 38) {
            nave.setImpulso(true);
        }
        //RotarIzquierda
        if (e.getKeyCode() == 37) {
            nave.setAngle(nave.getAngle() - 15);

        }
        //RotarDerecha
        if (e.getKeyCode() == 39) {
            nave.setAngle(nave.getAngle() + 15);
        }

        //Disparo
        if (e.getKeyCode() == 32) {

            if (disparado == false) {
                for (int i = 0; i < 10; i++) {
                    disparo[i].setAngle(nave.getAngle());

                    disparo[i].setCoorX(nave.getCoorX() + 40);

                    disparo[i].setCoorY(nave.getCoorY() + 40);

                }
            }

            disparado = true;

        }

    }

    public void paint(Graphics g) {
        BufferedImage fondo = null;
        BufferedImage debris = null;
        BufferedImage nave1 = null;
        BufferedImage nave2 = null;
        BufferedImage asteroide1 = null;
        BufferedImage explosion = null;
        BufferedImage disparo1 = null;

        try {
            fondo = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\ProjectAsteroids\\src\\projectasteroids\\nebula_blue.png"));
            debris = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\ProjectAsteroids\\src\\projectasteroids\\debris2_blue.png"));
            nave1 = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\ProjectAsteroids\\src\\projectasteroids\\nave1.png"));
            nave2 = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\ProjectAsteroids\\src\\projectasteroids\\nave2.png"));
            asteroide1 = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\ProjectAsteroids\\src\\projectasteroids\\asteroid_blue.png"));
            explosion = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\prjAsteroids\\src\\prjasteroids\\explosion.hasgraphics.png"));
            disparo1 = ImageIO.read(new File("C:\\Users\\Mesa7\\Documents\\NetBeansProjects\\prjAsteroids\\src\\prjasteroids\\shot1.png"));
        } catch (IOException ex) {
            Logger.getLogger(frmAsteroids.class.getName()).log(Level.SEVERE, null, ex);
        }

        //super.paint(g);

        /*        g.drawImage(img, 10, 30,100,120,0,0,90,90,this);//sin llama
         g.drawImage(img, 100, 30,190,120,90,0,180,90,this);//con llama */
        g.drawImage(fondo, 0, 0, null);
        g.drawImage(debris, posBKasteroids, 100, null);
        g.drawImage(debris, posBKasteroids - 640, 100, null);

        //rotacion 
        Graphics2D g2 = (Graphics2D) g;

        /* AffineTransform t = new AffineTransform();
         t.rotate(45);
         g2.setTransform(t);
         */
       // g2.drawImage(img, 150, 30,240,120,90,0,180,90,this);//con llama
        //g2.drawImage(nave.img,45,100,45+nave.informacion.ancho, 100+nave.informacion.alto,90,0,180,90,this);//sin llama
//        Graphics2D g2=(Graphics2D) g;
        // Drawing the rotated image at the required drawing locations
        if (disparado) {
            for (int i = 0; i < 10; i++) {

                double rotationDisparo = Math.toRadians(disparo[i].getAngle());
                double locationXdisparo = disparo1.getWidth() / 2;
                double locationYdisparo = disparo1.getHeight() / 2;
                AffineTransform txDisparo = AffineTransform.getRotateInstance(rotationDisparo, locationXdisparo, locationYdisparo);
                AffineTransformOp opDisparo = new AffineTransformOp(txDisparo, AffineTransformOp.TYPE_BILINEAR);

                g2.drawImage(opDisparo.filter(disparo1, null), disparo[i].getCoorX(), disparo[i].getCoorY(), null);

            }
        }

        double rotationNave = Math.toRadians(nave.getAngle());
        double locationXnave = nave1.getWidth() / 2;
        double locationYnave = nave1.getHeight() / 2;
        AffineTransform txNave = AffineTransform.getRotateInstance(rotationNave, locationXnave, locationYnave);
        AffineTransformOp opNave = new AffineTransformOp(txNave, AffineTransformOp.TYPE_BILINEAR);
//        Graphics2D g2=(Graphics2D) g;
        // Drawing the rotated image at the required drawing locations
        if (nave.getImpulso() == false) {
            g2.drawImage(opNave.filter(nave1, null), nave.getCoorX(), nave.getCoorY(), null);
        } else {
            g2.drawImage(opNave.filter(nave2, null), nave.getCoorX(), nave.getCoorY(), null);
        }

        double rotationAsteroide = Math.toRadians(asteroide.getAngle());
        double locationXasteroide = asteroide1.getWidth() / 2;
        double locationYasteroide = asteroide1.getHeight() / 2;
        AffineTransform txAsteroide = AffineTransform.getRotateInstance(rotationAsteroide, locationXasteroide, locationYasteroide);
        AffineTransformOp opAsteroide = new AffineTransformOp(txAsteroide, AffineTransformOp.TYPE_BILINEAR);

        if (colision == false) {
            g2.drawImage(opAsteroide.filter(asteroide1, null), asteroide.getCoorX(), asteroide.getCoorY(), null);
        } else {
            g.drawImage(explosion, asteroide.getCoorX() - 15, asteroide.getCoorY() - 15, asteroide.getCoorX() + 115, asteroide.getCoorY() + 115, explosionColumnas, explosionFilas, explosionColumnas + 100, explosionFilas + 100, this);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        temporizador.start();
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(frmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAsteroids.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAsteroids().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
