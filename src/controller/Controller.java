
package controller;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.GameView;
import view.ImagePanel;

public class Controller implements GameListener, ActionListener, MouseListener {
	private Game model;
	private GameView view;
	private boolean GameEnded;
	private Hero Player1;
	private Hero Player2;
	private int usedSpellIndex;
	private Spell usedSpell;
	private int indexOfattackerminion;
	private ImageIcon chosenImage;
	private String chosenText;
	private Boolean useHeroPower;
	private ArrayList<JButton> Player1HandButtons;
	private ArrayList<JButton> Player1FieldButtons;
	private ArrayList<JButton> Player2FieldButtons;
	private AudioInputStream audioinputstream;
	private Clip clip;
	private SimpleAudioPlayer s;

	public Controller() throws FullHandException, CloneNotSupportedException {
		view = new GameView();
		view.getStartGameButton().addActionListener(this);
		for (int i = 0; i < view.getStartGameHeroes().getComponentCount(); i++) {
			((JButton) (view.getStartGameHeroes().getComponent(i))).addActionListener(this);
		}
		view.getEndTurn().addActionListener(this);
		view.getUseHeroPower().addActionListener(this);
		view.getResetChosenCard().addActionListener(this);
		view.getHero1().addActionListener(this);
		view.getHero2().addActionListener(this);
		Player1HandButtons = new ArrayList<>();
		Player1FieldButtons = new ArrayList<>();
		Player2FieldButtons = new ArrayList<>();
		try {
			s = new SimpleAudioPlayer("HearthStone Sounds/Game Sounds/Game Music.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usedSpellIndex = -1;
		indexOfattackerminion = -1;
		GameEnded = false;
		useHeroPower = false;
		view.getHunterButton().addMouseListener(this);
		view.getMageButton().addMouseListener(this);
		view.getPaladinButton().addMouseListener(this);
		view.getPriestButton().addMouseListener(this);
		view.getWarlockButton().addMouseListener(this);
		view.getStartGameButton().addMouseListener(this);
		view.repaint();
		view.revalidate();

	}

	public void onGameOver() {
		view.clearView();
		Boolean p=false;
		if (Player1.getCurrentHP() == 0) {
			p=false;
		} else if (Player2.getCurrentHP() == 0) {
			p=true;
		}
		ImagePanel x=new ImagePanel("null");
		if(p) {
				x = new ImagePanel(new ImageIcon("HearthStone Resources/Endgamepics/"+Player1.getClass().getSimpleName()+"Player1Victory.png").getImage());
			
		}else if(!p) {
			x = new ImagePanel(new ImageIcon("HearthStone Resources/Endgamepics/"+Player2.getClass().getSimpleName()+"Player2Victory.png").getImage());
		}

		try {
			s.stop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.add(x);
		view.revalidate();
		view.repaint();
		GameEnded = true;

	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException, FullHandException {
		new Controller();
	}

	public void music(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// create AudioInputStream object
		audioinputstream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioinputstream);
		clip.start();
		// clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Choosing Player Hero
		 */
		try {
			music("HearthStone Sounds/Game Sounds/Click.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if (e.getActionCommand().equals("Start Game")) {
			InitiateChoosingHero();
			ChangetoPlayer1SelectHero();
		}

		if (e.getActionCommand().equals("Choose Player Hero as Hunter")) {

			if (Player1 == null) {
				s.play();
				try {
					Player1 = new Hunter();
				} catch (CloneNotSupportedException | IOException e1) {
				}
				ChangetoPlayer2SelectHero();
			} else {
				try {
					Player2 = new Hunter();
					startGame();
				} catch (IOException | CloneNotSupportedException | FullHandException e1) {
				}
			}
		} else if (e.getActionCommand().equals("Choose Player Hero as Mage")) {
			if (Player1 == null) {
				s.play();
				try {
					Player1 = new Mage();
				} catch (CloneNotSupportedException | IOException e1) {
				}
				ChangetoPlayer2SelectHero();
			} else {
				try {
					Player2 = new Mage();
					startGame();
				} catch (IOException | CloneNotSupportedException | FullHandException e1) {
				}
			}
		} else if (e.getActionCommand().equals("Choose Player Hero as Paladin")) {
			if (Player1 == null) {
				s.play();
				try {
					Player1 = new Paladin();
				} catch (CloneNotSupportedException | IOException e1) {
				}
				ChangetoPlayer2SelectHero();
			} else {
				try {
					Player2 = new Paladin();
					startGame();
				} catch (IOException | CloneNotSupportedException | FullHandException e1) {
				}
			}
		} else if (e.getActionCommand().equals("Choose Player Hero as Priest")) {
			if (Player1 == null) {
				s.play();
				try {
					Player1 = new Priest();
				} catch (CloneNotSupportedException | IOException e1) {
				}
				ChangetoPlayer2SelectHero();
			} else {
				try {
					Player2 = new Priest();
					startGame();
				} catch (IOException | CloneNotSupportedException | FullHandException e1) {
				}
			}
		} else if (e.getActionCommand().equals("Choose Player Hero as Warlock")) {
			if (Player1 == null) {
				try {
					s.play();
					Player1 = new Warlock();
				} catch (CloneNotSupportedException | IOException e1) {
				}
				ChangetoPlayer2SelectHero();
			} else {
				try {
					Player2 = new Warlock();
					startGame();
				} catch (IOException | CloneNotSupportedException | FullHandException e1) {
				}
			}
		} else if (e.getActionCommand().equals("End Turn")) {

			try {
				model.endTurn();
			} catch (FullHandException | CloneNotSupportedException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
			}
			if (!GameEnded) {
				renderPlayerview();

			}
		} else if (Player1HandButtons.contains((JButton) e.getSource())) {
			int n = Player1HandButtons.indexOf((JButton) e.getSource());
			if (model.getCurrentHero().getHand().get(n) instanceof Minion) {
				try {
					model.getCurrentHero().playMinion((Minion) model.getCurrentHero().getHand().get(n));
					renderPlayerview();
				} catch (NotEnoughManaException | NotYourTurnException | FullFieldException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}

			} else if (model.getCurrentHero().getHand().get(n) instanceof FieldSpell) {
				try {
					model.getCurrentHero().castSpell((FieldSpell) model.getCurrentHero().getHand().get(n));
					usedSpellIndex = -1;
					usedSpell = null;
					renderPlayerview();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			} else if (model.getCurrentHero().getHand().get(n) instanceof AOESpell) {
				try {
					model.getCurrentHero().castSpell((AOESpell) model.getCurrentHero().getHand().get(n),
							model.getOpponent().getField());
					usedSpellIndex = -1;
					usedSpell = null;
					renderPlayerview();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}

			} else if (model.getCurrentHero().getHand().get(n) instanceof MinionTargetSpell
					| model.getCurrentHero().getHand().get(n) instanceof HeroTargetSpell
					| model.getCurrentHero().getHand().get(n) instanceof LeechingSpell) {
				usedSpell = (Spell) model.getCurrentHero().getHand().get(n);
				usedSpellIndex = n;
				chosenImage = getImageforExplain(usedSpell);
				chosenText = null;
			}
		}

		/*
		 * Player Casting Spell
		 */
		else if ((Player1FieldButtons.contains((JButton) (e.getSource())) & usedSpellIndex != -1
				&& usedSpell instanceof MinionTargetSpell)) {
			int n = Player1FieldButtons.indexOf((JButton) (e.getSource()));
			try {
				model.getCurrentHero().castSpell(
						(MinionTargetSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getCurrentHero().getField().get(n));
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}

		} else if ((Player2FieldButtons.contains((JButton) (e.getSource())) & usedSpellIndex != -1
				&& usedSpell instanceof MinionTargetSpell)) {
			int n = Player2FieldButtons.indexOf((JButton) (e.getSource()));
			try {
				model.getCurrentHero().castSpell(
						(MinionTargetSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getOpponent().getField().get(n));
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		} else if (view.getHero1() == ((JButton) e.getSource()) & usedSpellIndex != -1
				&& usedSpell instanceof HeroTargetSpell) {
			try {
				model.getCurrentHero().castSpell((HeroTargetSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getCurrentHero());
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		} else if (view.getHero2() == ((JButton) e.getSource()) & usedSpellIndex != -1
				&& usedSpell instanceof HeroTargetSpell) {
			try {
				model.getCurrentHero().castSpell((HeroTargetSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getOpponent());
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		} else if ((Player1FieldButtons.contains((JButton) (e.getSource())) & usedSpellIndex != -1
				&& usedSpell instanceof LeechingSpell)) {
			int n = Player1FieldButtons.indexOf((JButton) (e.getSource()));
			try {
				model.getCurrentHero().castSpell((LeechingSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getCurrentHero().getField().get(n));
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		} else if (Player2FieldButtons.contains((JButton) (e.getSource())) & usedSpellIndex != -1
				&& usedSpell instanceof LeechingSpell) {
			int n = Player2FieldButtons.indexOf((JButton) (e.getSource()));
			try {
				model.getCurrentHero().castSpell((LeechingSpell) model.getCurrentHero().getHand().get(usedSpellIndex),
						model.getOpponent().getField().get(n));
				usedSpell = null;
				usedSpellIndex = -1;
				renderPlayerview();
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				try {
					popScreen(e1);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}

		else if (e.getActionCommand().equals("attacker")) {

			if (useHeroPower) {
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(model.getCurrentHero().getField()
								.get(Player1FieldButtons.indexOf((JButton) e.getSource())));
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

				} else if (model.getCurrentHero() instanceof Priest) {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(model.getCurrentHero().getField()
								.get(Player1FieldButtons.indexOf((JButton) e.getSource())));
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				useHeroPower = false;

			} else {

				JButton clickedminion = (JButton) e.getSource();
				indexOfattackerminion = Player1FieldButtons.indexOf(clickedminion);
				chosenImage = getImageforExplain(model.getCurrentHero().getField().get(indexOfattackerminion));
				String Sleeping = "";
				if (model.getCurrentHero().getField().get(indexOfattackerminion).isSleeping()) {
					Sleeping = "Sleeping";
				}
				chosenText = "<html>" + model.getCurrentHero().getField().get(indexOfattackerminion).toStringField()
						+ Sleeping;
			}

		} else if (e.getActionCommand().equals("target")) {
			if (useHeroPower) {
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(model.getOpponent().getField()
								.get(Player2FieldButtons.indexOf((JButton) e.getSource())));
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

				} else if (model.getCurrentHero() instanceof Priest) {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(model.getOpponent().getField()
								.get(Player2FieldButtons.indexOf((JButton) e.getSource())));
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				useHeroPower = false;
			} else if (indexOfattackerminion != -1) {
				Minion attacker = model.getCurrentHero().getField().get(indexOfattackerminion);
				JButton clickedminion = (JButton) e.getSource();
				int indexOftargetminion = Player2FieldButtons.indexOf(clickedminion);
				Minion target = model.getOpponent().getField().get(indexOftargetminion);
				try {
					model.getCurrentHero().attackWithMinion(attacker, target);
					renderPlayerview();
					indexOfattackerminion = -1;
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
						| NotSummonedException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		} else if (e.getActionCommand().equals("hero1target")) {
			if (useHeroPower) {
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

				} else if (model.getCurrentHero() instanceof Priest) {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				useHeroPower = false;

			}
		}

		else if (e.getActionCommand().equals("hero2target")) {

			if (useHeroPower) {
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(model.getOpponent());
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

				} else if (model.getCurrentHero() instanceof Priest) {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(model.getOpponent());
						renderPlayerview();
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						try {
							popScreen(e1);
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				useHeroPower = false;

			} else if (indexOfattackerminion != -1) {
				Minion attacker = model.getCurrentHero().getField().get(indexOfattackerminion);
				try {
					model.getCurrentHero().attackWithMinion(attacker, model.getOpponent());
					renderPlayerview();
					indexOfattackerminion = -1;
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
						| InvalidTargetException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		}

		else if (e.getActionCommand().equals("Use Hero Power")) {
			if (model.getCurrentHero() instanceof Mage | model.getCurrentHero() instanceof Priest) {
				useHeroPower = true;
				chosenImage = getImageforExplain();
			} else {
				try {
					model.getCurrentHero().useHeroPower();
					renderPlayerview();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					try {
						popScreen(e1);
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		} else if (e.getActionCommand().equals("Reset Chosen Card")) {
			indexOfattackerminion = -1;
			usedSpell = null;
			usedSpellIndex = -1;
			chosenImage = null;
			chosenText = null;
			useHeroPower = false;
			view.getExplainBig().setIcon(chosenImage);
			view.getExplainText().setText(chosenText);

		}
	}

	private void startGame() throws FullHandException, CloneNotSupportedException {
		SwitchToGameView();
		model = new Game(Player1, Player2);
		model.setListener(this);
		renderPlayerview();
	}

	private void renderPlayerview() {
		Player1HandButtons.clear();
		Player1FieldButtons.clear();
		Player2FieldButtons.clear();
		view.getPlayer1Hand().removeAll();
		view.getPlayer2Hand().removeAll();
		view.getPlayer1Field().removeAll();
		view.getPlayer2Field().removeAll();
		usedSpell = null;
		usedSpellIndex = -1;
		indexOfattackerminion = -1;
		chosenImage = null;
		chosenText = null;
		view.getExplainBig().setIcon(chosenImage);
		view.getExplainText().setText(chosenText);
		useHeroPower = false;
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				(new ImageIcon("HearthStone Resources/Cursors/Hearthstone Buttons Cursor.png")).getImage(), new Point(), "Normal Cursor");
		view.getUseHeroPower().addMouseListener(this);

		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			JButton b = new JButton();
			b.setPreferredSize(new Dimension(116, 190));
			b.addActionListener(this);
			b.setIcon(getImageforPlayer1Hand(model.getCurrentHero().getHand().get(i)));
			if (model.getCurrentHero().getHand().get(i) instanceof Minion) {
				JLabel text = new JLabel("<html>" + ((Minion) model.getCurrentHero().getHand().get(i)).toStringHand(),
						JLabel.CENTER);
				text.setFont(new Font("Serif", Font.BOLD, 10));
				b.setLayout(new BorderLayout());
				text.setPreferredSize(new Dimension(110, 94));
				b.add(text, BorderLayout.SOUTH);
			}
			b.setOpaque(false);
			b.setBorder(null);
			b.setMargin(new Insets(0, 0, 0, 0));
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
			b.addMouseListener(this);
			b.setCursor(cursor);
			Player1HandButtons.add(b);
			view.getPlayer1Hand().add(b);
		}

		for (int i = 0; i < model.getOpponent().getHand().size(); i++) {
			JButton b = new JButton();
			b.setPreferredSize(new Dimension(105, 215));
			b.setIcon(new ImageIcon("HearthStone Resources/CardBackPlayer2Hand.png"));
			b.setOpaque(false);
			b.setBorder(null);
			b.setMargin(new Insets(0, 0, 0, 0));
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
			view.getPlayer2Hand().add(b);
		}

		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
			JButton b = new JButton();
			b.addActionListener(this);
			b.setPreferredSize(new Dimension(153, 221));
			b.setIcon(getImageforPlayer1Field(model.getCurrentHero().getField().get(i)));
			String Sleeping = "";
			if (model.getCurrentHero().getField().get(i).isSleeping()) {
				Sleeping = "Sleeping";
			}
			JLabel text = new JLabel(
					"<html>" + ((Minion) model.getCurrentHero().getField().get(i)).toStringField() + Sleeping,
					JLabel.CENTER);
			text.setFont(new Font("Serif", Font.BOLD, 11));
			b.setLayout(new BorderLayout());
			text.setPreferredSize(new Dimension(110, 100));
			b.add(text, BorderLayout.SOUTH);

			b.setOpaque(false);
			b.setBorder(null);
			b.setMargin(new Insets(0, 0, 0, 0));
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);

			b.addMouseListener(this);
			b.setCursor(cursor);
			Player1FieldButtons.add(b);
			b.setActionCommand("attacker");
			view.getPlayer1Field().add(b);
		}

		for (int i = 0; i < model.getOpponent().getField().size(); i++) {
			JButton b = new JButton();
			b.addActionListener(this);
			b.setPreferredSize(new Dimension(153, 221));
			b.setIcon(getImageforPlayer2Field(model.getOpponent().getField().get(i)));

			JLabel text = new JLabel("<html>" + ((Minion) model.getOpponent().getField().get(i)).toStringField(),
					JLabel.CENTER);
			text.setFont(new Font("Serif", Font.BOLD, 11));
			b.setLayout(new BorderLayout());
			text.setPreferredSize(new Dimension(110, 100));
			b.add(text, BorderLayout.NORTH);

			b.setOpaque(false);
			b.setBorder(null);
			b.setMargin(new Insets(0, 0, 0, 0));
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);

			b.addMouseListener(this);
			b.setCursor(cursor);
			Player2FieldButtons.add(b);
			b.setActionCommand("target");
			view.getPlayer2Field().add(b);
		}

		view.getPlayer1DeckNum().setText("     " + model.getCurrentHero().getDeck().size());
		view.getPlayer2DeckNum().setText("     " + model.getOpponent().getDeck().size());

		if (Player1 == model.getCurrentHero()) {
			view.getHero1Stats()
					.setText("<html><center>Player1<p style='margin-top:-5'>" + model.getCurrentHero().toString());
			view.getHero2Stats()
					.setText("<html><center>Player2<p style='margin-top:-5'>" + model.getOpponent().toString());

		} else {
			view.getHero1Stats()
					.setText("<html><center>Player2<p style='margin-top:-5'>" + model.getCurrentHero().toString());
			view.getHero2Stats()
					.setText("<html><center>Player1<p style='margin-top:-5'>" + model.getOpponent().toString());
		}

		view.getHero1().setIcon(getImageforPlayer1Hero());
		view.getHero1().setCursor(cursor);
		view.getHero2().setIcon(getImageforPlayer2Hero());
		view.getHero2().setCursor(cursor);

		view.getEndTurn().setCursor(cursor);
		view.getResetChosenCard().setCursor(cursor);
		view.getUseHeroPower().setCursor(cursor);

		view.repaint();
		view.revalidate();

	}

	private ImageIcon getImageforExplain(Card c) {
		if (c.getName().equals("Shadow Word: Death")) {
			return new ImageIcon("HearthStone Resources/CardsExplainSize/Shadow Word Death.png");
		}else {
			return new ImageIcon("HearthStone Resources/CardsExplainSize/"+c.getName()+".png");
		}
	}

	private ImageIcon getImageforPlayer1Hand(Card c) {
		if (c.getName().equals("Shadow Word: Death")) {
			return new ImageIcon("HearthStone Resources/CardsHandSize/Shadow Word Death.png");
		}else {
			return new ImageIcon("HearthStone Resources/CardsHandSize/"+c.getName()+".png");
		}
	}

	private ImageIcon getImageforPlayer1Field(Card c) {
		return new ImageIcon("HearthStone Resources/CardsFieldSize/"+c.getName()+".png");
	}

	private ImageIcon getImageforPlayer2Field(Card c) {
		return new ImageIcon("HearthStone Resources/CardsFieldSizeFlipped/"+c.getName()+".png");
	}

	private ImageIcon getImageforPlayer1Hero() {
		return new ImageIcon("HearthStone Resources/Heroes/"+model.getCurrentHero().getClass().getSimpleName()+".png");
	}

	private ImageIcon getImageforPlayer2Hero() {
			return new ImageIcon("HearthStone Resources/HeroesFlipped/"+model.getOpponent().getClass().getSimpleName()+".png");
	}

	private ImageIcon getImageforExplain() {
		return new ImageIcon("HearthStone Resources/CardsExplainSize/Hero Powers/"+model.getCurrentHero().getClass().getSimpleName()+" Hero Power.png");
	}

	private void popScreen(Exception e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		JFrame pop = new JFrame();
		pop.setBounds(400, 250, 434, 185);

		if (e instanceof FullHandException) {
			ImagePanel x = new ImagePanel((new ImageIcon("HearthStone Resources/ExceptionPics/My hand is too full !!!.png").getImage()));
			x.setLayout(new BorderLayout());

			JLabel card = new JLabel(getImageforExplain(((FullHandException) e).getBurned()));
			card.setPreferredSize(new Dimension(250, 430));
			card.setOpaque(false);
			card.setLayout(new BorderLayout());

			JLabel cardText = new JLabel("", JLabel.CENTER);
			cardText.setFont(new Font("Serif", Font.BOLD, 14));
			cardText.setPreferredSize(new Dimension(110, 190));
			cardText.setOpaque(false);
			if (((FullHandException) e).getBurned() instanceof Minion) {
				cardText.setText("<html>" + ((Minion) ((FullHandException) e).getBurned()).toStringField());
			}
			card.add(cardText, BorderLayout.SOUTH);
			x.add(card, BorderLayout.EAST);
			pop.setBounds(400, 250, 800, 370);
			pop.add(x);
			music("HearthStone Sounds/"+model.getCurrentHero().getClass().getSimpleName()+"/fullhand"+model.getCurrentHero().getClass().getSimpleName()+".wav");
		} else if (e.getMessage().equals("A minion with taunt is in the way")|e.getMessage().equals("Give this minion a turn to get ready")|e.getMessage().equals("This minion has already attacked")|e.getMessage().equals("I don't have enough mana !!")|e.getMessage().equals("No space for this minion")|e.getMessage().equals("This minion Cannot Attack")|e.getMessage().equals("I already used my hero power")|e.getMessage().equals("this minion can not attack heroes")|e.getMessage().equals("Choose a minion with 5 or more attack")) {
			music("HearthStone Sounds/"+model.getCurrentHero().getClass().getSimpleName()+"/"+e.getMessage()+".wav");
			pop.add(new ImagePanel(new ImageIcon("HearthStone Resources/ExceptionPics/"+e.getMessage()+".png").getImage()));
		} else {
			music("HearthStone Sounds/"+model.getCurrentHero().getClass().getSimpleName()+"/exception.wav");
		}
		pop.setVisible(true);
		renderPlayerview();
	}

	private void SwitchToGameView() {
		view.clearView();
		view.SwitchtoPlayerView();

	}
	
	private void InitiateChoosingHero() {
		view.clearView();
		view.add(view.getStartGameHeroes());
		view.repaint();
		view.revalidate();
	}
	
	private void ChangetoPlayer1SelectHero() {
		ImagePanel y = new ImagePanel(
				new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChooseHero.jpg").getImage());
		view.clearView();
		y.add(view.getStartGameHeroes());
		view.add(y);
		view.repaint();
		view.revalidate();
	}

	private void ChangetoPlayer2SelectHero() {
		ImagePanel x = new ImagePanel(
				new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChooseHero.jpg").getImage());
		x.add(view.getStartGameHeroes());
		view.clearView();
		view.add(x);
		view.repaint();
		view.revalidate();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		if((JButton)e.getSource()==view.getStartGameButton()) {
			view.getStartGameButton().setIcon(new ImageIcon("HearthStone Resources/StartGameButtonHover.png"));
		}
		
		if (Player1FieldButtons.contains((JButton) e.getSource())) {
			view.getExplainBig().setIcon(getImageforExplain(
					model.getCurrentHero().getField().get(Player1FieldButtons.indexOf((JButton) e.getSource()))));

			String Sleeping = "";
			if (((Minion) model.getCurrentHero().getField().get(Player1FieldButtons.indexOf((JButton) e.getSource())))
					.isSleeping()) {
				Sleeping = "Sleeping";
			}
			view.getExplainText()
					.setText("<html>"
							+ ((Minion) model.getCurrentHero().getField()
									.get(Player1FieldButtons.indexOf((JButton) e.getSource()))).toStringField()
							+ Sleeping);
		} else if (Player1HandButtons.contains((JButton) e.getSource())) {
			view.getExplainBig().setIcon(getImageforExplain(
					model.getCurrentHero().getHand().get(Player1HandButtons.indexOf((JButton) e.getSource()))));
			if (model.getCurrentHero().getHand()
					.get(Player1HandButtons.indexOf((JButton) e.getSource())) instanceof Minion) {
				view.getExplainText().setText("<html>" + ((Minion) model.getCurrentHero().getHand()
						.get(Player1HandButtons.indexOf((JButton) e.getSource()))).toStringHand());
			} else {
				view.getExplainText().setText(null);
			}
		} else if (Player2FieldButtons.contains((JButton) e.getSource())) {
			view.getExplainBig().setIcon(getImageforExplain(
					model.getOpponent().getField().get(Player2FieldButtons.indexOf((JButton) e.getSource()))));

			view.getExplainText().setText("<html>" + ((Minion) model.getOpponent().getField()
					.get(Player2FieldButtons.indexOf((JButton) e.getSource()))).toStringField());
		}

		if (((JButton) e.getSource()) == view.getUseHeroPower()) {
			view.getExplainBig().setIcon(getImageforExplain());
			view.getExplainText().setText(null);
		}

		if (((JButton) e.getSource()) == view.getHunterButton() & Player1 == null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChooseHunter.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getMageButton() & Player1 == null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChooseMage.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getPaladinButton() & Player1 == null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChoosePaladin.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getPriestButton() & Player1 == null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChoosePriest.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getWarlockButton() & Player1 == null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChooseWarlock.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getHunterButton() & Player1 != null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChooseHunter.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getMageButton() & Player1 != null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChooseMage.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getPaladinButton() & Player1 != null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChoosePaladin.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getPriestButton() & Player1 != null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChoosePriest.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (((JButton) e.getSource()) == view.getWarlockButton() & Player1 != null) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChooseWarlock.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		if((JButton)e.getSource()==view.getStartGameButton()) {
			view.getStartGameButton().setIcon(new ImageIcon("HearthStone Resources/StartGameButton.png"));
		}
		
		if ((Player1 == null) & (((JButton) e.getSource()) == view.getHunterButton()
				| ((JButton) e.getSource()) == view.getMageButton()
				| ((JButton) e.getSource()) == view.getPaladinButton()
				| ((JButton) e.getSource()) == view.getPriestButton()
				| ((JButton) e.getSource()) == view.getWarlockButton())) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player1ChooseHero.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		} else if (Player1 != null & (((JButton) e.getSource()) == view.getHunterButton()
				| ((JButton) e.getSource()) == view.getMageButton()
				| ((JButton) e.getSource()) == view.getPaladinButton()
				| ((JButton) e.getSource()) == view.getPriestButton()
				| ((JButton) e.getSource()) == view.getWarlockButton())) {
			ImagePanel x = new ImagePanel(
					new ImageIcon("HearthStone Resources/ChoosingHeroBackgrounds/Player2ChooseHero.jpg").getImage());
			x.add(view.getStartGameHeroes());
			view.clearView();
			view.add(x);
			view.repaint();
			view.revalidate();
		}
		view.getExplainBig().setIcon(chosenImage);
		view.getExplainText().setText(chosenText);
	}

}
