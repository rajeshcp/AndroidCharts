package com.sbgraph.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.graphdemo.views.GraphDemoActivity;
import com.sbgraph.graphutils.GraphLayout;
import com.sbgraph.graphutils.GraphOptions;
import com.sbgraph.graphutils.GraphTypes;
import com.sbgraph.mathutils.PlotGraphHelper;


public class GraphView extends View {

	protected Canvas _canvas;
	
	protected RectF _graphArea;
	
	protected GraphOptions _options;
	
	protected GraphLayout _graphLayout;
	
	protected GraphDemoActivity activity;
	
	/**
	 * @param context
	 * Constructor function
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public GraphView(Context context) {
		super(context);
		activity = (GraphDemoActivity) context;
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
				PlotGraphHelper.drawLineChart(_graphLayout, _options, _canvas);
			}else if(_options.get_graphType() == GraphTypes.PIE_CHART)
			{
				PlotGraphHelper.drawPieChart(_graphLayout, _options, _canvas);
			}else if(_options.get_graphType() == GraphTypes.AREA_CHART)
			{
				PlotGraphHelper.drawAreaChart(_graphLayout, _options, _canvas);
			}else if(_options.get_graphType() == GraphTypes.COLUMN_CHART)
			{
				PlotGraphHelper.drawBarChart(_graphLayout, _options, _canvas);
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
}
