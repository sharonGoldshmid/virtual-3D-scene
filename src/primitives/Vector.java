package primitives;

//import static primitives.Point3D.*;


public class Vector {
	protected Point3D head;
	
	//constructors. an exception thrown if the vector equal to zero
	public Vector(Point3D p)
	{ 
		if(p.equals(Point3D.ZERO)) throw new IllegalArgumentException("vector cant be zero");
		head = p; 
	}
	public Vector(Coordinate a, Coordinate b, Coordinate c)
	{ 
		Point3D p = new Point3D(a, b, c);
		if(p.equals(Point3D.ZERO)) throw new IllegalArgumentException("vector cant be zero");
		head = p; 
	}
	public Vector(double a, double b, double c)
	{ 
		Point3D p = new Point3D(a, b, c);
		if(p.equals(Point3D.ZERO)) throw new IllegalArgumentException("vector cant be zero");
		head = p; 
	}
	
	public Point3D getHead( ) { return head; }
	
	//actions: +,-,*
	public Vector add(Vector v)
	{
		return new Vector(head.add(v));
	}
	public Vector subtract(Vector v)
	{
		return head.subtract(v.head);
	}
	public Vector scale(double d)
	{
		return new Vector(d*head.getx(), d*head.gety(), d*head.getz());
	}
	
	
	public Vector crossProduct(Vector v) //machpela vectorit
	{
		Point3D p1 = new Point3D(head.gety()*v.head.getz(), head.getz()*v.head.getx(), head.getx()*v.head.gety());
		Point3D p2 = new Point3D(head.getz()*v.head.gety(), head.getx()*v.head.getz(), head.gety()*v.head.getx());
		return p1.subtract(p2);
	}
	public double dotProduct(Vector v) //machpela scalarit
	{
		return head.getx()*v.head.getx() + head.gety()*v.head.gety() + head.getz()*v.head.getz();
	}
	
	//calculate length of this 
	public double lengthSquared() //len*len
	{
		return head.distanceSquared(Point3D.ZERO);
	}
	public double length() //len
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	//make length to be 1
	public Vector normalize() //change this to the normalize vector
	{
		double dis = 1.0 / this.length();
		Point3D temp = new Point3D(dis*head.getx(), dis*head.gety(), dis*head.getz());
		head = temp;
		return this;
	}
	public Vector normalized() //only return the normalize vector
	{
		Vector temp = new Vector(head);
		return temp.normalize();
	}
	
	
	public boolean equals(Object ob)
	{
		if (!(ob instanceof Vector)) return false;
		Vector temp = (Vector)ob;
		return head.equals(temp.getHead());
	}
	
	
	public String toString()
	{
		return head.toString();
	}

}
