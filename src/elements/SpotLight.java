package elements;
import static org.junit.Assert.*;


import java.awt.Color;

import renderer.*;
import org.junit.Test;
import org.junit.experimental.max.MaxCore;

import primitives.*;


public class SpotLight extends PointLight{

	private Vector direction;

	//constructor
	public SpotLight(primitives.Color l, Point3D p, Vector v)
	{
		super(l, p);
		direction = v.normalized();
	}
	
	//The function calculate the color
    public primitives.Color getIntensity(Point3D p)
    {
    	primitives.Color temp = super.getIntensity(p);
    	return temp.scale(Math.max(0, direction.dotProduct(getL(p))));
    }

    //return vector of Lighting direction value
	public Vector getL(Point3D p) {
		 return super.getL(p);
	}
}
