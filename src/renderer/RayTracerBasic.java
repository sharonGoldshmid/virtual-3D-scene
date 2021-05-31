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
	public Color traceRay(Ray ray)
	{
			GeoPoint closestGPoint = findClosestIntersection(ray);
			return closestGPoint == null ? scene.background : calcColor(closestGPoint, ray);
	}

	//get ray and send the closest point as geopiont
	private GeoPoint findClosestIntersection(Ray ray) {
			List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
			if (points != null) return ray.findClosestGeoPoint(points);
	        return null; //there is not cutting points
	}

	
	//help functions for calculate the color!
	
	//const for size moving first rays for shading rays
	//private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	
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
       Vector R = l.add(n.scale(-2 * nl)); // nl must be not zero!
       double minusVR = -Util.alignZero(R.dotProduct(v));
       if (minusVR <= 0) return Color.BLACK; // view from direction opposite to r vector
       return lightIntensity.scale(ks * Math.pow(minusVR, nShininess));
	}
	
	/*
	 *3) boolean function for check if there is shadow
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource)
	{
		if(gp.geometry.getMaterial().kT == 1) return true;
		
		Vector lightDirection = l.scale(-1); // from point to light source
		//Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		//Ray lightRay = new Ray(gp.point.add(delta), lightDirection);
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		
		List<GeoPoint> list = scene.geometries.findGeoIntersections(lightRay);
		
		if(list == null) return true;
		double dToLight = lightSource.getDistance(gp.point); //from the light the point
		for(int i=0; i < list.size(); i++) //check if there are geometries that make shadow
		{
			double dToGP = gp.point.distance(list.get(i).point);//from the point to another geometry
			if(dToLight > dToGP) return false;//there is a geometry that make shadow
		}
		return true;
	} 
	
	/*
	 * 4) A function that calculates ktr to create shadows	   
	 */
	   private double transparency(Vector l, Vector n, GeoPoint geopoint, LightSource light) {
		   Ray lightRay = new Ray(geopoint.point, l.scale(-1), n);// from point to light source with delta
		   double lightDistance = light.getDistance(geopoint.point);//distance light from point
		   List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		   
		   if (intersections == null) return 1;//no shadow
		   
		   double ktr = 1;
		   for (GeoPoint gp : intersections) {
			   if (Util.alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) { //the geopint closer then the light - there is shadow
				   	ktr *= gp.geometry.getMaterial().kT;
				   	if (ktr < MIN_CALC_COLOR_K) return 0;
			   }
		   }
		   return ktr;
	   }
	
	//calculation the reflection ray
	  public Ray constructReflectedRay(Point3D point, Vector v,Vector n) {
	       double vn = Util.alignZero(v.dotProduct(n));
	       Vector vnn = n.scale(-2 * vn);
	       Vector r = v.add(vnn);
	       return new Ray(point, r, n);
	  }

	
	//calculation the Transparency ray
	public Ray constructRefractedRay(Point3D point, Vector l,Vector n) {
	    return new Ray(point,l.scale(1),n);
	}
	
	
	/*
	 * calculate the color we get from all the lights- got it from model
	 * the function don't use reflection and transparency
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDirection (); //from camera to point
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		
		if (nv == 0) return Color.BLACK; //point ortogonal to camera
		
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD;
		double ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		
		for (LightSource lightSource : scene.lights) { //all light sources
			Vector l = lightSource.getL(intersection.point); //from light to point
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { //if they're on the same side add the light we need
				double ktr = transparency(l, n, intersection,lightSource); 
				if (ktr * k > MIN_CALC_COLOR_K) {
				//if (unshaded(l,n, intersection,lightSource)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr); //color from light according to ktr
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),calcSpecular(ks, l,n,nl, v, nShininess, lightIntensity));
			    }
			}
		}
		return color;
	}
	
	/*
	 * 5) calculate the color we get from reflection and transparency
	 *  recursive function - activate calcColor
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
	       
		Color color = Color.BLACK;
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		double kr = geopoint.geometry.getMaterial().kR;
		double kkr = k * kr;
		
		if (kkr > MIN_CALC_COLOR_K) {//we can calculate the reflection
			Ray reflectedRay = constructReflectedRay(geopoint.point, ray.getDirection(),n);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if(reflectedPoint == null) return color.scale(kr);
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
			//color till now + color of reflectedPoint
		}
		
		double kt = geopoint.geometry.getMaterial().kT;
		double kkt = k * kt;
		
		if (kkt > MIN_CALC_COLOR_K) {//we can calculate the transparency
			Ray refractedRay = constructRefractedRay(geopoint.point, ray.getDirection(),n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if(refractedPoint==null) return color.scale(kt);
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
			//color till now + color of refractedPoint
		}
		return color;
	}
	
	
	/*
	 * A function that calculates the color that is at a point and returns it
	 * 
	 * return the color + ambientLight
	 */
		private Color calcColor(GeoPoint geopoint, Ray ray) {
			return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL,1)
			.add(scene.ambientLight.getIntensity());
		}
		
		/*
		 * calculate the color of point and use reflection and transparency
		 * a recursive function - activate calcGlobalEffects
		 * color of geometry + pong model + 
		 */
		private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
			Color color = intersection.geometry.getEmmission();
			color = color.add(calcLocalEffects(intersection, ray,k));
			return 1 == level || k < MIN_CALC_COLOR_K ?
					color : color.add(calcGlobalEffects(intersection, ray, level, k));
		}
}
