/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjasteroids;

public class Objeto {
    
                int posX, posY;            
                int angulo;
                double velX, velY;
                double accX, accY;
                boolean impulso;
            

    public Objeto(int cX, int cY, int angle, double vX, double vY, double aX, double aY, boolean imp)
    {
        posX = cX;
        posY = cY;
        angulo = angle;
        velX = vX;
        velY = vY;
        accX = aX;
        accY = aY;
        impulso = imp;
    }
    
    public void setCoorX(int cX)
    {
        posX = cX;
    }
    
    public void setCoorY(int cY)
    {
        posY = cY;
    }
    
    public void setAngle(int angle)
    {
        angulo = angle;
    }
    
    public void setVelX(double vX)
    {
        velX = vX;
    }
    
    public void setVelY(double vY)
    {
        velY = vY;
    }
    
    public void setAccX(double aX)
    {
        accX = aX;
    }
    
    public void setAccY(double aY)
    {
        accY = aY;
    }
    
    public void setImpulso(boolean imp)
    {
        impulso = imp;
    }
    
    public int getCoorX()
    {
        return posX;
    }
    
    public int getCoorY()
    {
        return posY;
    }
    
    public int getAngle()
    {
        return angulo;
    }
    
    public double getVelX()
    {
        return velX;
    }
    
    public double getVelY()
    {
        return velY;
    }
    
    public double getAccX()
    {
        return accX;
    }
    
    public double getAccY()
    {
        return accY;
    }
    
    public boolean getImpulso()
    {
        return impulso;
    }
   
    
}
