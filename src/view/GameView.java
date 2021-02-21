package view;

import javax.swing.*;

import java.awt.*;

public class GameView extends JFrame {
	private JPanel startGameHeroes;
	
	private JPanel upperWindow;
	private JPanel lowerWindow;
	private JPanel upperLeftWindow;
	private JPanel upperRightWindow;
	private JPanel upperCenterWindow;
	private JPanel lowerLeftWindow;
	private JPanel lowerCenterWindow;
	private JPanel lowerRightWindow;
	
	private JLabel explainBig;
	private JLabel explainText;
	
	private JLabel hero1Stats;
	private JButton hero1;
	private JLabel player1Deck;
	private JLabel player1DeckNum;
	
	private JLabel hero2Stats;
	private JButton hero2;
	private JLabel player2Deck;
	private JLabel player2DeckNum;
	
	private JPanel lowerRightWindowButtons;
	private JButton resetChosenCard;
	private JButton endTurn;
	private JButton useHeroPower;
	
	private JPanel player1Hand;
	private JPanel player1Field;
	private JPanel player1FieldWindow;
	
	private JPanel player2Hand;
	private JPanel player2Field;
	
	private JButton HunterButton;
	private JButton MageButton;
	private JButton PaladinButton;
	private JButton PriestButton;
	private JButton WarlockButton;

	private ImagePanel panel;
	
	private JButton startGameButton;
	private JPanel startGameButtonPanel;


	public GameView() {

		this.setTitle("Hearthstone");
		this.setBounds(8,5, 1520, 860);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("HearthStone Resources/Cursors/Hearthstone Cursor.png")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
		//////////////////////////
		//This is the code for the starting game window.
		/*
		//StartGameHeroes .. Choosing heroes from buttons.. 
		*/
		
		panel = new ImagePanel(new ImageIcon("HearthStone Resources/StartGameBackground.png").getImage());
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(1520,860));
		
		startGameButton=new JButton();
		startGameButton.setPreferredSize(new Dimension(380,104));
		startGameButton.setOpaque(false);
		startGameButton.setBorder(null);
		startGameButton.setMargin(new Insets(0, 0, 0, 0));
		startGameButton.setFocusPainted(false);
		startGameButton.setContentAreaFilled(false);
		startGameButton.setIcon(new ImageIcon("HearthStone Resources/StartGameButton.png"));
		startGameButton.setActionCommand("Start Game");
		
		startGameButtonPanel=new JPanel();
		startGameButtonPanel.setPreferredSize(new Dimension (400,200));
		startGameButtonPanel.setOpaque(false);
		startGameButtonPanel.add(startGameButton);
		
		panel.add(startGameButtonPanel,BorderLayout.SOUTH);
		
		this.add(panel);
		
		
		/*
		 Initiating Other Objects 
		*/
		
		initiateChoosingPlayerView();
		initiatePlayerView();
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	private void initiateChoosingPlayerView() {
		startGameHeroes=new JPanel();
		startGameHeroes.setPreferredSize(new Dimension(1550,860));
		startGameHeroes.setLayout(new FlowLayout());
		startGameHeroes.setOpaque(false);
		HunterButton =new JButton();
		HunterButton.setPreferredSize(new Dimension(294,860));
		HunterButton.setOpaque(false);
		HunterButton.setBorder(null);
		HunterButton.setMargin(new Insets(0, 0, 0, 0));
		HunterButton.setContentAreaFilled(false);
		HunterButton.setActionCommand("Choose Player Hero as Hunter");
		MageButton = new JButton();
		MageButton.setPreferredSize(new Dimension(325,860));
		MageButton.setContentAreaFilled(false);
		MageButton.setOpaque(false);
		MageButton.setBorder(null);
		MageButton.setMargin(new Insets(0, 0, 0, 0));
		MageButton.setActionCommand("Choose Player Hero as Mage");
		PaladinButton=new JButton();
		PaladinButton.setPreferredSize(new Dimension(297,860));
		PaladinButton.setContentAreaFilled(false);
		PaladinButton.setOpaque(false);
		PaladinButton.setBorder(null);
		PaladinButton.setMargin(new Insets(0, 0, 0, 0));
		PaladinButton.setActionCommand("Choose Player Hero as Paladin");
		PriestButton=new JButton();
		PriestButton.setPreferredSize(new Dimension(291,860));
		PriestButton.setContentAreaFilled(false);
		PriestButton.setOpaque(false);
		PriestButton.setBorder(null);
		PriestButton.setMargin(new Insets(0, 0, 0, 0));
		PriestButton.setActionCommand("Choose Player Hero as Priest");
		WarlockButton=new JButton();
		WarlockButton.setPreferredSize(new Dimension(270,860));
		WarlockButton.setContentAreaFilled(false);
		WarlockButton.setOpaque(false);
		WarlockButton.setBorder(null);
		WarlockButton.setMargin(new Insets(0, 0, 0, 0));
		WarlockButton.setActionCommand("Choose Player Hero as Warlock");
		startGameHeroes.add(HunterButton);
		startGameHeroes.add(MageButton);
		startGameHeroes.add(PaladinButton);
		startGameHeroes.add(PriestButton);
		startGameHeroes.add(WarlockButton);
		
	}

