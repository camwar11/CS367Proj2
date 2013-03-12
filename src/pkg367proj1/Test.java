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
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
/**
 *
 * @author warnecam
 */
public class Test {
        static Cylinder cyl;
        static boolean light0=true;
        static boolean light1=true;
        static Teapots teapots;
        public static void main( String [] args ) {
        teapots = new Teapots();
        
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
        Cake.glcanvas = glcanvas;
        glcanvas.addKeyListener(new KeyListener(){

                @Override
                public void keyTyped(KeyEvent e) {
                    char key = e.getKeyChar();
                    System.out.println("Typed: "+key);
                    if(key == 'n'){
                        Cake.sceneNum = (Cake.sceneNum + 1) % 4;
                        System.out.println("Next Scene: "+Cake.sceneNum);
                    }else if(key == 'p'){
                        if(Cake.sceneNum == 0)
                            Cake.sceneNum = 4;
                        Cake.sceneNum = (Cake.sceneNum - 1) % 4;
                        System.out.println("Prev Scene: "+Cake.sceneNum);
                    }else if(key == 'w'){
                        Cake.fillMode = GL2.GL_LINE;
                    }else if(key == 'f'){
                        Cake.fillMode = GL2.GL_FILL;
                    }else if(key == 'k'){
                        light0=true;
                    }else if(key == 'l'){
                       light0=false;
                    }else if(key == 'h'){
                        light1=true;
                    }else if(key == 'j'){
                       light1=false;
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
        glcanvas.addGLEventListener( new GLEventListener() {
            
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                Cake.setup( glautodrawable.getGL().getGL2(), width, height );
                cyl.draw(glautodrawable.getGL().getGL2());
                teapots.draw(glautodrawable.getGL().getGL2());
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
                GL2 gl2 = glautodrawable.getGL().getGL2();
                Cake.init(gl2);
                //CubeSide2.init(glautodrawable.getGL().getGL2());
                cyl = new Cylinder(gl2, 50, 4, new Triple(1f,0.4f,0.4f));
                cyl.draw(gl2);
                teapots.add(new Teapot(10.0, new Triple(0f,.5f,0.8f)));
                teapots.get(0).rotateZ(20f);
                teapots.get(0).localTranslate(gl2, new Triple<Float>(0f,35f,2f));
                Teapot.initMaterial(gl2);
                initLights(gl2);
                teapots.draw(gl2);
            }
            
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                if (light0 == false) {
                    glautodrawable.getGL().getGL2().glDisable(GL2.GL_LIGHT0);
                }
                if (light1 == false) {
                    glautodrawable.getGL().getGL2().glDisable(GL2.GL_LIGHT1);
                }
                if (light0 == true) {
                    glautodrawable.getGL().getGL2().glEnable(GL2.GL_LIGHT0);
                }
                if (light1 == true) {
                    glautodrawable.getGL().getGL2().glEnable(GL2.GL_LIGHT1);
                }
                Cake.render( glautodrawable.getGL().getGL2());
                //CubeSide2.render( glautodrawable.getGL().getGL2());
                cyl.draw(glautodrawable.getGL().getGL2());
                teapots.draw(glautodrawable.getGL().getGL2());
            }
        });

        Frame frame = new Frame( "Portal Scene" );
        frame.add( glcanvas );
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                System.exit( 0 );
            }
        });

        frame.setSize( 640, 480 );
        frame.setVisible( true );
    }
        
    public static void initLights(GL2 gl) {
        /* define light properties */
        float light0_diffuse[] = {1.0f, 0.0f, 0.0f, 1.0f};
        float light0_position[] = {-1.0f, 0.0f, 1.0f, 0.0f};
        float light1_diffuse[] = {0.0f, 0.0f, 1.0f, 1.0f};
        float light1_position[] = {1.0f, 0.0f, 1.0f, 0.0f};

        /* enable lights 0 and 1*/
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);

        /* load light properties */
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, light1_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1_position, 0);
    }

}
