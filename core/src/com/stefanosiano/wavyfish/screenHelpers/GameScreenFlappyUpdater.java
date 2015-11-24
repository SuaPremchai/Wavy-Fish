package com.stefanosiano.wavyfish.screenHelpers;

import com.stefanosiano.common.tochange.GameButtonContainer;
import com.stefanosiano.wavyfish.game.CommonApiController;
import com.stefanosiano.wavyfish.gameObjects.GameObjectContainer;
import com.stefanosiano.wavyfish.gameObjects.Obstacle;
import com.stefanosiano.common.tochange.SoundLoader;
import com.stefanosiano.wavyfish.screens.GameScreen;
import com.stefanosiano.wavyfish.utilities.Settings;
import com.stefanosiano.wavyfish.utilities.Enums.ButtonNames;

public class GameScreenFlappyUpdater extends GameScreenUpdater{

	public GameScreenFlappyUpdater(GameScreen gameScreen, int speedUpStep, int speedDownStep, int numberOfWallsToFinish, CommonApiController adsController) {
		super(gameScreen, speedUpStep, speedDownStep, numberOfWallsToFinish, adsController);
		setValues();
	}

	@Override
	protected void updateRunning(float delta) {
		time += delta;
		loadInterstitial();
		background.update(delta);
		background2.update(delta);
		lifeBar.update(delta);
		names = GameButtonContainer.getBtnPressed();
		
		for(Obstacle wallCouple : obstacles)
			wallCouple.update(delta);
		
		for(ButtonNames name : names){
			switch(name){
				case buttonFlappyBackground:
					fish.flap();
					if(Settings.SOUND_ENABLED)
						SoundLoader.flap.play();
					break;
				case hardwareBack:
					pause();
					break;
				default:
					break;
			}
		}
		fish.updateFlappy(delta);
		
		if(GameObjectContainer.fishCollide()){
			fishCollide();
		}
		
		if(GameObjectContainer.wallPassed()){
			wallPassed();
		}
		
		if(GameObjectContainer.wallFinishedPassed()){
			wallFinishedPassed();
		}
		
	}
	
	@Override
	protected void reset(){
		super.reset();
		setValues();
	}
	
	private void setValues(){
		switch(Settings.difficulty){
			case easy:
				this.fish.setFlapValues(2000, -600);
				break;
			case medium:
				this.fish.setFlapValues(2350, -700);
				break;
			case hard:
				this.fish.setFlapValues(2700, -800);
				break;
			case crazy:
				this.fish.setFlapValues(3050, -900);
				break;
			default:
				this.fish.setFlapValues(2500, -700);
				break;
		}
	}
}
