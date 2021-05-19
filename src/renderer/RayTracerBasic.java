package renderer;
import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;

import primitives.*;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{

	//constructor
	public RayTracerBasic(Scene s)
	{
		super(s);
	}
	
	//get ray and send color
	public primitives.Color traceRay(Ray ray)
	{
		List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
		if (points != null) {
        	//take the closet point and calculate its color
            return calcColor(ray.findClosestGeoPoint(points),ray);
        }
		//there is not cutting points
        return scene.background;
    }

	//help functions for calculate the color!
	
	/*
	 * 1) calculate the diffuse of the light on the geometry
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity)
	{
		double nl= l.dotProduct(n);
		return lightIntensity.scale(Math.abs(nl)*kd);
	}
	
	/*
	 * 2) calculate the Specular of the light on the geometry
	 */
	private Color calcSpecular(double ks,Vector l,Vector n,double nl,Vector v,double nShininess,Color lightIntensity) {
	   double p = nShininess;
       Vector R = l.add(n.scale(-2 * nl)); // nl must be not zero!
       double minusVR = -Util.alignZero(R.dotProduct(v));
       if (minusVR <= 0) return Color.BLACK; // view from direction opposite to r vector
       return lightIntensity.scale(ks * Math.pow(minusVR, p));
	}
	
	//calculate the color we get from all the lights- got it from model
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector v = ray.getDirection ();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0) return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(calcDiffusive(kd, l, n, lightIntensity),
				calcSpecular(ks, l,n,nl, v, nShininess, lightIntensity));
			}
		}
		return color;
	}
	
	//calculate color of point
    private Color calcColor(GeoPoint closest,Ray ray) {
    	return scene.ambientLight.getIntensity().add(closest.geometry.getEmmission())
    			.add(calcLocalEffects(closest,ray));
    }
}
