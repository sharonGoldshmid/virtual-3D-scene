package elements;
import static org.junit.Assert.*;
//import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;


public class DirectionalLight extends Light implements LightSource{

	private Vector direction;
	
	//constructor
	public DirectionalLight(Color c, Vector v)
	{
		super(c);
		direction =v.normalized();
	}
	
	//The function calculate the color
    public Color getIntensity(Point3D p)
    {
    	return intensity;
    }

    //return vector of Lighting direction value
	public Vector getL(Point3D p) {
		return direction;
	}
	
	//return the distance from the light to a point
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
}
