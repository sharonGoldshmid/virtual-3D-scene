//package geometries;
//
//import primitives.Point3D;
//import primitives.Vector;
//
//public class Plane implements Geometry{
//	Point3D q0;
//	Vector normal;
//	
//}
package geometries;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
//import primitives.Point3D;
//import primitives.Vector;
import primitives.*;

public class Plane extends Geometry{
	Point3D q0;
	Vector normal;
	
	
	//constructors
	 public Plane(Point3D q, Vector v) {
		//super();
		// TODO Auto-generated constructor stub
		q0=q;
		normal=v;
	}
	public Plane(Point3D q, Point3D q1, Point3D q2) {
		//super();
		q0=q;
		Vector v1 = q.subtract(q1);
		Vector v2 = q.subtract(q2);
		normal = v1.crossProduct(v2).normalized();
	}
	
	
	public Point3D getQ0() { return q0; }
	public Vector getNormal() { return normal; }
	
	/*
	 * get normal on the from a point on the geometry
	 */
	public Vector getNormal(Point3D p) { return normal.normalize(); }
	

	public String toString() {
		return "Plane [q0=" + q0.toString() + ", normal=" + normal.toString() + "]";
	}


	/*
	 * 	return the cutting points with a ray
	 */
	public List<Point3D> helpFindIntersections(Ray ray) {
		try //check if start of ray on the plane
		{
			Vector p0 = q0.subtract(ray.getPoint());
			if (Util.isZero(normal.dotProduct(p0))) throw new IllegalArgumentException("on the plane");
		}
		catch(IllegalArgumentException e)
		{
			return null;
		}
		double nv = normal.dotProduct(ray.getDirection());
		if(Util.isZero(nv)) //check if ray is ortogonal to the plane 
		{
			return null;
		}
		double t = normal.dotProduct(q0.subtract(ray.getPoint()))/nv;

		
		if(t <= 0 || Util.isZero(t))
			return null;
		
		//Point3D p = ray.getPoint().add(ray.getDirection().scale(t));
		Point3D p = ray.getPoint(t);
		return List.of(p);
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
}