	private void initiatePlayerView() {
		
		upperWindow =new JPanel();
		upperWindow.setSize(new Dimension(1520,430));
		upperWindow.setLayout(new BorderLayout());
		upperWindow.setOpaque(false);
		
		upperRightWindow=new JPanel();
		upperRightWindow.setPreferredSize(new Dimension(250,430));
		upperRightWindow.setLayout(new BorderLayout());
		upperRightWindow.setOpaque(false);
		
		explainBig=new JLabel();
		explainBig.setPreferredSize(new Dimension(250,430));
		explainBig.setOpaque(false);
		
		explainText =new JLabel("",JLabel.CENTER);
		explainText.setFont(new Font("Serif", Font.BOLD, 14));
		explainBig.setLayout(new BorderLayout());
		explainText.setPreferredSize(new Dimension(110,250));
		explainBig.add(explainText,BorderLayout.SOUTH);
		explainText.setOpaque(false);
		
		upperRightWindow.add(explainBig);
		upperWindow.add(upperRightWindow,BorderLayout.EAST);
		
		upperLeftWindow = new JPanel();
		upperLeftWindow.setPreferredSize(new Dimension(150,430));
		upperLeftWindow.setLayout(new FlowLayout());
		upperLeftWindow.setOpaque(false);
		
		player2Deck = new JLabel();
		player2Deck.setPreferredSize(new Dimension(150, 170));
		player2Deck.setIcon(new ImageIcon("HearthStone Resources/CardBackDeck.png"));
		player2Deck.setOpaque(false);
		player2Deck.setBorder(null);
		
		player2Deck.setLayout(new BorderLayout());
		
		player2DeckNum =new JLabel();
		player2DeckNum.setFont(new Font("Serif", Font.BOLD, 36));
		player2DeckNum.setForeground(Color.BLACK);
		player2DeckNum.setPreferredSize(new Dimension(100, 80));
		player2Deck.add(player2DeckNum,BorderLayout.NORTH);
		
		hero2 = new JButton();
		
		hero2.setActionCommand("hero2target");
		hero2.setSize(new Dimension(130, 180));
		hero2.setOpaque(false);
		hero2.setBorder(null);
		hero2.setMargin(new Insets(0, 0, 0, 0));
		hero2.setContentAreaFilled(false);
		hero2.setFocusPainted(false);
		
		hero2Stats=new JLabel("<html>Health<p style='margin-top:-5'>Mana");
		hero2Stats.setForeground(Color.white);
		hero2Stats.setHorizontalTextPosition(JLabel.CENTER);
		hero2Stats.setPreferredSize(new Dimension(150,45));
		hero2Stats.setIcon(new ImageIcon("HearthStone Resources/Hero2Stats.png"));
		hero2Stats.setOpaque(false);
		
		upperLeftWindow.add(hero2Stats);
		upperLeftWindow.add(hero2);
		upperLeftWindow.add(player2Deck);
		
		upperWindow.add(upperLeftWindow,BorderLayout.WEST);
		
		upperCenterWindow=new JPanel();
		upperCenterWindow.setPreferredSize(new Dimension(1120,430));
		upperCenterWindow.setOpaque(false);
		
		player2Hand = new JPanel();
		player2Hand.setPreferredSize(new Dimension(1120,190));
		player2Hand.setOpaque(false);
		
		player2Field = new JPanel();
		player2Field.setPreferredSize(new Dimension(1120,210));
		player2Field.setLayout(new GridLayout(0, 7));
		player2Field.setOpaque(false);
		
		upperWindow.add(upperCenterWindow,BorderLayout.CENTER);
		upperCenterWindow.add(player2Hand);
		upperCenterWindow.add(player2Field);
		
		lowerWindow =new JPanel();
		lowerWindow.setSize(new Dimension(1520,430));
		lowerWindow.setLayout(new BorderLayout());
		lowerWindow.setOpaque(false);
		
		lowerLeftWindow = new JPanel();
		lowerLeftWindow.setPreferredSize(new Dimension(150,430));
		lowerLeftWindow.setLayout(new FlowLayout());
		lowerLeftWindow.setOpaque(false);
		
		player1Deck = new JLabel();
		player1Deck.setPreferredSize(new Dimension(150, 170));
		player1Deck.setIcon(new ImageIcon("HearthStone Resources/CardBackDeck.png"));
		player1Deck.setLayout(new BorderLayout());
		player1Deck.setOpaque(false);
		player1Deck.setBorder(null);
		
		player1DeckNum =new JLabel();
		player1DeckNum.setFont(new Font("Serif", Font.BOLD, 36));
		player1DeckNum.setForeground(Color.BLACK);
		player1DeckNum.setPreferredSize(new Dimension(100, 80));
		player1DeckNum.setOpaque(false);
		player1Deck.add(player1DeckNum,BorderLayout.SOUTH);
		
		
		hero1 = new JButton();
		
		hero1.setActionCommand("hero1target");
		hero1.setSize(new Dimension(150, 150));
		hero1.setIcon(new ImageIcon("RexarFlipped.png"));
		hero1.setOpaque(false);
		hero1.setBorder(null);
		hero1.setMargin(new Insets(0, 0, 0, 0));
		hero1.setContentAreaFilled(false);
		hero1.setFocusPainted(false);
		
		hero1Stats=new JLabel("<html>Health<p style='margin-top:-5'>Mana");
		hero1Stats.setForeground(Color.white);
		hero1Stats.setHorizontalTextPosition(JLabel.CENTER);
		hero1Stats.setPreferredSize(new Dimension(150,45));
		hero1Stats.setIcon(new ImageIcon("HearthStone Resources/Hero1Stats.png"));
		hero1Stats.setOpaque(false);
		
		lowerLeftWindow.add(player1Deck);
		lowerLeftWindow.add(hero1);
		lowerLeftWindow.add(hero1Stats);
		
		lowerWindow.add(lowerLeftWindow,BorderLayout.WEST);
		
		lowerRightWindow=new JPanel();
		lowerRightWindow.setPreferredSize(new Dimension(150,430));
		lowerRightWindow.setLayout(new BorderLayout());
		lowerRightWindow.setOpaque(false);
		
		lowerRightWindowButtons=new JPanel();
		lowerRightWindowButtons.setPreferredSize(new Dimension(150,200));
		lowerRightWindowButtons.setLayout(new FlowLayout());
		lowerRightWindowButtons.setOpaque(false);
		
		endTurn=new JButton(new ImageIcon("HearthStone Resources/ENDTURN.png"));
		
		endTurn.setActionCommand("End Turn");
		endTurn.setOpaque(false);
		endTurn.setBorder(null);
		endTurn.setMargin(new Insets(0, 0, 0, 0));
		endTurn.setContentAreaFilled(false);
		endTurn.setFocusPainted(false);
		
		resetChosenCard=new JButton(new ImageIcon("HearthStone Resources/RESETCHOICES.png"));
		
		resetChosenCard.setActionCommand("Reset Chosen Card");
		resetChosenCard.setOpaque(false);
		resetChosenCard.setBorder(null);
		resetChosenCard.setMargin(new Insets(0, 0, 0, 0));
		resetChosenCard.setContentAreaFilled(false);
		resetChosenCard.setFocusPainted(false);
		
		useHeroPower=new JButton(new ImageIcon("HearthStone Resources/USEHEROPOWER.png"));
		
		useHeroPower.setActionCommand("Use Hero Power");
		useHeroPower.setOpaque(false);
		useHeroPower.setBorder(null);
		useHeroPower.setMargin(new Insets(0, 0, 0, 0));
		useHeroPower.setContentAreaFilled(false);
		useHeroPower.setFocusPainted(false);
		
		
		lowerRightWindowButtons.add(resetChosenCard);
		lowerRightWindowButtons.add(useHeroPower);
		lowerRightWindowButtons.add(endTurn);
		lowerRightWindow.add(lowerRightWindowButtons,BorderLayout.SOUTH);
		
		lowerWindow.add(lowerRightWindow,BorderLayout.EAST);
		
		lowerCenterWindow=new JPanel();
		lowerCenterWindow.setPreferredSize(new Dimension(1220,430));
		lowerCenterWindow.setLayout(new FlowLayout());
		lowerCenterWindow.setOpaque(false);
		
		player1Hand = new JPanel();
		player1Hand.setPreferredSize(new Dimension(1220,190));
		player1Hand.setOpaque(false);
		
		player1Field = new JPanel();
		player1Field.setPreferredSize(new Dimension(1120,210));
		player1Field.setLayout(new GridLayout(0, 7));
		player1Field.setOpaque(false);
		
		player1FieldWindow=new JPanel();
		player1FieldWindow.setPreferredSize(new Dimension(1220,210));
		player1FieldWindow.setLayout(new BorderLayout());
		player1FieldWindow.setOpaque(false);
		
		player1FieldWindow.add(player1Field,BorderLayout.WEST);
		
		lowerCenterWindow.add(player1FieldWindow);
		lowerCenterWindow.add(player1Hand);
		lowerWindow.add(lowerCenterWindow,BorderLayout.CENTER);
		
	}
	

