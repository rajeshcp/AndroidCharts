package com.sbgraph.mathutils;

import android.graphics.PointF;
import android.graphics.RectF;

public class MathHelper {
	/**
	 * Constructor function
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public MathHelper(SingleTonEnforcer singletonEnforcer) {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param rect of type Rect
	 * @return diagonal of type float
	 * function which will return the diagonal of the rect
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public static float getDiagonal(RectF rect)
	{
		PointF pointA = new PointF(rect.left, rect.top);
		PointF pointB = new PointF(rect.right, rect.bottom);
		double distance = Math.pow((pointA.x - pointB.x), 2) + Math.pow((pointA.y - pointB.y), 2);
		distance = Math.sqrt(distance);
		float dist = Float.parseFloat(String.valueOf(distance));
		return dist;
	}
	
	/**
	 * @param angle of type float
	 * @return angleRadian of type double
	 * function which will convert the angle in degree to radian 
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public static double degreeToRadian(float angle)
	{
		double angleRadian = (float)angle * (Math.PI / 180);
		return angleRadian;
	}
	
	public static PointF getPoint(PointF origin, float distance, float angle)
	{
		double conAngle = Math.cos(degreeToRadian(angle));
		double sinAngle = Math.sin(degreeToRadian(angle));
		double x = distance * conAngle;
		double y = distance * sinAngle;
		x = origin.x + x;
		y = origin.y + y;
		return new PointF(Float.parseFloat(String.valueOf(x)), Float.parseFloat(String.valueOf(y)));
	}
	
}
class SingleTonEnforcer
{
	
}