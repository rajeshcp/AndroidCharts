package com.sbgraph.graphutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;

public class GraphLayout {
	
	
	private List<PointF[]> _dataSet = new ArrayList<PointF[]>();
	
	private List<String> _dataNames = new ArrayList<String>();
	
    private float _xOffset;
	
	private float _yOffset;
	
	private int _length = 0;
	private float _yMax;
	
	private float _xMin;
	private float _xMax;
	private float _yMin = 0;
	
	private int _width;
	
	private int _height;
	
	private int _xNofticks = 5;
	private int _yNofticks = 5;
	
	
	private float _leftGap = 40;
	private float _bottomGap = 40;
	
	private float _barWidth;
	
	private RectF rect;
	
	/**
	 * @param of type null
	 * @return _width of type int
	 * getter function for _width
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public int get_width() {
		return _width;
	}

	/**
	 * @param _width of type int
	 * @return of type null
	 * setter function for _width
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_width(int _width) {
		this._width = _width;
	}


	/**
	 * @param of type null
	 * @return _height of type int
	 * getter function for _height
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public int get_height() {
		return _height;
	}

	/**
	 * @param _height of type int
	 * @return of type null
	 * setter function for _height
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_height(int _height) {
		this._height = _height;
	}
	
	/**
	 * @param of type null
	 * @return _xMin of type float
	 * getter function for _xMin
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_xMin() {
		return _xMin;
	}
	
	/**
	 * @param of type null
	 * @return _xMax of type float
	 * getter function for _xMax
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_xMax() {
		return _xMax;
	}
	
	/**
	 * @param of type null
	 * @return _yMin of type float
	 * getter function for _yMin
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_yMin() {
		return _yMin;
	}
	
	/**
	 * @param of type null
	 * @return _yMax of type float
	 * getter function for _yMax
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_yMax() {
		return _yMax;
	}
	
	/**
	 * @param of type null
	 * @return _dataSet of type List<Point[]>
	 * getter function for _dataSet
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public List<PointF[]> get_dataSet() {
		return _dataSet;
	}

	/**
	 * @param of type null
	 * @return _yDivision of type float
	 * getter function for _yDivision
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_xDivision() {
		return _xOffset;
	}

	/**
	 * @param of type null
	 * @return _yDivision of type float
	 * getter function for _yDivision
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float get_yDivision() {
		return _yOffset;
	}
	

	public int get_xNofticks() {
		return _xNofticks;
	}

	public void set_xNofticks(int _xNofticks) {
		this._xNofticks = _xNofticks;
	}

	public int get_yNofticks() {
		return _yNofticks;
	}

	public void set_yNofticks(int _yNofticks) {
		this._yNofticks = _yNofticks;
	}

	public float get_leftGap() {
		return _leftGap;
	}

	public void set_leftGap(float _leftGap) {
		this._leftGap = _leftGap;
	}

	public float get_bottomGap() {
		return _bottomGap;
	}

	public void set_bottomGap(float _bottomGap) {
		this._bottomGap = _bottomGap;
	}

	public float get_barWidth() {
		return _barWidth;
	}

	/**
	 * Constructor function
	 */
	public GraphLayout() {
		super();
	}
	
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will clear the layouts 
	 * @author rajesh
	 * @date 3 feb 2012
	 */
	public void clear()
	{
		_barWidth = 0;
		_xMax = 0;
		_xMin = 0;
		_yMax = 0;
		_yMin = 0;
	}
	
