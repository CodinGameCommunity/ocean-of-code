package com.codingame.game.commands;

import java.util.ArrayList;
import java.util.List;

import com.codingame.game.GameException;
import com.codingame.game.GridManager;
import com.codingame.game.Player;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.Circle;
import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Text;

public abstract class Power extends Command {

	private List<Circle> graphicCharges = new ArrayList<Circle>();

	public int charge = 0;

	@Override
	public void init(Player player, Player opponent, MultiplayerGameManager<Player> gameManager, GraphicEntityModule entityManager, GridManager gridManager) {
		super.init(player, opponent, gameManager, entityManager, gridManager);
		int y = 550 + 120 * getPowerIndex();

		entityManager
				.createText(getName())
				.setFontFamily("Arial")
				.setX(player.getIndex() == 1 ? 30 : 1890)
				.setY(y-50)
				.setAnchorX(player.getIndex() == 1? 0 : 1)
				.setFontSize(30)
				.setAnchorY(0.5)
				.setZIndex(5)
				.setFontWeight(Text.FontWeight.BOLD)
				.setFillColor(0xefefef);

		for (int i = 0; i < getMaxValue(); i++) {
			
			int x = 50 + 55 * i;

			if (player.getIndex() == 1) {
				x = 1920 - (50 + 55 * i);
			}

			//entityManager.createCircle().setX(x).setY(y)
			//		.setFillColor(0x555555).setRadius(22).setAlpha(1).setFillAlpha(1).setLineColor(0x000000).setLineWidth(3);

			graphicCharges.add(entityManager.createCircle()
					.setX(x)
					.setY(y)
					.setFillColor(0x555555)
					.setAlpha(0.8)
					.setRadius(20)
					.setScale(0.5)
					.setLineWidth(5)
					.setLineColor(0x000000));
		}
	}

	public void chargePower() {

		if (charge >= getMaxValue())
			return;

		graphicCharges.get(charge).setScale(1.0, Curve.ELASTIC).setFillColor(player.getColorToken());

		charge++;
	}


	public void consume() throws GameException {
		wasValid = true;
		if (charge != getMaxValue()) {
			wasValid = false;
			gameManager.addToGameSummary("Not enough charges of " + getName());
			return;
		}
		charge = 0;

		for (int i = 0; i < graphicCharges.size(); i++) {
			graphicCharges.get(i).setScale(0.5, Curve.ELASTIC).setFillColor(0x555555);
		}
	}

	public abstract String getName();

	public abstract int getMaxValue();

	public abstract int getPowerIndex();
}
