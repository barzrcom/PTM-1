package viewModel;

import java.util.Observable;
import java.util.Observer;

import model.GameModel;


public class PipeGameViewModel extends Observable implements Observer {
	
	GameModel m;
	
	public PipeGameViewModel(GameModel m) {
		this.m = m;
	}

	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub
	}
}
