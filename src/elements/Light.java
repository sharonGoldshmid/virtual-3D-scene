package elements;
import static org.junit.Assert.*;


import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;

/*
 * all the light sources are extends from it
 */
public abstract class Light {
	
	primitives.Color intensity;
	
	//constructor
	protected Light(primitives.Color c)
	{
		intensity = c;
	}
	
	//geter
	public primitives.Color getIntensity() { return intensity; }
	
	
}
