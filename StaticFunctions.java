public class StaticFunctions {
// Atributes for draw statistics graph bars 
	static final int barsizewidth = 30;
	static final int barsizeheight = 30;

	// Format Game Board Functions

	// Draws icon in game matrix

	static void drawIcon(ColorImage img, int posx, int posy, Color c, char letra) {
		int i, j, x, y;
		for (i = 0, x = posx; i < Constantes.ICON_SIZE; i++, x++) {
			for (j = 0, y = posy; j < Constantes.ICON_SIZE; j++, y++) {
				img.setColor(x, y, c);
			}
		}
		img.drawCenteredText(posx + Constantes.ICON_SIZE / 2, posy + Constantes.ICON_SIZE / 2, letra + " ",
				Constantes.ICON_SIZE / 2, new Color(255, 255, 255));

	}

	// draws matrix of the game

	static ColorImage drawBoardGame(ColorImage img, char[][] c, String s) {
		int i, j;

		for (i = 0; i < c.length; i++) {
			int[] numberofletters = numberOfLetters(s);
			Color[] cor = isInWord(c[i], s, numberofletters);
			for (j = 0; j < c[i].length; j++) {

				drawIcon(img,
						j * (Constantes.ICON_SIZE + Constantes.ICON_SPACING)
								+ ((img.getWidth() / 4) + (Constantes.ICON_SIZE + (Constantes.ICON_SIZE / 2))),
						i * (Constantes.ICON_SIZE + Constantes.ICON_SPACING) + (img.getHeight() / 4), cor[j], c[i][j]);
			}
		}
		return img;

	}

	// draws the keyboard of the game

	static void drawKeyboard(ColorImage img, char[][] c, int[] v) {
		int i, j, k = 0;

		for (i = 0; i < c.length; i++) {
			if (k <= v.length) {
				for (j = 0; j < c[i].length; j++) {
					if (i == 0) {
						drawIcon(img,
								j * (Constantes.ICON_SIZE + Constantes.ICON_SPACING)
										+ ((img.getWidth() / 4) - Constantes.ICON_SIZE),
								i * (Constantes.ICON_SIZE + Constantes.ICON_SPACING) + (img.getHeight() * 3 / 4),
								keyColor(v[k]), c[i][j]);
					}
					if (i == 1) {
						drawIcon(img,
								(Constantes.ICON_SIZE / 2) + j * (Constantes.ICON_SIZE + Constantes.ICON_SPACING)
										+ ((img.getWidth() / 4) - Constantes.ICON_SIZE),
								i * (Constantes.ICON_SIZE + Constantes.ICON_SPACING) + (img.getHeight() * 3 / 4),
								keyColor(v[k]), c[i][j]);
					}
					if (i == 2) {
						drawIcon(img,
								Constantes.ICON_SIZE + (Constantes.ICON_SIZE / 2)
										+ j * (Constantes.ICON_SIZE + Constantes.ICON_SPACING)
										+ ((img.getWidth() / 4) - Constantes.ICON_SIZE),
								i * (Constantes.ICON_SIZE + Constantes.ICON_SPACING) + (img.getHeight() * 3 / 4),
								keyColor(v[k]), c[i][j]);
					}
					k++;
				}

			}
		}
	}

	// Draws all the items of the game

	public static ColorImage drawFullGame(ColorImage img, char[][] boardGame, char[][] keyboard, int[] keyboardState,
			String ppuzzle) {
		img = drawBoardGame(img, boardGame, ppuzzle);
		drawKeyboard(img, keyboard, keyboardState);
		return img;
	}

	// Draws graphic bars in Statistics Image

	static void drawbars(ColorImage img, int posx, int posy, int value, Color color) {

		int i, j, x, y;
		for (i = 0, x = posx; i < barsizewidth * value; i++, x++) {
			for (j = 0, y = posy; j < barsizeheight; j++, y++) {

				img.setColor(x, y, color);

			}
		}
	}

	// Checks the number of letters in secret word

	static int[] numberOfLetters(String puzzle) {
		int[] letters = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		char[] charpuzzle = puzzle.toCharArray();
		for (int i = 0; i < charpuzzle.length; i++) {
			letters[charpuzzle[i] - 'A']++;
		}
		return letters;
	}

	// Characters state and color Functions

	// returns color of char icon according to the word inserted by the user

	static Color[] isInWord(char[] tentativa, String puzzle, int[] letters) {
		Color[] colors = { Constantes.EMPTY_BG, Constantes.EMPTY_BG, Constantes.EMPTY_BG, Constantes.EMPTY_BG,
				Constantes.EMPTY_BG };

		String tentativaUser = new String(tentativa);
		if (tentativaUser.equals("     ")) {
			return colors;
		}
		for (int i = 0; i < puzzle.length(); i++) {
			if (puzzle.charAt(i) == tentativa[i]) {
				colors[i] = Constantes.CORRECT_BG;
				letters[tentativa[i] - 'A']--;
			}
		}
		for (int j = 0; j < puzzle.length(); j++) {
			if (colors[j] == Constantes.EMPTY_BG) {
				if (letters[tentativa[j] - 'A'] != 0) {
					if (puzzle.indexOf(tentativa[j]) != -1) {
						colors[j] = Constantes.EXISTS_BG;
						letters[tentativa[j] - 'A']--;
					}
				} else {
					colors[j] = Constantes.NOT_IN_WORD_BG;
				}
			}
		}
		return colors;
	}

	// returns color of keyboard letter according to its state

	static Color keyColor(int keystate) {

		if (keystate == 1) {
			return Constantes.NOT_IN_WORD_BG;
		}
		if (keystate == 2) {
			return Constantes.EXISTS_BG;
		}
		if (keystate == 3) {
			return Constantes.CORRECT_BG;
		}
		return Constantes.EMPTY_BG;

	}

	// Strips string inserted by user

	public static String noSpecialCharactersString(String usertry) {
		char[] charUsertry = usertry.toCharArray();
		int i = 0;
		for (i = 0; i < charUsertry.length; i++) {
			if (charUsertry[i] == 'a' || charUsertry[i] == 'á' || charUsertry[i] == 'à' || charUsertry[i] == 'â'
					|| charUsertry[i] == 'ã' || charUsertry[i] == 'A' || charUsertry[i] == 'Á' || charUsertry[i] == 'Â'
					|| charUsertry[i] == 'À' || charUsertry[i] == 'Ã')
				charUsertry[i] = 'A';

			if (charUsertry[i] == 'c' || charUsertry[i] == 'C' || charUsertry[i] == 'ç' || charUsertry[i] == 'Ç') {
				charUsertry[i] = 'C';
			}

			if (charUsertry[i] == 'o' || charUsertry[i] == 'O' || charUsertry[i] == 'ó' || charUsertry[i] == 'ò'
					|| charUsertry[i] == 'õ' || charUsertry[i] == 'ô' || charUsertry[i] == 'Ó' || charUsertry[i] == 'Ò'
					|| charUsertry[i] == 'Ô' || charUsertry[i] == 'Õ') {
				charUsertry[i] = 'O';
			}

			if (charUsertry[i] == 'e' || charUsertry[i] == 'E' || charUsertry[i] == 'é' || charUsertry[i] == 'è'
					|| charUsertry[i] == 'ê' || charUsertry[i] == 'É' || charUsertry[i] == 'È'
					|| charUsertry[i] == 'Ê') {
				charUsertry[i] = 'E';
			}

			if (charUsertry[i] == 'i' || charUsertry[i] == 'I' || charUsertry[i] == 'í' || charUsertry[i] == 'ì'
					|| charUsertry[i] == 'Í' || charUsertry[i] == 'Ì') {
				charUsertry[i] = 'I';
			}

			if (charUsertry[i] == 'u' || charUsertry[i] == 'ú' || charUsertry[i] == 'û' || charUsertry[i] == 'ù'
					|| charUsertry[i] == 'U' || charUsertry[i] == 'Ú' || charUsertry[i] == 'Ù'
					|| charUsertry[i] == 'Û') {
				charUsertry[i] = 'U';
			}

			charUsertry[i] = Character.toUpperCase(charUsertry[i]);

		}
		String nospecialcharactersString = String.valueOf(charUsertry);
		return nospecialcharactersString;
	}

}