	/**
	 * @param data of type Array
	 * @return of type null
	 * function which will add the data to the dataSet
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void addData(PointF[] data, String name)
	{
		PointCompare pointCompare = new PointCompare();
		Arrays.sort(data, pointCompare);
		if(_dataNames.indexOf(name) != -1)
		{
			int index = _dataNames.indexOf(name);
			_dataSet.add(index, data);
		}else
		{
			_dataNames.add(name);
			_dataSet.add(_dataNames.indexOf(name), data);
		}
		_length = (_length < data.length) ? data.length : _length;	
		pointCompare = null;
	}
	
	/**
	 * @param options of type GrpahOptions
	 * @return of type null
	 * function which will find the _xDivisions and _yDivisions
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void evaluate(GraphOptions options)
	{
		defineGraphArea(options.get_graphType());
		RectF rect = getGraphArea();
		setMinMax();
		
		if(options.get_graphType() == GraphTypes.COLUMN_CHART)
		{
			determineBarWidth();
			float factor = (rect.width() - _barWidth) / (_xMax - _xMin);
			factor = factor * _barWidth;
			_xOffset = (rect.width() - factor) / (_xMax - _xMin);
			_yOffset = (rect.height()) / (_yMax - _yMin);
		}else
		{
			_xOffset = (rect.width()) / (_xMax - _xMin);
			_yOffset = (rect.height()) / (_yMax - _yMin);
		}
		rect = null;	
	}
	
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will set the min max values for the graph
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	protected void setMinMax()
	{
		for(int i = 0; i < _dataSet.size(); ++i)
		{
			PointF[] points = _dataSet.get(i);
			for(int j = 0; j < points.length; ++j)
			{
				PointF point = points[j];
				
				if(j == 0 && i == 0)
				{
					_yMax = _yMin = point.y;
					_xMax = _xMin = point.x;
				}else
				{
					_yMax = (_yMax < point.y) ? point.y : _yMax;
					_yMin = (_yMin > point.y) ? point.y : _yMin;
					_xMax = (_xMax < point.x) ? point.x : _xMax;
					_xMin = (_xMin > point.x) ? point.x : _xMin;
				}
				point = null;
			}
			points = null;
		}
	}
	
	/**
	 * @param of type null
	 * @return sum of type float
	 * function which will return the sum of all the y values 
	 * @author rajesh
	 * @date 31 jan 2012
	 */
	public float getSum()
	{
		PointF[] points = _dataSet.get(0);
		float sum = 0;
		for(int i = 0; i < points.length; ++i)
		{
			PointF point = points[i];
			sum += point.y;
		}
		return sum;
	}
	
	
	/**
	 * @param of type null
	 * @return rect of type Rect
	 * function which will return the area of the graph
	 * @author rajesh
	 */
	public RectF getGraphArea()
	{
		return rect;
	}
	
	/**
	 * @param graphType of type String 
	 * @return of type bull
	 * function which will define the graphArea
	 * @author rajesh
	 * @date 3 feb 2012
	 */
	private void defineGraphArea(String type)
	{
		//rect = (type != GraphTypes.PIE_CHART) ? new RectF(_leftGap, 10, _width - 10, _height - _bottomGap) : new RectF(0, 0, _width, _height - 20);
		rect = new RectF(_leftGap, 10, _width - 10, _height - _bottomGap);
	}
	
	
	
	/**
	 * @param of type null
	 * @return of type null
	 * function which will set the barWidth
	 * @author rajesh
	 * @date 2 feb 2012
	 */
	protected void determineBarWidth()
	{
		float smallestDiff = 0;
		PointF prevPoint = new PointF(0, 0);
		for(int i = 0; i < _dataSet.size(); i++)
		{
			PointF[] points = _dataSet.get(i);
			for(int j = 0; j < points.length; j++)
			{
				PointF point = points[j];
				float diff = point.x - prevPoint.x;
				if(smallestDiff == 0)
				{
					smallestDiff = diff;
				}else if(smallestDiff > diff)
				{
					smallestDiff = diff;
				}
				prevPoint = point;
				point = null;
			}
			points = null;
		}
		prevPoint = null;
		_barWidth = smallestDiff;
	}
	
	
	
	//dummy
	public PointF[] populateList(int size)
	{
		PointF[] list = new PointF[size];
		float x = 0;
		float y = 0;
		for(int i = 0; i < size; ++i)
		{
			x += 1;//Math.round(Math.random() * 100);
			y = Math.round(Math.random() * 200);
			PointF p = new PointF(x, y);
			list[i] = p;
		}
		return list;
	}

}
/**
 * class to handle the sorting of points
 * @author rajesh
 * @date 2 feb 2012
 */
class PointCompare implements Comparator<PointF> {
	
	/**
	 * @param a of type PointF
	 * @param b of type PointF
	 * function which will compare two points x values 
	 * @author rajesh
	 * @date 2 feb 2012
	 */
	public int compare(final PointF a, final PointF b) {
	    if (a.x < b.x) {
	        return -1;
	    }
	    else if (a.x > b.x) {
	        return 1;
	    }
	    else {
	        return 0;
	    }
	}
}

