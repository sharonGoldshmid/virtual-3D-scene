package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/*
 * interface LightSource exercise a light source
 */
public interface LightSource {
	
	public Color getIntensity(Point3D p);
	public Vector getL(Point3D p);
	double getDistance(Point3D point);
}
