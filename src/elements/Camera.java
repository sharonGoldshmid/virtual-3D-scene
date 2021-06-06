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
		//calculate the center view
		Point3D p = getCenterOfPixel(nX, nY, j, i);
		
		//make ray from p0 to the center
		return new Ray(p0,p.subtract(p0));
	}
	
	
	/*
	 * help function for find center of pixel
	 */
	private Point3D getCenterOfPixel(Point3D p,double Rx,double Ry, int i,int j,int nX,int nY) {
		
		double tempY = ((i - nY / 2.0) * Ry + Ry/2.0);
		double tempX = ((j - nX / 2.0) * Rx + Rx/2.0);
				
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
		
		return getCenterOfPixel(Pc,Rx,Ry,i,j,nX,nY);
	}
	
	
	/*
	 * for mini project 1 - create ray throw the pixel in place ij
	 */
	public Ray ConstructRayThroughPixelGrid(int nX, int nY, int x, int y, int j, int i, int numbergrid) {
		
		//next lines for calculate point to ray :
		
		Point3D Pc = getCenterOfPixel(nX, nY, x, y); //center of pixel = new pc
	
//		//for debug:
//		return new Ray(p0,Pc.subtract(p0));
		
		//****************************************************************
		
		//width of pixel
		double Rx = width / nX / numbergrid;
		//height of pixel
		double Ry = height / nY / numbergrid;

		//p is a point on the grid of the pixel 
		Point3D p = getCenterOfPixel(Pc,Rx,Ry,i,j,numbergrid,numbergrid);
		
		//make ray from p0 to the center			    
		return new Ray(p0,p.subtract(p0));
		
		//****************************************************************
		
//		//for debug:
//		return ConstructRayThroughPixel(nX,nY,x,y);
	}
	
}
