package hu.balayon.pianogame.view;

import hu.balayon.pianogame.soundengine.ChordPlayer;
import hu.balayon.pianogame.soundengine.Note;
import hu.balayon.pianogame.soundengine.SoundSystem;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ButtonContainer implements Drawable{
	
//	private TextureRegion whbot;
//	private TextureRegion whup;
//	private TextureRegion bl;
//	private TextureRegion whbotp;
//	private TextureRegion whupp;
//	private TextureRegion blp;
//	private TextureRegion wh2p;
//	private TextureRegion wh3p;
//	private TextureRegion bl1p;
	Texture tru;
	Texture trp;
	private ArrayList<ButtonView> buttons;
	private ArrayList<WhiteButtonView> whitebtns;
	
	private WhiteKey[] whiteys;
	private BlackKey[] blacks;
	private ChordPitchButton[] chordpitches;
	private ModifierButton[] mods;
	private ChordVariationButton[] vars;
	private OctaveButton[] octs;
	private BPMButton[] bpms;
	
	private  int texW,texH;
	private  int w, h;
	float c = 1.0f;
	private SoundSystem ss;
	
	public ButtonContainer(SoundSystem ss, Texture tex1, Texture tex2, int tw, int th){
		//super()
		this.ss = ss;
		createButtons();
		texW = tw;
		texH = th;
		setTexts(tex1,tex2);
		
		
	}
	public void setTexts(Texture tex1, Texture tex2) {
		tru = tex1;
		trp = tex2;
		for(ButtonView bv: buttons) {
			bv.setTextRegion(tru, trp);
		}
		Rectangle r1 = new Rectangle(0,0,texW*(2.0f/19.0f),texH*(8.0f/12.0f));
		Rectangle r2 =  new Rectangle(texW*(2.0f/19.0f),0,texW*(3.0f/19.0f),texH*(4.0f/12.0f));
		for(int i = 0; i< 8; i++) {
			whitebtns.get(i).srcu = r1;
			whitebtns.get(i).srcp = r1;
			whitebtns.get(i).srcu2 = r2;
			whitebtns.get(i).srcp2 = r2;
		}
		Rectangle r3 =new Rectangle(texW*(5.0f/19.0f),0,texW*(2.0f/19.0f),texH*(8.0f/12.0f));
		for(int i = 8; i< 14; i++) {
			buttons.get(i).srcu = r3;
			buttons.get(i).srcp = r3;
		}
		
		//CDEF
		for(int i = 0; i< 4; i++) {
			Rectangle r4 =new Rectangle(texW*(7.0f/19.0f),i*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
			buttons.get(i+14).srcu = r4;
			buttons.get(i+14).srcp = r4;
		}
		//GAHC
		for(int i = 0; i< 4; i++) {
			Rectangle r5 =new Rectangle(texW*(10.0f/19.0f),i*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
			buttons.get(i+18).srcu = r5;
			buttons.get(i+18).srcp = r5;
		}
		//vars
		Rectangle r6 =new Rectangle(texW*(13.0f/19.0f),1*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r7 =new Rectangle(texW*(16.0f/19.0f),1*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r8 =new Rectangle(texW*(13.0f/19.0f),2*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r9 =new Rectangle(texW*(16.0f/19.0f),2*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		//mods
		Rectangle r10 =new Rectangle(texW*(13.0f/19.0f),3*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r11 =new Rectangle(texW*(16.0f/19.0f),3*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		//octs
		Rectangle r12 =new Rectangle(texW*(13.0f/19.0f),0,texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r13 =new Rectangle(texW*(16.0f/19.0f),0,texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		//bpms
		Rectangle r14 =new Rectangle(texW*(1.0f/19.0f),3*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		Rectangle r15 =new Rectangle(texW*(4.0f/19.0f),3*texH*(1.0f/4.0f),texW*(3.0f/19.0f),texH*(1.0f/4.0f));
		buttons.get(22).srcu = r10;
		buttons.get(22).srcp = r10;
		buttons.get(23).srcu = r11;
		buttons.get(23).srcp = r11;
		buttons.get(24).srcu = r6;
		buttons.get(24).srcp = r6;
		buttons.get(25).srcu = r7;
		buttons.get(25).srcp = r7;
		buttons.get(26).srcu = r8;
		buttons.get(26).srcp = r8;
		buttons.get(27).srcu = r9;
		buttons.get(27).srcp = r9;
		buttons.get(28).srcu = r12;
		buttons.get(28).srcp = r12;
		buttons.get(29).srcu = r13;
		buttons.get(29).srcp = r13;
		buttons.get(30).srcu = r14;
		buttons.get(30).srcp = r14;
		buttons.get(31).srcu = r15;
		buttons.get(31).srcp = r15;
		
		
	}
	
	

	public void pressed(int x, int y) {
		for(int i = 0; i< buttons.size(); i++) {
			buttons.get(i).btn.pressed(x, y);
		}
	}
	public void released(int x, int y) {
		for(int i = 0; i< buttons.size(); i++) {
			buttons.get(i).btn.released(x,y);
		}
	}

	public void draggedFromTo(int x, int y, int u, int v) {
		// TODO Auto-generated method stub
		for(int i = 0; i< buttons.size(); i++) {
			buttons.get(i).btn.draggedFromTo(x, y, u,v);
		}
	}
	
	public void setDimension(int w, int h) {
		this.w=w; this.h=h;
		updateButtons();
	}
	
	private void createButtons() {
		// TODO Auto-generated method stub
		
		// create the buttons
		
		
		whiteys = new WhiteKey[8];
		
		whiteys[0] = new WhiteKey(new Note(0,0,1000,false),ss);
		whiteys[1] = new WhiteKey(new Note(2,0,1000,false),ss);
		whiteys[2] = new WhiteKey(new Note(4,0,1000,false),ss);
		whiteys[3] = new WhiteKey(new Note(5,0,1000,false),ss);
		whiteys[4] = new WhiteKey(new Note(7,0,1000,false),ss);
		whiteys[5] = new WhiteKey(new Note(9,0,1000,false),ss);
		whiteys[6] = new WhiteKey(new Note(11,0,1000,false),ss);
		whiteys[7] = new WhiteKey(new Note(12,0,1000,false),ss);
		
		blacks = new BlackKey[6];
		
		blacks[0] = new BlackKey(new Note(1,0,1000,false),ss);
		blacks[1] = new BlackKey(new Note(3,0,1000,false),ss);
		blacks[2] = new BlackKey(new Note(6,0,1000,false),ss);
		blacks[3] = new BlackKey(new Note(8,0,1000,false),ss);
		blacks[4] = new BlackKey(new Note(10,0,1000,false),ss);
		blacks[5] = new BlackKey(new Note(13,0,1000,false),ss);
		
		chordpitches = new ChordPitchButton[8];
		
		chordpitches[0] = new ChordPitchButton('C', 0, ss);
		chordpitches[1] = new ChordPitchButton('D', 2, ss);
		chordpitches[2] = new ChordPitchButton('E', 4, ss);
		chordpitches[3] = new ChordPitchButton('F', 5, ss);
		chordpitches[4] = new ChordPitchButton('G', 7, ss);
		chordpitches[5] = new ChordPitchButton('A', 9, ss);
		chordpitches[6] = new ChordPitchButton('H', 11, ss);
		chordpitches[7] = new ChordPitchButton('C', 12, ss);
		
		mods = new ModifierButton[2];
		
		mods[0] = new ModifierButton(true, ss);
		mods[1] = new ModifierButton(false, ss);
		
		vars = new ChordVariationButton[4];
		
		vars[0] = new ChordVariationButton(ChordPlayer.MAJOR,ss);
		vars[1] = new ChordVariationButton(ChordPlayer.MINOR,ss);
		vars[2] = new ChordVariationButton(ChordPlayer.MAJOR7,ss);
		vars[3] = new ChordVariationButton(ChordPlayer.MINOR7,ss);
		
		octs = new OctaveButton[2];
		octs[0] = new OctaveButton(ss,-1);
		octs[1] = new OctaveButton(ss,1);
		
		bpms = new BPMButton[2];
		bpms[0] = new BPMButton(ss, 1);
		bpms[1] = new BPMButton(ss, -1);
		
		buttons = new ArrayList<ButtonView>();
		whitebtns = new ArrayList<WhiteButtonView>();
		for(int i = 0; i< 8 ; i++) {
			WhiteButtonView wbv = new WhiteButtonView(whiteys[i],null,null);
			buttons.add(wbv);
			whitebtns.add(wbv);
		}
		for(int i = 0; i< 6 ; i++) {
			buttons.add(new ButtonView(blacks[i],null,null));
		}
		for(int i = 0; i< 8 ; i++) {
			buttons.add(new ButtonView(chordpitches[i],null,null));
		}
		buttons.add(new ButtonView(mods[0],null,null));
		buttons.add(new ButtonView(mods[1],null,null));
		for(int i = 0; i< 4; i++) {
			buttons.add(new ButtonView(vars[i],null,null));
		}
		buttons.add(new ButtonView(octs[0],null,null));
		buttons.add(new ButtonView(octs[1],null,null));
		
		buttons.add(new ButtonView(bpms[0],null,null));
		buttons.add(new ButtonView(bpms[1],null,null));
	}
	

	private void updateButtons() {
		// TODO setting buttons' sizes
		if(w!=0 && h!=0){
			float cw = (float) (w/10.0);
			float ch = (float) (h/5.0);
			float l = (float) (cw * 2.0/3.0) * c; // c between 0.8 and 1.2 for say
			float t = (float) (3.0f*ch * 8.0 / 9.0);//h*8/15
			float wh1y = (float) (ch*7.0/3.0);
			float wh1h = (float) (ch*8.0/3.0);
			//float nw = (float) (cw /3.0);
			whiteys[0].r1 = new Rectangle(0,wh1y,cw-l/2.0f, wh1h);
			whiteys[0].r2 = new Rectangle(0,ch,cw,t/2.0f);
			
			whiteys[1].r1 = new Rectangle(l/2.0f+cw*1.0f,wh1y,cw-l, wh1h);
			whiteys[1].r2 = new Rectangle(cw*1.0f,ch,cw,t/2.0f);
			
			whiteys[2].r1 = new Rectangle(l/2.0f+cw*2.0f,wh1y,cw-l/2.0f, wh1h);
			whiteys[2].r2 = new Rectangle(cw*2.0f,ch,cw,t/2.0f);
			
			whiteys[3].r1 = new Rectangle(cw*3.0f,wh1y,cw-l/2.0f, wh1h);
			whiteys[3].r2 = new Rectangle(cw*3.0f,ch,cw,t/2.0f);
			
			whiteys[4].r1 = new Rectangle(l/2.0f+cw*4.0f,wh1y,cw-l, wh1h);
			whiteys[4].r2 = new Rectangle(cw*4.0f,ch,cw,t/2.0f);
			
			whiteys[5].r1 = new Rectangle(l/2.0f+cw*5.0f,wh1y,cw-l, wh1h);
			whiteys[5].r2 = new Rectangle(cw*5.0f,ch,cw,t/2.0f);
			
			whiteys[6].r1 = new Rectangle(l/2.0f+cw*6.0f,wh1y,cw-l/2.0f, wh1h);
			whiteys[6].r2 = new Rectangle(cw*6.0f,ch,cw,t/2.0f);
			
			whiteys[7].r1 = new Rectangle(cw*7.0f,wh1y,cw-l/2.0f, t);
			whiteys[7].r2 = new Rectangle(cw*7.0f,ch,cw,t/2.0f);
			
			blacks[0].r1 = new Rectangle(cw-l/2.0f,wh1y,l,wh1h);
			blacks[1].r1 = new Rectangle(2.0f*cw-l/2.0f,wh1y,l,wh1h);
			blacks[2].r1 = new Rectangle(4.0f*cw-l/2.0f,wh1y,l,wh1h);
			blacks[3].r1 = new Rectangle(5.0f*cw-l/2.0f,wh1y,l,wh1h);
			blacks[4].r1 = new Rectangle(6.0f*cw-l/2.0f,wh1y,l,wh1h);
			blacks[5].r1 = new Rectangle(8.0f*cw-l/2.0f,wh1y,l/2.0f,wh1h);
			
			chordpitches[0].r1 = new Rectangle(0,0,cw,ch);
			chordpitches[1].r1 = new Rectangle(1.0f*cw,0,cw,ch);
			chordpitches[2].r1 = new Rectangle(2.0f*cw,0,cw,ch);
			chordpitches[3].r1 = new Rectangle(3.0f*cw,0,cw,ch);
			chordpitches[4].r1 = new Rectangle(4.0f*cw,0,cw,ch);
			chordpitches[5].r1 = new Rectangle(5.0f*cw,0,cw,ch);
			chordpitches[6].r1 = new Rectangle(6.0f*cw,0,cw,ch);
			chordpitches[7].r1 = new Rectangle(7.0f*cw,0,cw,ch);
			
			mods[0].r1 = new Rectangle(8.0f*cw,0,cw,ch);
			mods[1].r1 = new Rectangle(9.0f*cw,0,cw,ch);
			
			vars[0].r1 = new Rectangle(8.0f*cw,3*ch,cw,ch);
			vars[1].r1 = new Rectangle(9.0f*cw,3*ch,cw,ch);
			vars[2].r1 = new Rectangle(8.0f*cw,2*ch,cw,ch);
			vars[3].r1 = new Rectangle(9.0f*cw,2*ch,cw,ch);
			
			octs[0].r1 =new Rectangle(8.0f*cw,4*ch,cw,ch);
			octs[1].r1 =new Rectangle(9.0f*cw,4*ch,cw,ch);
			
			bpms[0].r1 = new Rectangle(8.0f*cw,1.0f*ch,cw,ch);
			bpms[1].r1 = new Rectangle(9.0f*cw,1.0f*ch,cw,ch);
		}
		
	}



	@Override
	public void draw(SpriteBatch batch) {
		for(int i = 0; i< buttons.size(); i++) {
			buttons.get(i).draw(batch);
		}
	}



	
}
