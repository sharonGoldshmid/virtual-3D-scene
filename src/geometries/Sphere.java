package geometries;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

public class Sphere extends Geometry{
	
	Point3D center;
	double radius;
	
	//constructor
	public Sphere(Point3D p, double d) {
		//super();
		// TODO Auto-generated constructor stub
		center = p;
		radius = d;
	}
	
	public Point3D getCenter() {
		return center;
	}


	public double getRadius() {
		return radius;
	}
	
	@Override
	public String toString() {
		return "Sphere [center=" + center.toString() + ", radius=" + radius + "]";
	}
	/*
	 * get normal on the from a point on the geometry
	 */
	public Vector getNormal(Point3D p) {
		Vector pnormal = p.subtract(center);
		return pnormal.normalize();
	}


	/*
	 * 	return the cutting points with a ray
	 */
	public List<Point3D> helpFindIntersections(Ray ray) {
		Point3D P0 = ray.getPoint();
        Vector v = ray.getDirection();

        if (P0.equals(center)) {
            return List.of(center.add(v.scale(radius)));
        }

        Vector U = center.subtract(P0);

        double tm = Util.alignZero(v.dotProduct(U));
        double d = Util.alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = Util.alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = Util.alignZero(tm - th);
        double t2 = Util.alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
//            Point3D P2 = P0.add(v.scale(t2));
            Point3D P1 =ray.getPoint(t1);
            Point3D P2 =ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
//            Point3D P1 = P0.add(v.scale(t1));
            Point3D P1 =ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
//            Point3D P2 = P0.add(v.scale(t2));
            Point3D P2 =ray.getPoint(t2);
            return List.of(P2);
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
}
