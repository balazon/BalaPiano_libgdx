package com.balacraft.balapiano.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public /*abstract*/ class ButtonView implements Drawable{

	protected Texture texture_unpressed;
	protected Texture texture_pressed;
	protected Rectangle srcu;
	protected Rectangle srcp;
	
	protected Button btn;
	
	public ButtonView(Button btn,Texture unpressed, Texture pressed) {
		this.btn=btn;
		this.texture_unpressed=unpressed;
		this.texture_pressed=pressed;
	}
	
	public void setTextRegion(Texture tex1, Texture tex2) {
		texture_unpressed = tex1;
		texture_pressed = tex2;
	}
	@Override
	public void draw(SpriteBatch batch) {	
		if(btn.isPressed()) {
			batch.draw(texture_pressed,btn.r1.x,btn.r1.y,btn.r1.width,btn.r1.height,
					(int)srcp.x,(int)srcp.y,(int)srcp.width,(int)srcp.height,false,false);
		}else {
			batch.draw(texture_unpressed,btn.r1.x,btn.r1.y,btn.r1.width,btn.r1.height,
				(int)srcu.x,(int)srcu.y,(int)srcu.width,(int)srcu.height,false,false);
		}
		//batch.draw(texture_unpressed, 0, 0);
		//batch.draw(texture_pressed,0,0);
		//batch.draw(texture_unpressed, 0, 0, 1000, 500);
		
	}

}
