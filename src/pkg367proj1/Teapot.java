/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg367proj1;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Matrix4f;

/**
 *
 * @author warnecam
 */
public class Teapot extends SceneObj{

    public Teapot(double size, Triple<Float> color){
        this.size = size;
        cf = new Matrix4f();
        cf.setIdentity();
        this.color = color;
        
    }
    
    @Override
    public void draw(GL2 gl){
        gl.glPushMatrix();
        gl.glMultMatrixf(Test.cyl.getCFf(), 0);
        gl.glMultMatrixf(this.getCFf(), 0);
        GLUT glut = new GLUT();
        gl.glColor3f(color.R(), color.G(), color.B());
        glut.glutSolidTeapot(size,false);
        gl.glPopMatrix();
    }
    
}
