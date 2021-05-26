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
	private static final double DELTA = 0.1;//const for size moving first rays for shading rays
	private static final int MAX_CALC_COLOR_LEVEL = 10;//const for recursion stop conditions
	private static final double MIN_CALC_COLOR_K = 0.001;//const for recursion stop conditions
	
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
		if(gp.geometry.getMaterial().kT == 0) return true;
		
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		Ray lightRay = new Ray(gp.point.add(delta), lightDirection);
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
	
	//calculation the reflection ray
	public Ray constructReflectedRay(Point3D point, Vector l,Vector n) {
		double nl = Util.alignZero(n.dotProduct(l));
		Vector v = l.add(n.scale(-2 * nl)); // nl must be not zero!
		Vector delta = n.scale(n.dotProduct(l) > 0 ? DELTA : - DELTA);
		return new Ray(point.add(delta),v);
	}
	
	//calculation the Transparency ray
	public Ray constructRefractedRay(Point3D point, Vector l,Vector n) {
		Vector delta = n.scale(n.dotProduct(l) < 0 ? DELTA : - DELTA);
		return new Ray(point.add(delta),l);
	}

	
//	//calculate the color we get from all the lights- got it from model
//	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
//		Vector v = ray.getDirection (); //from camera to point
//		Vector n = intersection.geometry.getNormal(intersection.point);
//		double nv = Util.alignZero(n.dotProduct(v));
//		if (nv == 0) return Color.BLACK; //point ortogonal to camera
//		int nShinines = intersection.geometry.getMaterial().nShininess;
//		double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
//		Color color = Color.BLACK;
//		for (LightSource lightSource : scene.lights) { //all light sources
//			Vector l = lightSource.getL(intersection.point); //from light to point
//			double nl = Util.alignZero(n.dotProduct(l));
//			if (nl * nv > 0) { //if they're on the same side add the light we need
//				if(unshaded(l,n,intersection,lightSource)) {
//					Color lightIntensity = lightSource.getIntensity(intersection.point);
//					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
//					calcSpecular(ks, l,n,nl, v, nShinines, lightIntensity));
//				}
//			}
//		}
//		return color;
//	}
	
//////	//call to the recursion function
//////    private Color calcGlobalEffects(Ray ray, int level, double kx, double kkx) {
//////    	GeoPoint gp = findClosestIntersection (ray);
//////    	return (gp == null ? scene.background : calcColor(gp, ray, level�1, kkx)).scale(kx));
//////	}
//////    
//////    //the recursion function
//////    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
//////    	Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
//////    	Material material = gp.geometry.getMaterial();
//////    	double kkr = k * material.kr;
//////    	if (kkr > MIN_CALC_COLOR_K)
//////    	color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kr, kkr);
//////    	double kkt = k * material.kT;
//////    	if (kkt > MIN_CALC_COLOR_K) color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
//////    	return color;
//////    }
//////	
////	
//	
	
//	//calculate color of point
//    private Color calcColor(GeoPoint closest, Ray ray, int level, double k) {
//    	Color color = scene.ambientLight.getIntensity().add(closest.geometry.getEmmission());
//    	color = color.add(calcLocalEffects(closest, ray));
//    	return 1 == level ? color : color.add(calcGlobalEffects(closest, ray, level, k));
//    }
//
//	//traceRay call this function to calculate the color
//    private Color calcColor(GeoPoint gp, Ray ray) {
//    	return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, Double.POSITIVE_INFINITY)
//    			.add(scene.ambientLight.getIntensity());
//    }
    
    
    //_________________________________________________________________________________
    
	/***
	 * A function that calculates the color that is at a point of intersection and returns it
	 * @param closestPoint
	 * @return The color that thinks
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL,1.0)
		.add(scene.ambientLight.getIntensity());
		}
	
	/**
	 * calculate the color of pixel according to the geometry and returns the color of the pixel
	 * 
	 * @param p
	 * @return Color
	 */
	
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color =new Color( intersection.geometry.getEmmission());
		color = color.add(calcLocalEffects(intersection, ray,k));
		return 1 == level ||k < MIN_CALC_COLOR_K ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
		
		}
	/**
	 * method for calculating a pong model
	 * @param intersection
	 * @param ray
	 * @return Color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDirection ();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().KS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point); 
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv) 
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
				//if (unshaded(lightSource,l,n, intersection)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l,n,nl, v, nShininess, lightIntensity));
			    }
			}
		}
		return color;
		}

		
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
	       
			Color color = Color.BLACK;
			Vector n= geopoint.geometry.getNormal(geopoint.point);
			Material material = geopoint.geometry.getMaterial();
			double kr = material.kR,
					kkr = k * kr;
			if (kkr > MIN_CALC_COLOR_K) {
				Ray reflectedRay = constructReflectedRay( geopoint.point, ray,n);
				GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
				if(reflectedPoint==null)
					return color.scale(kr);
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
				
			}
			double kt = material.KT, kkt = k * kt;
			if (kkt > MIN_CALC_COLOR_K) {
				Ray refractedRay = constructRefractedRay(geopoint.point, ray,n);
				GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
				if(refractedPoint==null)
					return color.scale(kt);
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
			}
			return color;
	}

//	    /***
//	     *  A function that calculates the shadow of the shape
//	     * @param l
//	     * @param n
//	     * @param geopoint
//	     * @return If there is a shadow or no shadow
//	     * 
//	     */
//	   private boolean unshaded(LightSource light,Vector l, Vector n, GeoPoint geopoint) {
//	    	Vector lightDirection = l.scale(-1); // from point to light source
//	    	Ray lightRay = new Ray(geopoint.point, lightDirection,n);
//	    	List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//	    	if (intersections == null) return true;
//	    	double lightDistance = light.getDistance(geopoint.point);
//	    	for (GeoPoint gp : intersections) {
//		    	if (alignZero( gp.point.distance(geopoint.point)- lightDistance) <= 0 && gp.geometry.getMaterial().KT == 0)
//		    	      return false;
//	    	}
//	    	return true;
//	    }
	   /**
	    * A function that calculates ktr
	    * @param ls
	    * @param l
	    * @param n
	    * @param geoPoint
	    * @return
	    */
	   private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint)
	   {
	    	Vector lightDirection = l.scale(-1); // from point to light source
	    	
	    	Ray lightRay = new Ray(geoPoint.point, lightDirection,n);
	    	List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
	    	if (intersections == null) return 1.0;
	    	double lightDistance = ls.getDistance(geoPoint.point);
	    	double ktr=1.0;
	    	for (GeoPoint gp : intersections) {
		    	if (Util.alignZero( gp.point.distance(geoPoint.point)- lightDistance) <= 0 )
		    	{
		    		ktr*=gp.geometry.getMaterial().KT;
		    		if(ktr < MIN_CALC_COLOR_K)
		    			return 0.0;
		    	}
	    	}
	    	return ktr;
	    }
}

