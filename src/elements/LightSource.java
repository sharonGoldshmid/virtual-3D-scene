package elements;

import primitives.Point3D;
import primitives.Vector;

/*
 * interface LightSource exercise a light source
 */
public interface LightSource {
	
	public primitives.Color getIntensity(Point3D p);
	public Vector getL(Point3D p);
}
