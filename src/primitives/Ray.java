package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

public class Ray {
	final Point3D p0;
	final Vector dir;
	
	//constructor
	public Ray(Point3D p, Vector v)
	{
		p0 = p;
		dir = v.normalize();
	}
	
	
	public boolean equals(Object ob)
	{
		Ray temp = (Ray)ob;
		return temp.p0.equals(temp.p0) && temp.dir.equals(temp.dir);
	}
	public Point3D getPoint() { return p0; }
	public Vector getDirection() { return dir; }
	
	
	public String toString()
	{
		return "Ray [p0=" + p0.toString() + ", normal=" + dir.toString() + "]";
	}
	
	//refactoring for calculate P=P0+t∙v for findIntersections
	public Point3D getPoint(double t)
	{
		return p0.add(dir.scale(t));
	}
	
	//return the point with the min distance from p0
	public Point3D findClosestPoint(List<Point3D> listp)
	{
		if(listp.isEmpty()) return null;
		
		Point3D pmin = listp.get(0);
		double min = pmin.distance(p0);
		for (int i = 1; i < listp.size(); ++i) {
			Point3D newp = listp.get(i);
			double newdis = newp.distance(p0);
			if(min > newdis)
			{
				min = newdis;
				pmin = newp;
			}
		}
		return pmin;
	}
	
	//return the geopoint with the min distance from p0
	public GeoPoint findClosestGeoPoint(List<GeoPoint> listp)
	{
		if(listp==null || listp.isEmpty()) return null;
		
		GeoPoint geomin = listp.get(0);
		double min = geomin.point.distance(p0);
		for (int i = 1; i < listp.size(); ++i)
			if(listp.get(i).point.distance(p0) < geomin.point.distance(p0))
				geomin = listp.get(i);
		return geomin;
	}
}
