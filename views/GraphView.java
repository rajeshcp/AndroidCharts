package com.sbgraph.views;

import com.sbgraph.graphutils.GraphLayout;
import com.sbgraph.graphutils.GraphOptions;
import com.sbgraph.graphutils.GraphTypes;
import com.sbgraph.mathutils.MathHelper;
import com.view.sample.SampleActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import android.util.Log;
import android.view.View;


public class GraphView extends View {

	protected Canvas _canvas;
	
	protected RectF _graphArea;
	
	protected GraphOptions _options;
	
	protected GraphLayout _graphLayout;
	
	protected SampleActivity activity;
	
	/**
	 * @param context
	 * Constructor function
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public GraphView(Context context) {
		super(context);
		activity = (SampleActivity) context;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(_canvas != canvas)
		{
			_canvas = canvas;
			activity.onViewReady();
		}else
		{
			drawCharts();
		}
	}
	
	
	/**
	 * @param of type null
	 * @return of type null
	 * @author rajesh
	 * function wjich will draw the charts
	 * @author rajesh
	 * @date 3 feb 2012
	 */
	protected void drawCharts()
	{
		if(_options != null)
		{
			drawBackGround();
			if(_options.get_graphType() == GraphTypes.LINE_CHART)
			{
				drawLineChart();
			}else if(_options.get_graphType() == GraphTypes.PIE_CHART)
			{
				drawPieChart();
			}else if(_options.get_graphType() == GraphTypes.AREA_CHART)
			{
				drawAreaChart();
			}else if(_options.get_graphType() == GraphTypes.BAR_CHART)
			{
				drawBarChart();
			}
		}
	}
	
	/**
	 * @param options of type GraphOptions
	 * @return of type null
	 * function which will render the graph
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void renderGraph(GraphOptions options, GraphLayout graphLayout)
	{
		_options = options;
		_graphLayout = graphLayout;
		_graphArea = _graphLayout.getGraphArea();
		invalidate();
	}
	
	
	/**
	 * @param of type null
	 * function which will draw the recrangle 
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	protected void drawBackGround() 
	{
		Paint paint = new Paint();
		paint.setColor(_options.get_backgroundcolor());
		_canvas.drawRect(_graphArea, paint);
		paint.reset();
		paint = null;
	}
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will draw the grid 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	protected void drawGrid()
	{
		
		Log.d("SampleActivity", "Xmin : " + String.valueOf(_graphLayout.get_xMin()) + "Xmax : " + String.valueOf(_graphLayout.get_xMax()) + "Ymin : " + String.valueOf(_graphLayout.get_yMin()) + "Ymax : " + String.valueOf(_graphLayout.get_yMax()));
		
		Path gridPath = new Path();
		Paint paint = new Paint();
		paint.setColor(_options.get_gridLineColor());
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		gridPath.moveTo(_graphArea.left, _graphArea.top);
		
		
		int limit = (_graphLayout.get_xNofticks() > _graphLayout.get_yNofticks()) ? _graphLayout.get_xNofticks() : _graphLayout.get_yNofticks();
		float xFactor = (_graphLayout.get_xMax() - _graphLayout.get_xMin()) / _graphLayout.get_xNofticks();
		float yFactor = (_graphLayout.get_yMax() - _graphLayout.get_yMin()) / _graphLayout.get_yNofticks();
		
		for(int i = 0; i < limit; i++)
		{
			if(i < _graphLayout.get_yNofticks())
			{
				float y = _graphArea.top + (i * (_graphArea.height() / _graphLayout.get_yNofticks()));
				gridPath.moveTo(_graphArea.left - 3, y);
				gridPath.lineTo(_graphArea.right, y);
				float vY = _graphLayout.get_yMin() + (i * yFactor);
				vY = Math.round(vY);
				drawTextAt(String.valueOf(vY), _graphArea.left - 10, _graphArea.bottom - (y - _graphArea.top), 1, _options.get_color());
			}
			if(i < _graphLayout.get_xNofticks())
			{
				float x = _graphArea.left + (i * (_graphArea.width() / _graphLayout.get_xNofticks()));
				gridPath.moveTo(x, _graphArea.top);
				gridPath.lineTo(x, _graphArea.bottom + 3);
				float vX = _graphLayout.get_xMin() + (i * xFactor);
				vX = Math.round(vX);
				drawTextAt(String.valueOf(vX), x, _graphArea.bottom + 10, (1/2), _options.get_color());
			}
		}
		gridPath.close();
		_canvas.drawPath(gridPath, paint);
		gridPath = null;
		paint = null;
	}
	
	/**
	 * @param text of type String 
	 * @param x of type float 
	 * @param y of type float 
	 * @param fraction of type float
	 * function which will draw the text on the graphics 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	protected void drawTextAt(String text, float x, float y, float fraction, int color)
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

		//paint.sett
		paint.setAntiAlias(true);
		_canvas.drawText(text, x - (bounds.width() * fraction), y, paint);
		//_canvas.drawt
	}
	
	/**
	 * 
	 */
	protected void drawShadow(PointF center, Paint paint, float radius)
	{
		paint.setColor(0x3F000000);
		paint.setStrokeWidth(3);
		_canvas.drawCircle(center.x + 1, center.y + 1, radius, paint);
		paint.setColor(0x2FFFFFFF);
		paint.setStrokeWidth(3);
		_canvas.drawCircle(center.x, center.y, radius - 2, paint);
	}
	
