package primitives;

//import java.math.*;

//import static primitives.Coordinate.*;

public class Point3D {
	final Coordinate x;
	final Coordinate y;
	final Coordinate z;
	public static final Point3D ZERO = new Point3D(0, 0, 0);
	
	//constructors
	
	public Point3D(Coordinate a, Coordinate b, Coordinate c)
	{
		x= a;
		y= b;
		z = c;
	}
	public Point3D(double a, double b, double c)
	{
		x= new Coordinate(a);
		y= new Coordinate(b);
		z= new Coordinate(c);
	}
	
	//actions: +,-
	public Point3D add(Vector v)
	{
		Point3D p = v.getHead();
		Point3D temp = new Point3D(this.getx() + p.getx(), this.gety() + p.gety(), this.getz() + p.getz());
		return temp;
	}
	public Vector subtract(Point3D p)
	{
		Point3D temp = new Point3D(this.getx() - p.getx(), this.gety() - p.gety(), this.getz() - p.getz());
		return new Vector(temp);
	}
	
	//calculate the distance between point to this
	public double distanceSquared(Point3D p) //dis*dis
	{
		double a = this.getx() - p.getx();
		double b = this.gety() - p.gety();
		double c = this.getz() - p.getz();
		return a*a + b*b + c*c;
	}
	public double distance(Point3D p) //dis
	{
		return Math.sqrt(distanceSquared(p));
	}
	
	
	public boolean equals(Object ob)
	{
		if (this == ob) return true;
		if (ob == null) return false;
		if (!(ob instanceof Point3D)) return false;
		Point3D temp = (Point3D)ob;
		return x.equals(temp.x) && y.equals(temp.y) && z.equals(temp.z);
	}

	public double getx() { return x.coord; }
	public double gety() { return y.coord; }
	public double getz() { return z.coord; }
	
	public String toString()
	{
		return this.getx() + " " + this.gety() + " " + this.getz();
	}
}
