public class Game {

	private char[][] boardGame = { { ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ' }, };
	private char[][] keyboard = { { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' },
			{ 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S' }, { 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' } };
	private Dictionary dicionario;
	 ColorImage game;
	private String ppuzzle = "";
	private int line = 0;
	private int[] keyboardstate = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private Stats Statistics = new Stats(boardGame.length);

	// Constructors

	Game(Dictionary dictionary) {
		if (dictionary == null) {
			throw new NullPointerException(" Não foram introduzidos dados necessários para começar o jogo");
		} else {

			ColorImage img = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.DEFAULT_BG);
			this.game = StaticFunctions.drawFullGame(img, boardGame, keyboard, keyboardstate, ppuzzle);
			this.dicionario = dictionary;
			ppuzzle = word();
		}
	}

	Game(ColorImage image) {
		if (image == null) {
			throw new NullPointerException("Não foram introduzidos dados necessários para começar o jogo");
		} else {

			this.dicionario = new Dictionary("pt_br.txt");
			this.game = StaticFunctions.drawFullGame(image, boardGame, keyboard, keyboardstate, ppuzzle);
		}
	}

	// Methods

	// generate word
	String word() {
		this.ppuzzle = dicionario.generateSecretWord(boardGame[0].length);
		return ppuzzle;
	}

	// Check for keyboard state
	void keyState(int key) {
		for (int j = 0; j < boardGame[key].length; j++) {
			if (boardGame[key][j] == ppuzzle.charAt(j)) {
				keyboardstate[boardGame[key][j] - 'A'] = 3;
			} else if (ppuzzle.indexOf(boardGame[key][j]) == -1) {
				keyboardstate[boardGame[key][j] - 'A'] = 1;
			} else if (keyboardstate[boardGame[key][j] - 'A'] != 3) {
				keyboardstate[boardGame[key][j] - 'A'] = 2;
			}
		}

	}

	// Inserts User try in BoardGame
	void insert(String userAttempt) {
		userAttempt = StaticFunctions.noSpecialCharactersString(userAttempt);

		if (userAttempt == " ") {
			throw new IllegalArgumentException("Não foi introduzida nenhuma palavra");
		} else {
			if (userAttempt.length() != boardGame[0].length) {
				throw new IllegalArgumentException("A palavra não tem tamanho válido");
			}

			if (dicionario.exists(userAttempt) == true) {

				char[] charOfUserTry = userAttempt.toCharArray();
				int j;

				if (line != boardGame[0].length) {

					for (j = 0; j < boardGame[line].length; j++) {
						boardGame[line][j] = charOfUserTry[j];

					}
					keyState(line);
					this.game = StaticFunctions.drawFullGame(game, boardGame, keyboard, keyboardstate, ppuzzle);
					if (ppuzzle.equals(userAttempt) == true) {
						Statistics.updatestats(line);
						Statistics.showStats();
						System.out.print(" Parabéns! Acertaste a palavra secreta que era " + ppuzzle+" ");
						System.out.println();
					
				
					} else {
						line++;
					}
				} else {
					if (ppuzzle.equals(userAttempt) == true) {

						for (j = 0; j < boardGame[line].length; j++) {
							boardGame[line][j] = charOfUserTry[j];

						}
						keyState(line);
						this.game = StaticFunctions.drawFullGame(game, boardGame, keyboard, keyboardstate, ppuzzle);
						Statistics.updatestats(line + 1);
						Statistics.showStats();
						System.out.print(" Parabéns! Acertaste a palavra secreta que era "+ ppuzzle+" ");
						System.out.println();
						
					} else {

						for (j = 0; j < boardGame[line].length; j++) {
							boardGame[line][j] = charOfUserTry[j];

						}
						keyState(line);
						this.game = StaticFunctions.drawFullGame(game, boardGame, keyboard, keyboardstate, ppuzzle);
						Statistics.updatestats(line + 1);
						Statistics.showStats();
						System.out.print(" A palavra correta era " + ppuzzle + "");
						System.out.println();
						
			
					}
				}

			} else {
				throw new IllegalArgumentException("A palavra introduzida não existe");
			}
		}
	}

	// Reset Functions

	// Check if players have insert any word
	boolean isFirstLineEmpty() {
		for (int j = 0; j < boardGame[0].length; j++) {
			if (boardGame[0][j] == ' ') {
				return true;
			}
		}

		return false;
	}

	// Resets all game matrix and all the states
	void newGame() {
		boolean isEmpty = isFirstLineEmpty();
		if (isEmpty == true) {
			throw new IllegalStateException("Tem de jogar pelo menos uma vez para realizar esta operação ");
		} else {
			for (int i = 0; i < boardGame.length; i++) {
				for (int j = 0; j < boardGame[i].length; j++) {
					boardGame[i][j] = ' ';
				}
			}

			for (int i = 0; i < keyboardstate.length; i++) {
				keyboardstate[i] = 0;
			}
			line = 0;
			ppuzzle =word();
			StaticFunctions.drawFullGame(game, boardGame, keyboard, keyboardstate, ppuzzle);
		}
	}

}
