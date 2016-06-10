package com.balacraft.balapiano.view;

import com.badlogic.gdx.math.Rectangle;

public class Algorithms {

	static final short INSIDE = 0; // 0000
	static final short LEFT = 1;   // 0001
	static final short RIGHT = 2;  // 0010
	static final short BOTTOM = 4; // 0100
	static final short TOP = 8;    // 1000

	//cohen sutherland algorithm -- more or less (we only need to know if there is an intersection at all)
	public static boolean lineSegmentIntersectsRect(float x1, float y1, float x2, float y2, float xmin, float ymin, float xmax, float ymax) {
		short code1 = Code(x1, y1, xmin, ymin, xmax, ymax);
		short code2 = Code(x2, y2, xmin, ymin, xmax, ymax);

		//both are TOP, or both are BOTTOM , etc.
		if((code1 & code2) != 0) return false;

		//both are in the middle vertically or horizontally : | shape or - shape
		if( ((code1 | code2) & (RIGHT | LEFT)) == 0) return true;
		if( ((code1 | code2) & (TOP | BOTTOM)) == 0) return true;

		//any of them is inside
		if(code1 == 0 || code2 == 0) return true;

		//calculate intersections and check if they are in the middle
		//y = y1 + (x - x1) * slope

		float x, y;
		y = y1 + (xmax - x1) * (y2 - y1) /(x2 - x1);
		if(ymin <= y && y <= ymax) return true;
		y = y1 + (xmin - x1) * (y2 - y1) /(x2 - x1);
		if(ymin <= y && y <= ymax) return true;
		x = x1 + (ymax - y1) * (x2 - x1) / (y2 - y1);
		if(xmin <= x && x <= ymax) return true;
		x = x1 + (ymin - y1) * (x2 - x1) / (y2 - y1);
		if(xmin <= x && x <= ymax) return true;
		return false;
	}
	private static short Code(float x,float y,float xmin, float ymin, float xmax, float ymax) {
		short res = INSIDE;
		if(y > ymax) {
			res |= TOP;
		} else if(y < ymin) {
			res |= BOTTOM;
		}
		if(x > xmax) {
			res |= RIGHT;
		} else if(x < xmin) {
			res |= LEFT;
		}
		return res;
	}
}
