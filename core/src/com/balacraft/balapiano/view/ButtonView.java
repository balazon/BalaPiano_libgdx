package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public class ButtonView implements Drawable{

	protected Texture texture_unpressed;
	protected Texture texture_pressed;
	//rectangles for the parts of the texture that will be drawn
	protected Rectangle srcu;
	protected Rectangle srcp;

	
	protected Button btn;
	
	public ButtonView(Button btn,Texture unpressed, Texture pressed) {
		this.btn=btn;
		this.texture_unpressed=unpressed;
		this.texture_pressed=pressed;
	}
	
	public void setTexes(Texture tex1, Texture tex2) {
		texture_unpressed = tex1;
		texture_pressed = tex2;
	}

	public void set


	@Override
	public void draw(SpriteBatch batch) {
		if(btn.isPressed()) {
			for(Rectangle r : btn.clickArea) {
				batch.draw(texture_pressed,r.x,r.y,r.width,r.height,
						(int)srcp.x,(int)srcp.y,(int)srcp.width,(int)srcp.height,false,false);
			}
		} else {
			for(Rectangle r : btn.clickArea) {
				batch.draw(texture_unpressed,r.x,r.y,r.width,r.height,
						(int)srcp.x,(int)srcp.y,(int)srcp.width,(int)srcp.height,false,false);
			}
		}
	}

}