	public void clearView() {
		this.getContentPane().removeAll();
		this.revalidate();
		this.repaint();
	}
	
	public void SwitchtoPlayerView() {
		ImagePanel panel = new ImagePanel(new ImageIcon("HearthStone Resources/Wallpaper.jpg").getImage());
		panel.setLayout(new GridLayout(2,0));
		panel.add(upperWindow);
		panel.add(lowerWindow);
		this.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public static void main(String[] args) {
		GameView view=new GameView();
		view.revalidate();
		view.repaint();
		
	}
	
	public JLabel getExplainText() {
		return explainText;
	}
	
	public JLabel getPlayer1DeckNum() {
		return player1DeckNum;
	}
	
	
	public JLabel getPlayer2DeckNum() {
		return player2DeckNum;
	}
	public JButton getResetChosenCard() {
		return resetChosenCard;
	}
	
	public JPanel getStartGameHeroes() {
		return startGameHeroes;
	}
	
	
	public JPanel getUpperWindow() {
		return upperWindow;
	}
	
	
	public JPanel getLowerWindow() {
		return lowerWindow;
	}
	
	
	public JPanel getPlayer1Hand() {
		return player1Hand;
	}
	
	
	public JPanel getPlayer1Field() {
		return player1Field;
	}
	
	public JButton getHero1() {
		return hero1;
	}
	
	
	public JLabel getPlayer1Deck() {
		return player1Deck;
	}
	
	
	public JButton getEndTurn() {
		return endTurn;
	}
	
	
	public JButton getUseHeroPower() {
		return useHeroPower;
	}
	
	
	public JPanel getPlayer2Hand() {
		return player2Hand;
	}
	
	
	public JPanel getPlayer2Field() {
		return player2Field;
	}
	
	
	public JButton getHero2() {
		return hero2;
	}
	
	
	public JLabel getPlayer2Deck() {
		return player2Deck;
	}
	
	public JPanel getUpperLeftWindow() {
		return upperLeftWindow;
	}
	
	
	public JPanel getUpperRightWindow() {
		return upperRightWindow;
	}
	
	
	public JPanel getUpperCenterWindow() {
		return upperCenterWindow;
	}
	
	
	public JPanel getLowerLeftWindow() {
		return lowerLeftWindow;
	}
	
	
	public JPanel getLowerCenterWindow() {
		return lowerCenterWindow;
	}
	
	
	public JPanel getLowerRightWindow() {
		return lowerRightWindow;
	}
	
	
	public JLabel getExplainBig() {
		return explainBig;
	}
	
	
	public JLabel getHero1Stats() {
		return hero1Stats;
	}
	
	
	public JLabel getHero2Stats() {
		return hero2Stats;
	}
	
	
	public JPanel getLowerRightWindowButtons() {
		return lowerRightWindowButtons;
	}
	
	
	public JPanel getPlayer1FieldWindow() {
		return player1FieldWindow;
	}
	
	public JButton getHunterButton() {
		return HunterButton;
	}
	public JButton getMageButton() {
		return MageButton;
	}
	
	public JButton getPaladinButton() {
		return PaladinButton;
	}
	
	public JButton getPriestButton() {
		return PriestButton;
	}
	
	public JButton getWarlockButton() {
		return WarlockButton;
	}
	
	public ImagePanel getPanel() {
		return panel;
	}
	
	public JButton getStartGameButton() {
		return startGameButton;
	}
}