//package geometries;
//
//import primitives.Point3D;
//import primitives.Ray;
//import primitives.Vector;
//
//public class Tube implements Geometry{
//	Ray axisRay;
//	double radius;
//
//}
package geometries;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import jdk.swing.interop.DispatcherWrapper;
import primitives.*;

public class Tube extends Geometry{
	
	Ray axisRay;
	double radius;
	
	
	//constructor
	public Tube(Ray r, double d) {
		//super();
		// TODO Auto-generated constructor stub
		axisRay = r;
		radius = d;
	}
	
	
	public Ray getAxisRay() {
		return axisRay;
	}
	
	public double getRadius() {
		return radius;
	}

	
	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay.toString() + ", radius=" + radius + "]";
	}


	/*
	 * get normal on the from a point on the geometry
	 */
	public Vector getNormal(Point3D p) {
		Point3D p0 = axisRay.getPoint();
		Vector rayVector = axisRay.getDirection();
		double t = rayVector.dotProduct(p.subtract(p0));
		Point3D o = p0.add(rayVector.scale(t));
		return p.subtract(o).normalize();
	}
	
	public List<Point3D> findIntersections(Ray ray) {
		return null;
	}
	
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		return null;
	}
}
