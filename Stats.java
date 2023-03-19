class Stats {

	private int[] lines;
	private int games = 0;
	private int wins = 0;
	private int winstreak = 0;
	private int winpercentage = 0;
	private int bestwinstreak = 0;
	private ColorImage stats;
	static final int statswidth = Constantes.DEFAULT_WIDTH * 3 / 4;
	static final int statsheight = Constantes.DEFAULT_HEIGHT;

	// Constructors

	Stats(int line) {
		lines = new int[line + 1];
		redrawStats();
	}

	// Methods

	// GivePlayer their statistics

	ColorImage showStats() {
		return stats;
	}

	// Draws a bar in the position player wins or loses

	void drawgraph(int position) {

		Color cor = Constantes.ERROR_BG;

		StaticFunctions.drawbars(stats, (statswidth) / 4 + 20, ((statsheight) * 2 / 5) + 10 + (40 * (position + 1)),
				lines[position], cor);

	}

	// Format and Upload Statistics Image function

	void redrawStats() {
		this.stats = new ColorImage(statswidth, statsheight, new Color(20, 23, 24));
		stats.drawCenteredText((statswidth) / 2, 50, "ESTATÍSTICAS", 30, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) / 5, 100, games + " ", 50, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) / 5, 140, "Jogados", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 2 / 5, 100, winpercentage + " ", 50, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 2 / 5, 140, "% Vitórias", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 3 / 5, 100, winstreak + " ", 50, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 3 / 5, 140, "Sequência", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 3 / 5, 160, "de vitórias", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 4 / 5, 100, bestwinstreak + " ", 50, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 4 / 5, 140, "Melhor", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) * 4 / 5, 160, "sequência", 20, new Color(255, 255, 255));
		stats.drawCenteredText((statswidth) / 2, statsheight * 2 / 5, "Distribuição de Tentativas", 30,
				new Color(255, 255, 255));

		for (int i = 0; i < lines.length; i++) {
			if (i != lines.length - 1) {
				stats.drawCenteredText((statswidth) / 4, ((statsheight) * 2 / 5) + 25 + (40 * (i + 1)), i + 1 + "", 30,
						new Color(255, 255, 255));
			} else {
				stats.drawCenteredText((statswidth) / 4, ((statsheight) * 2 / 5) + 25 + (40 * (i + 1)), "x", 30,
						new Color(255, 255, 255));
			}
			drawgraph(i);
		}
		linevalues();
	}

	// Updates The statistics after finishing the game

	void updatestats(int line) {

		games++;
		if (line != 6) {
			lines[line]++;
			wins++;
			winpercentage = (wins / games) * 100;
			if (wins > winstreak) {
				winstreak = wins;
			}
			if (bestwinstreak < winstreak)
				bestwinstreak = winstreak;
		} else {
			winstreak = 0;
			wins = 0;
			lines[6]++;
		}

		redrawStats();
	}

	// draws the line number in Statistics Image

	void linevalues() {
		for (int t = 0; t < lines.length; t++) {

			stats.drawCenteredText(((statswidth) / 4) + 35, ((statsheight) * 2 / 5) + 25 + (40 * (t + 1)),
					lines[t] + "", 30, new Color(255, 255, 255));
		}
	}

}