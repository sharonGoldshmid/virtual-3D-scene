//package geometries;
//
//import primitives.Vector;
//
//public class Triangle extends Polygon{
//	
//}
package geometries;

import java.util.LinkedList;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

public class Triangle extends Polygon{

	@Override
	public String toString() {
		return super.toString();
	}

	
	//constructor
	public Triangle(Point3D... vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * 	return the cutting points with a ray
	 */
	public List<Point3D> helpFindIntersections(Ray ray)
	{ 
		Vector v1 = vertices.get(0).subtract(ray.getPoint());
		Vector v2 = vertices.get(1).subtract(ray.getPoint());
		Vector v3 = vertices.get(2).subtract(ray.getPoint());
		
		Vector N1 = v1.crossProduct(v2);
		Vector N2 = v2.crossProduct(v3);
		Vector N3 = v3.crossProduct(v1);
		
		double t1 = ray.getDirection().dotProduct(N1);
		if(Util.isZero(t1))
			return null;
		double t2 = ray.getDirection().dotProduct(N2);
		if(Util.isZero(t2))
			return null;
		double t3 = ray.getDirection().dotProduct(N3);
		if(Util.isZero(t3))
			return null;
		
		if(t1 >0 && t2 > 0 && t3 > 0 || t1<0 && t2 < 0 && t3 < 0)
		{
			Plane p = new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
			return p.findIntersections(ray);
		}
		
		return null;
	}
	
	/*
	 * return the cutting points with a ray as geopoint
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		List<GeoPoint> intersections = new LinkedList<>();
		List<Point3D> list = helpFindIntersections(ray);
		
		if(list == null || list.isEmpty()) return null;
        for (int i = 0; i < list.size(); ++i)
        {
        	intersections.add(new GeoPoint(this, list.get(i)));
        }
        return intersections;
		}
	
	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}
}

