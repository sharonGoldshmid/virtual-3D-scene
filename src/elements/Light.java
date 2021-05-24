package elements;
import static org.junit.Assert.*;

//import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;

/*
 * all the light sources are extends from it
 */
public abstract class Light {
	
	Color intensity;
	
	//constructor
	protected Light(Color c)
	{
		intensity = c;
	}
	
	//geter
	public Color getIntensity() { return intensity; }
	
	
}
