/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg367proj1;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author warnecam
 */
public class Test {

    static Cylinder cyl;
    static Teapots teapots;
    static GL2 myGL;
    
    public static void main(String[] args) {
        teapots = new Teapots();
        setupTeapots();

        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        final GLCanvas glcanvas = new GLCanvas(glcapabilities);
        Cake.glcanvas = glcanvas;
        glcanvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                System.out.println("Typed: " + key);
                if (key == 'n') {
                    Cake.sceneNum = (Cake.sceneNum + 1) % 4;
                    System.out.println("Next Scene: " + Cake.sceneNum);
                } else if (key == 'p') {
                    if (Cake.sceneNum == 0) {
                        Cake.sceneNum = 4;
                    }
                    Cake.sceneNum = (Cake.sceneNum - 1) % 4;
                    System.out.println("Prev Scene: " + Cake.sceneNum);
                } else if (key == 'f') {
                    if(Cake.fillMode==GL2.GL_FILL)
                        Cake.fillMode = GL2.GL_LINE;
                    else
                        Cake.fillMode = GL2.GL_FILL;
                } else if(key == 'w'){
                  cyl.localTranslate(1f);
                } else if(key == 'a'){
                  cyl.rotateX(1f);
                } else if(key == 's'){
                  cyl.localTranslate(myGL, new Triple<Float>(-1f,0f,0f));
                } else if(key == 'd'){
                  cyl.rotateX(-1f);
                } else if(key == 'q'){
                    Cake.rotating = !Cake.rotating; 
                } else if(key == 'e'){
                    cyl.moving = !cyl.moving;
                }
                
                glcanvas.display();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        glcanvas.addGLEventListener(new GLEventListener() {
            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                Cake.setup(glautodrawable.getGL().getGL2(), width, height);
                cyl.draw(glautodrawable.getGL().getGL2());
                teapots.draw(glautodrawable.getGL().getGL2());
            }

            @Override
            public void init(GLAutoDrawable glautodrawable) {
                GL2 gl2 = glautodrawable.getGL().getGL2();
                myGL = gl2;
                Cake.init(gl2);
                //CubeSide2.init(glautodrawable.getGL().getGL2());
                cyl = new Cylinder(gl2, 50, 4, new Triple(1f, 0.4f, 0.4f));
                cyl.draw(gl2);
                initTeapots(gl2);
                teapots.draw(gl2);
            }

            @Override
            public void dispose(GLAutoDrawable glautodrawable) {
            }

            @Override
            public void display(GLAutoDrawable glautodrawable) {
                Cake.render(glautodrawable.getGL().getGL2());
                //CubeSide2.render( glautodrawable.getGL().getGL2());
               // GLUT glut = 
                cyl.draw(glautodrawable.getGL().getGL2());
                teapots.draw(glautodrawable.getGL().getGL2());
            }
        });

        Frame frame = new Frame("Portal Scene");
        frame.add(glcanvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                System.exit(0);
            }
        });

        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    private static void setupTeapots() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++){
            teapots.add(new Teapot(10.0, new Triple(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
            
        }   
        
    }
    private static void initTeapots(GL2 gl2) {
        teapots.get(0).rotateZ(0f);
        teapots.get(0).globalTranslate(gl2, new Triple<Float>(0f, 35f, 2f));
        
        teapots.get(1).rotateZ(90f);
        teapots.get(1).globalTranslate(gl2, new Triple<Float>(0f, -35f, 2f));
        
        teapots.get(2).rotateZ(180f);
        teapots.get(2).globalTranslate(gl2, new Triple<Float>(35f, 0f, 2f));
        
        teapots.get(3).rotateZ(270f);
        teapots.get(3).globalTranslate(gl2, new Triple<Float>(-35f, 0f, 2f));
        
        
    }
}
