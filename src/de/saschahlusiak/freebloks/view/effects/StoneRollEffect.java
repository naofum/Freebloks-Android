package de.saschahlusiak.freebloks.view.effects;

import javax.microedition.khronos.opengles.GL10;

import de.saschahlusiak.freebloks.model.Spiel;
import de.saschahlusiak.freebloks.model.Stone;
import de.saschahlusiak.freebloks.view.BoardRenderer;

public class StoneRollEffect extends AbsStoneEffect {
	float z, vz;
	boolean done = false;
	
	public StoneRollEffect(Stone stone, int player, int x, int y, float z, float vz) {
		super(stone, player, x, y);
		this.z = z;
		this.vz = vz;
	}
	
	@Override
	public boolean isDone() {
		return done;
	}
	
	@Override
	public boolean execute(float elapsed) {
		final float EPSILON = 0.10f;
		if (z > EPSILON || (vz > EPSILON || vz < -EPSILON)) {
			z -= vz * elapsed;
			vz += elapsed * 62.0f;
			if (z < 0.0f) {
				vz *= -0.55f;
				z = 0.0f;
				if (vz > -6.0f)
					vz = 0.0f;
			}
		} else {
			z = 0.0f;
			done = true;
		}
		
		super.execute(elapsed);
		return true;
	}

	@Override
	public void render(GL10 gl, BoardRenderer renderer) {
		gl.glPushMatrix();
		gl.glTranslatef(0, z, 0);
		
	    gl.glTranslatef(
	    		-BoardRenderer.stone_size * (float)(Spiel.DEFAULT_FIELD_SIZE_X - 1) + BoardRenderer.stone_size * 2.0f * (float)x,
	    		0,
	    		-BoardRenderer.stone_size * (float)(Spiel.DEFAULT_FIELD_SIZE_Y - 1) + BoardRenderer.stone_size * 2.0f * (float)y);
		
		renderer.renderPlayerStone(gl, player, stone, BoardRenderer.DEFAULT_ALPHA);
		gl.glPopMatrix();
	}
}