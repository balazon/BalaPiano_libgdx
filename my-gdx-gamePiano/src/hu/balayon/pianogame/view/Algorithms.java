package hu.balayon.pianogame.view;

import com.badlogic.gdx.math.Rectangle;

public class Algorithms {
	
	
	//cohen sutherland algorithm-- more or less (not the fastest type)
	public static boolean lineSegmentIntersectsRect(Rectangle rect, float p1x, float p1y, float p2x, float p2y) {
		short code1=0,code2=0;
		float x1=p1x; float y1=p1y;
		float x2=p2x; float y2=p2y;
		code1= Code(x1,y1,rect);
		code2= Code(x2,y2,rect);
		if((code1 & code2)!=0) return false;
		if((code1 & 10) ==0 && (code2 & 10) ==0) return true;
		if((code1 & 5) ==0 && (code2 & 5) ==0) return true;
		if(code1 ==0 || code2==0) return true;

		float x_i, y_i;
		y_i=y1+(y2-y1)*(rect.x+rect.width-x1)/(x2-x1);
		if(y_i>= rect.y && y_i <= rect.y+rect.height) return true;
		y_i=y1+(y2-y1)*(rect.x-x1)/(x2-x1);
		if(rect.y<=y_i && y_i <= rect.y+rect.height) return true;
		x_i=x1+(x2-x1)*(rect.y+rect.height-y1)/(y2-y1);
		if(rect.x<=x_i && x_i<= rect.x+rect.width) return true;
		x_i=x1+(x2-x1)*(rect.y-y1)/(y2-y1);
		if(rect.x<=x_i && x_i<= rect.x+rect.width) return true;
		return false;
	}
	public static  short Code(float x_,float y_,Rectangle rect) {
		short c1,c2,c3,c4;
		c1 = (short) ((y_ > rect.y+rect.height)? 1: 0);
		c2 = (short) ((x_> rect.x+rect.width)? 1: 0);
		c3 = (short) ((y_< rect.y) ? 1: 0);
		c4 =  (short) ((x_ < rect.x) ? 1: 0);
		return (short) (c1 <<3  | c2 <<2  |c3 <<1  | c4);
	}
}