	/************************************************************/
	/****************fuctions for drawing charts*****************/
	/************************************************************/
	/**
	 * @param of type null
	 * @return of type null
	 * function which will draw the line graph
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	protected void drawLineChart()
	{
		if(_options.is_drawGrid())
		{
			drawGrid();
		}
		Path path = new Path();
		Paint paint = new Paint();
		for(int i = 0; i < _graphLayout.get_dataSet().size(); ++i)
		{
			PointF[] points = _graphLayout.get_dataSet().get(i);
			path.moveTo(_graphArea.left, _graphArea.bottom);
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = _graphArea.left + ((point.x - _graphLayout.get_xMin()) * _graphLayout.get_xDivision());
				point.y = _graphArea.top + ((point.y - _graphLayout.get_yMin()) * _graphLayout.get_yDivision());
				point.y = _graphArea.bottom - (point.y - _graphArea.top);
				path.lineTo(point.x, point.y);
				path.addCircle(point.x, point.y, 3, Direction.CW);
				path.moveTo(point.x, point.y);
				point = null;
			}
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			paint.setStrokeWidth(2);
			paint.setColor(_options.get_color() + (i * 50));
			paint.setAntiAlias(true);
			_canvas.drawPath(path, paint);
			path.reset();
			points = null;
		}
		paint = null;
		path = null;
	}
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will draw the area graph
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	protected void drawAreaChart()
	{
		if(_options.is_drawGrid())
		{
			drawGrid();
		}
		
		Paint paint = new Paint();
		Path path = new Path();
		for(int i = 0; i < _graphLayout.get_dataSet().size(); ++i)
		{
			PointF[] points = _graphLayout.get_dataSet().get(i);
			
			path.moveTo(_graphArea.left, _graphArea.bottom);
			
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = _graphArea.left + ((point.x - _graphLayout.get_xMin()) * _graphLayout.get_xDivision());
				point.y = _graphArea.top + ((point.y - _graphLayout.get_yMin()) * _graphLayout.get_yDivision());
				point.y = _graphArea.bottom - (point.y - _graphArea.top);
				path.lineTo(point.x, point.y);
				point = null;
			}
			
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			path.lineTo(_graphArea.width() + _graphArea.left, _graphArea.height() + _graphArea.top);
			path.lineTo(_graphArea.left, _graphArea.top + _graphArea.height());
			path.close();
			paint.setStrokeWidth(2);
			paint.setColor(_options.get_color() + (i * 50));
			paint.setAntiAlias(true);
			_canvas.drawPath(path, paint);
			path.reset();
			points = null;
		}
		paint = null;
		path = null;
	}
	
	
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will draw the pi chart
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	protected void drawPieChart()
	{
		PointF[] points = _graphLayout.get_dataSet().get(0);
		float total = _graphLayout.getSum();
		PointF center = new PointF(_graphArea.left + (_graphArea.width() / 2), _graphArea.top + (_graphArea.height() / 2));
		
		float r = (_graphArea.width() < _graphArea.height()) ? (float)(_graphArea.width()) : (float)(_graphArea.height());
		
		float radius = (float)(r / 2.5);
		RectF rect = new RectF((center.x - radius), (center.y - radius), (center.x + radius), (center.y + radius));
		Path arcPaths = new Path();
		
		Path linePaths = new Path();
		
		float angle = 0;
		Paint paint = new Paint();
		
		int color = _options.get_color();
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
			arcPaths.addArc(rect, angle, 360 * (percent / 100));
			arcPaths.lineTo(center.x, center.y);
			
			float textAngle = angle + ((360 * (percent / 100)) / 2);
			PointF textPoint = MathHelper.getPoint(center, r / 2, textAngle);
			
			angle += 360 * (percent / 100);
			paint.setColor(color);
			_canvas.drawPath(arcPaths, paint);
			
			
			
			float fraction = (angle < 180) ? 0 : 1/2;
			
			drawTextAt(String.valueOf(percent) + "%", textPoint.x, textPoint.y, fraction, color);
			arcPaths.reset();
			point = MathHelper.getPoint(center, radius, textAngle);
			arcPaths.moveTo(point.x, point.y);
			arcPaths.lineTo(textPoint.x, textPoint.y);
			_canvas.drawPath(arcPaths, paint);
			arcPaths.reset();
			point = null;
			color += 50;
		}
		paint.setColor(0xFFFFFFFF); 
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		linePaths.close();
		_canvas.drawPath(linePaths, paint);
		linePaths.reset();
		drawShadow(center, paint, radius);
		paint.reset();
		arcPaths = null;
		linePaths = null;
		paint = null;
		points = null;
		center = null;
	}
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will draw the bar chart 
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	protected void drawBarChart()
	{
		Paint paint = new Paint();
		Path path = new Path();
		
		if(_options.is_drawGrid())
		{
			drawGrid();
		}
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		float right = (_graphLayout.get_xDivision() * _graphLayout.get_barWidth());
		right = right - (right/4);
		for(int i = 0; i < _graphLayout.get_dataSet().size(); ++i)
		{
			PointF[] points = _graphLayout.get_dataSet().get(i);
			
			path.moveTo(_graphArea.left, _graphArea.bottom);
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = new PointF(points[j].x, points[j].y);
				point.x = _graphArea.left + ((point.x - _graphLayout.get_xMin()) * _graphLayout.get_xDivision());
				point.y = _graphArea.top + ((point.y - _graphLayout.get_yMin()) * _graphLayout.get_yDivision());
				point.y = _graphArea.bottom - (point.y - _graphArea.top);
				path.moveTo(point.x, _graphArea.bottom);
				path.addRect(point.x, point.y, point.x + right, _graphArea.bottom, Direction.CW);
			}
			paint.setColor(_options.get_color() + (i * 50));
			_canvas.drawPath(path, paint);
			path.reset();
			path.reset();
		}
	}
	
	
}
