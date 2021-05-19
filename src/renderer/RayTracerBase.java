package renderer;

import primitives.*;
import scene.Scene;

public abstract class RayTracerBase {

	protected Scene scene;
	
	//constructor
	public RayTracerBase(Scene s)
	{
		scene = s;
	}
	
	//implementaion in RayTracerBasic
	public abstract primitives.Color traceRay(Ray ray);

}
