package primitives;

public class Material {

	public int nShininess = 0;
	public double kD,kS = 0;
	
	//seters
	public Material setKD(double num)
	{ 
		kD = num; 
		return this;
	}
	public Material setKS(double num) 
	{ 
		kS = num; 
		return this;
	}
	public Material setShininess(int num)
	{ 
		nShininess = num; 
		return this;
	}

}
