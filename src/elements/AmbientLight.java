package elements;
import static org.junit.Assert.*;
//import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;


public class AmbientLight extends Light{
	
	//constructors
	public AmbientLight()
	{
		super(Color.BLACK);
	}
	
	public AmbientLight(Color ia,double ka)
	{
		super(ia.scale(ka));
	}
	
}
