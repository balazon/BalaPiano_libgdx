package com.balacraft.balapiano.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class WhiteButtonView extends ButtonView{
	Rectangle srcu2;
	Rectangle srcp2;
	public WhiteButtonView(Button btn, Texture unpressed,
			Texture pressed) {
		super(btn, unpressed, pressed);
	}
	@Override
	public void draw(SpriteBatch batch) {
		Rectangle r2 =super.btn.getRects()[1];
		if(btn.isPressed()) {
			batch.draw(texture_pressed,btn.r1.x,btn.r1.y,btn.r1.width,btn.r1.height,
					(int)srcp.x,(int)srcp.y,(int)srcp.width,(int)srcp.height,false,false);
			batch.draw(texture_pressed,r2.x,r2.y,r2.width,r2.height,
					(int)srcp2.x,(int)srcp2.y,(int)srcp2.width,(int)srcp2.height,false,false);

		}else {
			batch.draw(texture_unpressed,r2.x,r2.y,r2.width,r2.height,
					(int)srcu2.x,(int)srcu2.y,(int)srcu2.width,(int)srcu2.height,false,false);
			batch.draw(texture_unpressed,btn.r1.x,btn.r1.y,btn.r1.width,btn.r1.height,
					(int)srcu.x,(int)srcu.y,(int)srcu.width,(int)srcu.height,false,false);
		}
	}
	

}
