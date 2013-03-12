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
public class Cylinder extends SceneObj{
    private int listID;
    private int move;
    private boolean increasing = true;
    public boolean moving = true;
    public Cylinder(GL2 gl, float r, float h, Triple<Float> color){
        cf = new Matrix4f();
        cf.setIdentity();
        
        listID = gl.glGenLists(1);
        gl.glNewList(listID, GL2.GL_COMPILE); 
        gl.glColor3f(color.R(),color.G(),color.B());
        
        //create top and bottom
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glNormal3f(0f, 0f, -1f);
        gl.glVertex3f(0, 0, 0);
        for (int i = 0; i <= 20; i++) {
            if(i==0){
                gl.glColor3f(0f,0f,0f);
            }
            if(i==1){
                gl.glColor3f(color.R(),color.G(),color.B());
            }
            int angleFactor = -2;//need this to draw the bottom in a CC rotation so that it is facing out
            double theta = i / 20.0 * angleFactor * Math.PI;
            float cosine = (float) Math.cos(theta);
            float sine = (float) Math.sin(theta);
            float x = r * cosine;
            float y = r * sine;

            gl.glVertex3f(x, y, 0);
        }
        gl.glNormal3f(0f, 0f, 1f);
        gl.glVertex3f(0, 0, h);
        for (int i = 0; i <= 20; i++) {
            int angleFactor = 2;
            if (i == 0) {
                gl.glColor3f(0f, 0f, 0f);
            }
            if (i == 1) {
                gl.glColor3f(color.R(), color.G(), color.B());
            }
            double theta = i / 20.0 * angleFactor * Math.PI;
            float cosine = (float) Math.cos(theta);
            float sine = (float) Math.sin(theta);
            float x = r * cosine;
            float y = r * sine;

            gl.glVertex3f(x, y, h);
        }
        
        gl.glEnd();
        GLU glu = new GLU();
        GLUquadric cyl = glu.gluNewQuadric();
        glu.gluQuadricTexture(cyl, true);
        glu.gluCylinder(cyl, r, r, h, 20, 20);
        gl.glEnd();
        gl.glEndList();
    }
    public void draw(GL2 gl){
        gl.glPushMatrix();
        gl.glMultMatrixf(getCFf(), 0);
        if(moving){
        this.localTranslate(gl,new Triple<Float>((float)move, 0f, 0f));
        if(move == 10 || move == -10){
            increasing = !increasing;
        }
        if(increasing){
            move++;
        }else{
            move--;
        }}
        gl.glCallList(listID); 
        gl.glPopMatrix();
    }
}
