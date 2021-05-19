package geometries;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;

public class Geometries implements Intersectable {
	//list of geometries
	private LinkedList<Intersectable> geometriesList;
	
	
	//constructors
	public Geometries() 
	{
		geometriesList = new LinkedList<Intersectable>();
	}
	
	public Geometries(Intersectable... geometries)
	{
		for(int i=0;i<geometries.length;i++)
		{
			geometriesList.addLast(geometries[i]);
		}
	}
	
	//add geometries to the list
	public void add(Intersectable... geometries) {
		for(int i=0;i<geometries.length;i++)
		{
			geometriesList.addLast(geometries[i]);
		}
	}
	
	/*
	 * 	return the cutting points of geometries with the ray
	 */
	public List<Point3D> findIntersections(Ray ray) {
		
		if(geometriesList.size()==0)
			return null;
		
		List<Point3D> points = new LinkedList<>();
        for (int i=0;i<geometriesList.size();i++) { //all geometries
        	
            //get cutting points with every geometry
            List<Point3D> geometryPoints = geometriesList.get(i).findIntersections(ray);
            if(geometryPoints != null){ //there are cutting points with this geometry
                points.addAll(geometryPoints);
            }
        }
        if(points.isEmpty()) return null;
        return points;
		}
	
	/*
	 * 	return the cutting points of geometries with the ray as geopoint list
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		List<GeoPoint> intersections = new LinkedList<>();
		
		for (int i=0;i<geometriesList.size();i++) { //all geometries
            //get cutting points with every geometry
            List<GeoPoint> geometryPoints = geometriesList.get(i).findGeoIntersections(ray);
            if(geometryPoints != null){ //there are cutting points with this geometry
            	intersections.addAll(geometryPoints);
            }
        }
		if (intersections.isEmpty())
			return null;
        return intersections;
	}
}