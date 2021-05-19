//package geometries;
//
//public class Cylinder extends Tube{
//	double height;
//
//}
package geometries;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

public class Cylinder extends Tube{
	double height;
	
	//constructors
	public Cylinder(Ray r, double d, double h) {
		super(r,d);
		// TODO Auto-generated constructor stub
		height = h;
	}
	
	public double getHeight() {
		return height;
	}

	
	@Override
	public String toString() {
		return "Cylinder [axisRay=" + axisRay.toString() + ", height=" + height + ", radius=" + radius + "]";
	}


	public Vector getNormal(Point3D p) {
		return null;
	}
	public List<Point3D> findIntersections(Ray ray) {
		return null;
	}

	public List<GeoPoint> findGeoIntersections(Ray ray){return null;}
}
