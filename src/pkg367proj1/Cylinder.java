/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg367proj1;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.vecmath.Matrix4f;

/**
 *
 * @author warnecam
 */
public class Cylinder {
    private int listID;
    private Matrix4f cf;
    public Cylinder(GL2 gl, float r, float h, Triple<Float> color){
        cf = new Matrix4f();
        cf.setIdentity();
        
        listID = gl.glGenLists(1);
        gl.glNewList(listID, GL2.GL_COMPILE);        
        GLU glu = new GLU();
        GLUquadric cyl = glu.gluNewQuadric();
        glu.gluQuadricTexture(cyl, true);
        glu.gluCylinder(cyl, r, r, h, 20, 20);
        gl.glEnd();
        gl.glEndList();
    }
    public Matrix4f getCFMat(){
        return cf;
    }
    
    public float[] getCFf(){
        float[] mat = new float[16];
        for(int i =0; i < 4; i++){
            float[] row = new float[4];
            cf.getRow(i, row);
            for(int j = 0; j<4; j++){
                mat[(i*4)+j] = row[j];
            }
        }
        return mat;
    }
    public void draw(GL2 gl){
        gl.glPushMatrix();
        gl.glMultMatrixf(getCFf(), 0);
        gl.glCallList(listID); 
        gl.glPopMatrix();
    }
}