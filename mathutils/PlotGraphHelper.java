/**
 * 
 */
package com.sbgraph.mathutils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.sbgraph.graphutils.GraphLayout;
import com.sbgraph.graphutils.GraphOptions;

/**
 * @author rajesh
 * @date 4 feb 2012
 */
public class PlotGraphHelper {

	/**
	 * 
	 */
	public PlotGraphHelper(SingleTonEnforcer singleTonEnforcer) {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param canvas of type Canvas 
	 * @param center of type PointF
	 * @param paint of type Paint 
	 * @param radius of type float 
	 * @return of type null
	 * @author rajesh
	 * @date 3 feb 2012
	 */
	public static void drawShadow(Canvas canvas, PointF center, Paint paint, float radius)
	{
		paint.setColor(0x3F000000);
		paint.setStrokeWidth(3);
		canvas.drawCircle(center.x + 1, center.y + 1, radius, paint);
		paint.setColor(0x2FFFFFFF);
		paint.setStrokeWidth(3);
		canvas.drawCircle(center.x, center.y, radius - 2, paint);
	}
	
	/**
	 * @param canvas of type Canvas
	 * @param text of type String 
	 * @param x of type float 
	 * @param y of type float 
	 * @param fraction of type float
	 * function which will draw the text on the graphics 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public static void drawTextAt(Canvas canvas, String text, float x, float y, float fraction, int color)
	{
		int index = text.indexOf(".");
		if(index > -1)
		{
			int fractionLength = text.length() - index;
			fractionLength = Math.min(2, fractionLength);
			text = text.substring(0, index + fractionLength);
		}
		Paint paint = new Paint();
		paint.setColor(color);
		
		Rect bounds = new Rect();
	    paint.getTextBounds(text, 0, text.length(), bounds);

		paint.setAntiAlias(true);
		canvas.drawText(text, x - (bounds.width() * fraction), y, paint);
	}
	
	/**
	 * @param graphLayOut of type GraphLayout
	 * @param options of type GraphOptions
	 * @param canvas of type Canvas 
	 * @param area of type RectF
	 * @return of type null
	 * function which will draw the grid 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public static void drawGrid(GraphLayout graphLayOut, GraphOptions options, Canvas canvas)
	{
		RectF area = graphLayOut.getGraphArea();
		
		Path gridPath = new Path();
		Paint paint = new Paint();
		paint.setColor(options.get_gridLineColor());
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		gridPath.moveTo(area.left, area.top);
		
		
		int limit = (graphLayOut.get_xNofticks() > graphLayOut.get_yNofticks()) ? graphLayOut.get_xNofticks() : graphLayOut.get_yNofticks();
		float xFactor = (graphLayOut.get_xMax() - graphLayOut.get_xMin()) / graphLayOut.get_xNofticks();
		float yFactor = (graphLayOut.get_yMax() - graphLayOut.get_yMin()) / graphLayOut.get_yNofticks();
		
		for(int i = 0; i < limit; i++)
		{
			if(i < graphLayOut.get_yNofticks())
			{
				float y = area.top + (i * (area.height() / graphLayOut.get_yNofticks()));
				gridPath.moveTo(area.left - 3, y);
				gridPath.lineTo(area.right, y);
				float vY = graphLayOut.get_yMin() + (i * yFactor);
				vY = Math.round(vY);
				drawTextAt(canvas, String.valueOf(vY), area.left - 10, area.bottom - (y - area.top), 1, options.get_color());
			}
			if(i < graphLayOut.get_xNofticks())
			{
				float x = area.left + (i * (area.width() / graphLayOut.get_xNofticks()));
				gridPath.moveTo(x, area.top);
				gridPath.lineTo(x, area.bottom + 3);
				float vX = graphLayOut.get_xMin() + (i * xFactor);
				vX = Math.round(vX);
				drawTextAt(canvas, String.valueOf(vX), x, area.bottom + 10, (1/2), options.get_color());
			}
		}
		gridPath.close();
		canvas.drawPath(gridPath, paint);
		gridPath = null;
		paint = null;
	}
	
	
	/************************************************************/
	/****************fuctions for drawing charts*****************/
	/************************************************************/
	/**
	 * @param graphLayOut of type GraphLayout
	 * @param options of type GraphOptions
	 * @param canvas of type Canvas 
	 * @param area of type RectF
	 * @return of type null
	 * function which will draw the line graph
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public static void drawLineChart(GraphLayout graphLayOut, GraphOptions options, Canvas canvas)
	{
		RectF area = graphLayOut.getGraphArea();
		if(options.is_drawGrid())
		{
			drawGrid(graphLayOut, options, canvas);
		}
		Path path = new Path();
		Paint paint = new Paint();
		for(int i = 0; i < graphLayOut.get_dataSet().size(); ++i)
		{
			PointF[] points = graphLayOut.get_dataSet().get(i);
			path.moveTo(area.left, area.bottom);
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = area.left + ((point.x - graphLayOut.get_xMin()) * graphLayOut.get_xDivision());
				point.y = area.top + ((point.y - graphLayOut.get_yMin()) * graphLayOut.get_yDivision());
				point.y = area.bottom - (point.y - area.top);
				path.lineTo(point.x, point.y);
				path.addCircle(point.x, point.y, 3, Direction.CW);
				path.moveTo(point.x, point.y);
				point = null;
			}
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setStrokeWidth(2);
			paint.setColor(options.get_color() + (i * 50));
			paint.setAntiAlias(true);
			canvas.drawPath(path, paint);
			path.reset();
			points = null;
		}
		paint = null;
		path = null;
	}
	
	/**
	 * @param graphLayOut of type GraphLayout
	 * @param options of type GraphOptions
	 * @param canvas of type Canvas 
	 * @param area of type RectF
	 * @return of type null
	 * function which will draw the area graph
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public static void drawAreaChart(GraphLayout graphLayOut, GraphOptions options, Canvas canvas)
	{
		RectF area = graphLayOut.getGraphArea();
		if(options.is_drawGrid())
		{
			drawGrid(graphLayOut, options, canvas);
		}
		
		Paint paint = new Paint();
		Path path = new Path();
		for(int i = 0; i < graphLayOut.get_dataSet().size(); ++i)
		{
			PointF[] points = graphLayOut.get_dataSet().get(i);
			
			path.moveTo(area.left, area.bottom);
			
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = area.left + ((point.x - graphLayOut.get_xMin()) * graphLayOut.get_xDivision());
				point.y = area.top + ((point.y - graphLayOut.get_yMin()) * graphLayOut.get_yDivision());
				point.y = area.bottom - (point.y - area.top);
				path.lineTo(point.x, point.y);
				point = null;
			}
			
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			path.lineTo(area.width() + area.left, area.height() + area.top);
			path.lineTo(area.left, area.top + area.height());
			path.close();
			paint.setStrokeWidth(2);
			paint.setColor(options.get_color() + (i * 50));
			paint.setAntiAlias(true);
			canvas.drawPath(path, paint);
			path.reset();
			points = null;
		}
		paint = null;
		path = null;
	}
	
	/**
	 * @param graphLayOut of type GraphLayout
	 * @param options of type GraphOptions
	 * @param canvas of type Canvas 
	 * @param area of type RectF
	 * @return of type null
	 * function which will draw the pi chart
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public static void drawPieChart(GraphLayout graphLayOut, GraphOptions options, Canvas canvas)
	{
		RectF area = graphLayOut.getGraphArea();
		float totalAngle = 360;
		PointF[] points = graphLayOut.get_dataSet().get(0);
		float total = graphLayOut.getSum();
		PointF center = new PointF(area.left + (area.width() / 2), area.top + (area.height() / 2));
		
		float r = (area.width() < area.height()) ? (float)(area.width()) : (float)(area.height());
		
		float radius = (float)(r / 2.5);
		RectF rect = new RectF((center.x - radius), (center.y - radius), (center.x + radius), (center.y + radius));
		Path arcPaths = new Path();
		
		Path linePaths = new Path();
		
		float angle = 0; 
		Paint paint = new Paint();
		
		int color = options.get_color();
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);
		arcPaths.moveTo(center.x, center.y);
		for(int i = 0; i < points.length; ++i)
		{
			PointF point = new PointF(points[i].x, points[i].y);
			float percent = (point.y * 100) / total;
			PointF p = MathHelper.getPoint(center, radius, angle);
			linePaths.moveTo(center.x, center.y);
			linePaths.lineTo(p.x, p.y);
			arcPaths.moveTo(center.x, center.y);
			arcPaths.lineTo(p.x, p.y);
			arcPaths.addArc(rect, angle, totalAngle * (percent / 100));
			arcPaths.lineTo(center.x, center.y);
			
			float textAngle = angle + ((totalAngle * (percent / 100)) / 2);
			PointF textPoint = MathHelper.getPoint(center, r / 2, textAngle);
			
			angle += totalAngle * (percent / 100);
			paint.setColor(color);
			canvas.drawPath(arcPaths, paint);
			
			
			
			float fraction = (angle < 180) ? 0 : 1/2;
			
			drawTextAt(canvas, String.valueOf(percent) + "%", textPoint.x, textPoint.y, fraction, color);
			arcPaths.reset();
			point = MathHelper.getPoint(center, radius, textAngle);
			arcPaths.moveTo(point.x, point.y);
			arcPaths.lineTo(textPoint.x, textPoint.y);
			canvas.drawPath(arcPaths, paint);
			arcPaths.reset();
			point = null;
			color += 50;
		}
		paint.setColor(0xFFFFFFFF); 
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		linePaths.close();
		canvas.drawPath(linePaths, paint);
		linePaths.reset();
		drawShadow(canvas, center, paint, radius);
		paint.reset();
		arcPaths = null;
		linePaths = null;
		paint = null;
		points = null;
		center = null;
	}
	
	
	/**
	 * @param graphLayOut of type GraphLayout
	 * @param options of type GraphOptions
	 * @param canvas of type Canvas 
	 * @param area of type RectF
	 * @return of type null
	 * function which will draw the bar chart 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public static void drawBarChart(GraphLayout graphLayOut, GraphOptions options, Canvas canvas)
	{
		RectF area = graphLayOut.getGraphArea();
		Paint paint = new Paint();
		Path path = new Path();
		
		if(options.is_drawGrid())
		{
			drawGrid(graphLayOut, options, canvas);
		}
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		float right = (graphLayOut.get_xDivision() * graphLayOut.get_barWidth());
		right = right - (right/4);
		for(int i = 0; i < graphLayOut.get_dataSet().size(); ++i)
		{
			PointF[] points = graphLayOut.get_dataSet().get(i);
			
			path.moveTo(area.left, area.bottom);
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = area.left + ((point.x - graphLayOut.get_xMin()) * graphLayOut.get_xDivision());
				point.y = area.top + ((point.y - graphLayOut.get_yMin()) * graphLayOut.get_yDivision());
				point.y = area.bottom - (point.y - area.top);
				path.moveTo(point.x, area.bottom);
				path.addRect(point.x, point.y, point.x + right, area.bottom, Direction.CW);
			}
			paint.setColor(options.get_color() + (i * 50));
			canvas.drawPath(path, paint);
			path.reset();
			path.reset();
		}
	}
	
	
	/**
	 * 
	 * @author rajesh
	 *
	 */
	class SingleTonEnforcer
	{
		
	}
}
