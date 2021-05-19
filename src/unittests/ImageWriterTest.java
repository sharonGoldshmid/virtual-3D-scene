/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.awt.Color;

import renderer.*;
import org.junit.Test;
import primitives.*;

/**
 * Unit tests for renderer.ImageWriter class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */

public class ImageWriterTest {

	@Test
	public void test()
	{
		ImageWriter grid = new ImageWriter("grid",800,500);
		primitives.Color black = new primitives.Color(0,0,0);
		primitives.Color red = new primitives.Color(255,0,0);

		for(int i = 0; i<800; i++)
		{	
			for(int j= 0;j < 500; j++)
			{
				if(j % 50==0 || i % 50 == 0)
					grid.writePixel(i, j, black);
				else
					grid.writePixel(i, j, red);
			}
		}
		grid.writeToImage();	

	}

}
