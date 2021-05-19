package geometries;
import primitives.*;
import java.util.List;
import java.util.stream.Collectors;

import static geometries.Intersectable.GeoPoint;


public interface Intersectable {
	
	
	//public List<Point3D> findIntersections(Ray ray);
	default List<Point3D> findIntersections(Ray ray) {
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}


	
	/*
	 * class for ordered pairs of geometry and point
	 */
	public static class GeoPoint {
	    public Geometry geometry;
	    public Point3D point;
	    
	    //constructor
	    public GeoPoint(Geometry g, Point3D p)
	    {
	    	geometry = g;
	    	point = p;
	    }
	    
		public boolean equals(Object ob)
		{
			if (this == ob) return true;
			if (ob == null) return false;
			if (!(ob instanceof GeoPoint)) return false;
			GeoPoint temp = (GeoPoint)ob;
			return geometry.equals(temp.geometry) && point.equals(temp.point);
		}
		
	}
	
	public List<GeoPoint> findGeoIntersections(Ray ray);
}

