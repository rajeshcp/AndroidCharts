package com.sbgraph.graphutils;


public class GraphOptions {

	private int _color;
	
	private int _backgroundcolor;
	
	private boolean _shouldFill = true;
	
	private String _graphType;
	
	private int _gridLineColor = 0xFFFF0000;
	
	private boolean _drawGrid;
	
	
	/********************************************************/
	/******************Getters and Setters*******************/
	/********************************************************/
	
	/**
	 * @param of type null
	 * @return _color of type int
	 * getter function for _color
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public int get_color() {
		return _color;
	}
	
	
	/**
	 * @param _color of type int
	 * @return of type null
	 * setter function for _color
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_color(int _color) {
		this._color = _color;
	}

	/**
	 * @param of type null
	 * @return _backgroundcolor of type int
	 * getter function for _backgroundcolor
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public int get_backgroundcolor() {
		return _backgroundcolor;
	}

	/**
	 * @param _backgroundcolor of type int
	 * @return of type null
	 * setter function for _backgroundcolor
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_backgroundcolor(int _backgroundcolor) {
		this._backgroundcolor = _backgroundcolor;
	}

	/**
	 * @param of type null
	 * @return _shouldFill of type boolean
	 * getter function for _shouldFill
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public boolean is_shouldFill() {
		return _shouldFill;
	}

	/**
	 * @param _shouldFill of type boolean
	 * @return of type null
	 * setter function for _shouldFill
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_shouldFill(boolean _shouldFill) {
		this._shouldFill = _shouldFill;
	}
	
	/**
	 * @param of type null
	 * @return _graphType of type String
	 * getter function for _graphType
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public String get_graphType() {
		return _graphType;
	}

	/**
	 * @param _graphType of type String
	 * @return of type null
	 * setter function for _graphType
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public void set_graphType(String _graphType) {
		this._graphType = _graphType;
	}

	/**
	 * @param of type null
	 * @return _gridLineColor of type int
	 * getter function for _gridLineColor
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public int get_gridLineColor() {
		return _gridLineColor;
	}

	/**
	 * @param _gridLineColor of type int
	 * @return of type null
	 * setter function for _gridLineColor
	 * @author rajesh
	 * @date 30 jan 2012
	 */
	public void set_gridLineColor(int _gridLineColor) {
		this._gridLineColor = _gridLineColor;
	}

	/**
	 * @param of type null
	 * @return _drawGrid of type boolean
	 * getter function for _drawGrid
	 * @author rajesh
	  * @date 1 feb 2012
	 */
	public boolean is_drawGrid() {
		return _drawGrid;
	}

	/**
	 * @param _drawGrid of type boolean
	 * @return of type null
	 * setter function for _drawGrid
	 * @author rajesh
	 * @date 1 feb 2012
	 */
	public void set_drawGrid(boolean _drawGrid) {
		this._drawGrid = _drawGrid;
	}

	/**
	 * Constructor function
	 */
	public GraphOptions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
