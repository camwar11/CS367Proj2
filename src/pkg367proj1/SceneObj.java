/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg367proj1;

import javax.media.opengl.GL2;
import javax.vecmath.Matrix4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 *
 * @author warnecam
 */
public class SceneObj implements Drawable{
    protected Matrix4f cf;
    protected double size;
    protected Triple<Float> color;
    
    @Override
    public void draw(GL2 gl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void rotateX(float rot){
        Matrix4f rotMat = new Matrix4f();
        rotMat.rotX(rot);
        cf.mul(rotMat,cf);
    }
    public void rotateY(float rot){
        Matrix4f rotMat = new Matrix4f();
        rotMat.rotY(rot);
        cf.mul(rotMat,cf);
    }
    public void rotateZ(float rot){
        Matrix4f rotMat = new Matrix4f();
        rotMat.rotZ(rot);
        cf.mul(rotMat,cf);
    }
    
    void globalTranslate(GL2 gl2, Triple<Float> translation) {
        gl2.glPushMatrix();
        gl2.glLoadIdentity();
        gl2.glTranslatef(translation.X(), translation.Y(), translation.Z());

        float[] matrix = new float[16];
        gl2.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, matrix, 0);

        Matrix4f multMat = new Matrix4f(matrix);

        cf.mul(multMat);

        gl2.glPopMatrix();
    }
    void localTranslate(GL2 gl2, Triple<Float> translation) {
        double[] matrix = new double[16];
        float[] coords = this.getCFf();
        for(int i = 0; i < 16;i++){
            matrix[i] = Double.valueOf(coords[i]);
        }
        Cake.globalTranslate(gl2, matrix, translation);
        cf = new Matrix4f(new Matrix4d(matrix));
    }
    
    void localTranslate(float xDir) {
        Vector3f trans = new Vector3f(xDir, 0, 0);
        Matrix4f transMat = new Matrix4f();
        
        transMat.set(trans);
        cf.add(transMat);
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
    
}
