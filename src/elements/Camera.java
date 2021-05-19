package elements;

import primitives.*;
import geometries.*;

public class Camera {

	Point3D p0;
	Vector vUp;
	Vector vTo;
	Vector vRight;
	double width, height, distance;

	//constructor
	public Camera(Point3D p, Vector vt, Vector vp) {
		p0 = p;
		if(!Util.isZero(vp.dotProduct(vt)))//if they are not orthogonal
			throw new IllegalArgumentException("The vectors are not orthogonal");
		vUp = vp.normalize();
		vTo = vt.normalize();
		vRight = vp.crossProduct(vt).normalize().scale(-1);
	}
	
	//geters
	public Point3D getP0() { return p0; }
	public Vector getVUp() { return vUp; }
	public Vector getVTo() { return vTo; }
	public Vector getVRight() { return vRight; }
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	public double getDistance() { return distance; }


	//seters and check if the values are valid
	public Camera setVpSize(double w, double h)
	{
		if (Util.isZero(w) || Util.isZero(h)) throw new IllegalArgumentException("width or height cannot be 0");
		width = w;
		height = h;
		return this;
	}
	public Camera setVpDistance(double d)
	{
		if (Util.isZero(d)) throw new IllegalArgumentException("distance cannot be 0");
		distance = d;
		return this;
	}

	
	//make a ray to the center of the pixel we get 
	public Ray ConstructRayThroughPixel(int nX, int nY, int j, int i)
	{
		//calculate the center point
		Point3D p = getCenterOfPixel(nX, nY, j, i);
		
		//make ray from p0 to the center
	    Vector v = p.subtract(p0);
	    
		return new Ray(p0,v);
	}
	
	
	/*
	 * help for return the center:
	 * the func calculate the center of pixel and return it as Point3D
	 */
	public Point3D getCenterOfPixel(int nX, int nY, int j, int i)
	{
		//the center point of the center pixel
		Point3D Pc = p0.add(vTo.scale(distance));
		//width of pixel
		double Rx = width / nX;
		//height of pixel
		double Ry = height / nY;
		
		double tempY = ((i - nY / 2.0) * Ry + Ry/2.0);
		double tempX = ((j - nX / 2.0) * Rx + Rx/2.0);
		
		Point3D p = Pc;
		if(!Util.isZero(tempX))
		{
	    	 Vector Vx = vRight.scale(tempX);
	    	 p = p.add(Vx);
	    	 
	    	 
	    }
		if(!Util.isZero(tempY))
		{
	    	Vector Vy = vUp.scale(-tempY);
	    	p = p.add(Vy);
		}
		return p;
	}


}
