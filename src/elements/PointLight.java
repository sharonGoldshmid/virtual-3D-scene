package elements;
import static org.junit.Assert.*;
//import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;

public class PointLight extends Light implements LightSource{

	private Point3D position;
	private double kC, kL, kQ;
	
	//constructor
	public PointLight(Color l,Point3D p)
	{
		super(l);
		position = p;
		kL = kQ = 0;
		kC = 1;
	}
	
	//seters
	public PointLight setkC(double d)
	{
		kC = d;
		return this; 
	}
	public PointLight setkL(double d) 
	{ 
		kL = d;
		return this; 
	}	
	public PointLight setkQ(double d) 
	{
		kQ = d;
		return this; 
	}
	

	//The function calculate the color
    public Color getIntensity(Point3D p)
    {
    	double d = p.distance(position);
        return (intensity.reduce(kC + kL * d + kQ * d * d));
    }

    //return vector of Lighting direction value
	public Vector getL(Point3D p) {
		 if (p.equals(position)) { return null; }
	     return p.subtract(position).normalize();
	}

	//return the distance from the light to a point
	public double getDistance(Point3D point) {
		return point.distance(position);
	}
}

