package elements;
import static org.junit.Assert.*;


import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;


public class DirectionalLight extends Light implements LightSource{

	private Vector direction;
	
	//constructor
	public DirectionalLight(primitives.Color c, Vector v)
	{
		super(c);
		direction =v.normalized();
	}
	
	//The function calculate the color
    public primitives.Color getIntensity(Point3D p)
    {
    	return intensity;
    }

    //return vector of Lighting direction value
	public Vector getL(Point3D p) {
		return direction;
	}
}
