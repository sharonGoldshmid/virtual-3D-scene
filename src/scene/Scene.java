package scene;

import static org.junit.Assert.*;

import java.awt.Color;
import java.nio.charset.IllegalCharsetNameException;
import java.util.LinkedList;
import java.util.List;

import elements.*;
import geometries.Geometries;
import renderer.*;
import org.junit.Test;
import primitives.*;


public class Scene {

	public String name;
	public primitives.Color background;
	public AmbientLight ambientLight;
	public Geometries geometries;
	public List<LightSource> lights;
	
	public Scene(String s)
	{
		name = s;
		background = new primitives.Color(0,0,0);
		ambientLight = new AmbientLight();
		geometries = new Geometries();
		lights = new LinkedList<>();
	}
	
	public Scene setBackground(primitives.Color c)
	{
		background = c;
		return this; 
	}
	public Scene setAmbientLight(AmbientLight a)
	{
		ambientLight = a;
		return this; 
	}
	public Scene setGeometries(Geometries g)
	{
		geometries = g;
		return this; 
	}
	public Scene setLights(List<LightSource> l) 
	{
		lights = l;
		return this; 
	}
}
