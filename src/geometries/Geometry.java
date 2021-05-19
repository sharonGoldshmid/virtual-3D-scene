package geometries;

import primitives.*;

public abstract class Geometry implements Intersectable{
	
	protected Color emmission = Color.BLACK;
	private Material material = new Material();
	
	public abstract Vector getNormal(Point3D p);
	
	//geter
	public Color getEmmission() { return emmission; }
	public Material getMaterial() { return material; }
	
	//seter
	public Geometry setEmmission(Color e) 
	{ 
		emmission = e; 
		return this;
	}
	public Geometry setMaterial(Material m) 
	{ 
		material = m; 
		return this;
	}
}
