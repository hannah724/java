import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;



public class Table extends JPanel implements ActionListener, KeyListener{


	
	private JButton play, add, switchPlayer, call, raise, fold, confirm, newRound, next, showdown, key, back;
	private JTextField bet, blind;
	
	private CardGame cardGame;
	private int max = 0;
	private int screen = 1;
	private int betting, prevScreen;
	private boolean error, error1, callEnabled;
	private Color red = new Color(225, 0, 0);
	private Color black = new Color(0, 0, 0);


	public Table() {

		error = false;
		error1 = false;
		cardGame = new CardGame();	

		cardGame.shuffle();
		cardGame.dealCard();

		//setup buttons

		bet = new JTextField(30);
        bet.setBounds(800, 250, 100, 20);
        this.add(bet);
		bet.setVisible(false);

		blind = new JTextField(30);
        blind.setBounds(425, 300, 150, 30);
        this.add(blind);
		blind.setVisible(false);
		
		
		play = new JButton("Play");
		play.setBounds(800, 35, 150, 60); //x,y,width,height
		play.addActionListener(this);
		add(play);
		play.setVisible(true);

		next = new JButton("Next");
		next.setBounds(425, 340, 150, 60); //x,y,width,height
		next.addActionListener(this);
		add(next);
		next.setVisible(false);

		add = new JButton("Next Round");
		add.setBounds(790, 50, 120, 30); //x,y,width,height
		add.addActionListener(this);
		add(add);
		add.setVisible(false);

		switchPlayer = new JButton("Switch");
		switchPlayer.setBounds(800, 90, 100, 30);
		switchPlayer.addActionListener(this);
		add(switchPlayer);
		switchPlayer.setVisible(true);
		switchPlayer.setEnabled(false);

		call = new JButton("Call");
		call.setBounds(800,130,100,30); //x,y,width,height
		call.addActionListener(this);
		add(call);
		call.setVisible(false);

		fold = new JButton("Fold");
		fold.setBounds(800,170,100,30); //x,y,width,height
		fold.addActionListener(this);
		add(fold);
		fold.setVisible(false);

		raise = new JButton("Raise");
		raise.setBounds(800,210,100,30); //x,y,width,height
		raise.addActionListener(this);
		add(raise);
		raise.setVisible(false);

		confirm = new JButton("Confirm");
		confirm.setBounds(800,280,100,25); //x,y,width,height
		confirm.addActionListener(this);
		add(confirm);
		confirm.setVisible(false);

		//play a new game with updated points - show at end of rounds until a player has nothing to bet anymore
		newRound = new JButton("New Game");
		newRound.setBounds(425, 300, 150, 60); //x,y,width,height
		newRound.addActionListener(this);
		add(newRound);
		newRound.setVisible(false);

		showdown = new JButton("Showdown!");
		showdown.setBounds(800, 330, 100, 60); //x,y,width,height
		showdown.addActionListener(this);
		add(showdown);
		showdown.setVisible(false);

		key = new JButton("Poker Sets");
		key.setBounds(800, 520, 150, 50); //x,y,width,height
		key.addActionListener(this);
		add(key);
		key.setVisible(false);

		back = new JButton("Back");
		back.setBounds(800, 520, 150, 50); //x,y,width,height
		back.addActionListener(this);
		add(back);
		back.setVisible(false);

		
		screen = cardGame.getScreen();
		cardGame.setScreen(screen);

		addKeyListener(this);		

		setFocusable(true);
		setLayout(null);

	}
	
	public Dimension getPreferredSize() {
		//Sets the size of the panel
		return new Dimension(1000,600);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		switch (screen){
			case 1:
				//start screen
				error = false;
				play.setVisible(true);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				next.setVisible(false);
				break;
			case 2:
				//player 1 cards
				play.setVisible(false);
				if (cardGame.getPlayer() == 1 && callEnabled == false){
					add.setEnabled(true);
					add.setVisible(true);
				}
				else{
					add.setVisible(false);
				}
				switchPlayer.setVisible(true);
				call.setVisible(true);
				raise.setVisible(true);
				fold.setVisible(true);
				key.setVisible(true);
				if (max == 5 && (screen == 2 || screen == 3)){
					showdown.setVisible(true);
					add.setEnabled(false);
					bet.setEnabled(false);
					confirm.setEnabled(false);
					switchPlayer.setEnabled(false);
				}
				break;
			case 3:
				//player 2 cards
				play.setVisible(false);
				add.setVisible(true);
				switchPlayer.setVisible(true);
				call.setVisible(true);
				raise.setVisible(true);
				fold.setVisible(true);
				key.setVisible(true);
				if (cardGame.getPlayer() == 2 && callEnabled == false){
					add.setEnabled(true);
					add.setVisible(true);
				}
				else{
					add.setVisible(false);
				}
				if (max == 5 && (screen == 2 || screen == 3) && callEnabled == false){
					showdown.setVisible(true);
					add.setEnabled(false);
					bet.setEnabled(false);
					confirm.setEnabled(false);
					switchPlayer.setEnabled(false);
				}
				break;
			case 4:
				error = false;
				//switching players
				play.setVisible(false);
				add.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				switchPlayer.setVisible(true);
				break;
			case 5:
				error = false;
				//player ___ win!
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(true);
				break;
			case 6:
				//thank you for playing
				error = false;
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(false);
				break;
			case 7:
				//prompt player 1 (or 2 if it's a new round) for blind bet
				error = false;
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(false);
				blind.setVisible(true);
				next.setVisible(true);
				break;
			case 8:
				error = false;
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(false);
				blind.setVisible(false);
				next.setVisible(false);
				break;
			case 9:
				//showdown
				error = false;
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(false);
				blind.setVisible(false);
				next.setVisible(false);
				showdown.setVisible(false);
				break;
			case 10:
				back.setVisible(true);
				error = false;
				play.setVisible(false);
				add.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				fold.setVisible(false);
				newRound.setVisible(false);
				blind.setVisible(false);
				next.setVisible(false);
				showdown.setVisible(false);
				break;
			default:
				//error screen
				error = false;
				cardGame.setScreen(8);
				play.setVisible(false);
				add.setVisible(false);
				bet.setVisible(false);
				confirm.setVisible(false);
				switchPlayer.setVisible(false);
				call.setVisible(false);
				raise.setVisible(false);
				fold.setVisible(false);
				break;
		}
		
		cardGame.drawGame(g);
		if (error == true){
			g.setColor(red);
			Font small = new Font("Serif", Font.PLAIN, 10);
			g.setFont(small);
			g.drawString("Bet has to be greater than highest bet and affordable.", 600, 550);
			g.drawString("Try different action or input a different number.", 600, 570);
			g.setColor(black);
		}
		if (error1 == true){
			g.setColor(red);
			Font small = new Font("Serif", Font.PLAIN, 10);
			g.setFont(small);
			g.drawString("Please input an affordable blind bet.", 415, 435);
			g.setColor(black);
		}
		

		repaint();
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play){
            cardGame.setScreen(7);
			screen = 7;
        }
		if (e.getSource() == next){
			int b = 0;
			error = false;
			error1 = false;
			blind.setVisible(false);
			next.setVisible(false);
			try {
				b = Integer.parseInt(blind.getText());
				System.out.println("Integer value using valueOf(): " + b);
			} catch (NumberFormatException x) {
			    System.err.println("Error: Invalid string format for valueOf().");
			}
			int amt = b;
			if (b > 0 && (cardGame.getP2Points() > amt || cardGame.getP1Points() > amt)){
				if (cardGame.getPlayer() == 1){
					prevScreen = 2;
					if (cardGame.getP1Points() > amt){
						cardGame.raise(1, amt);
						blind.setVisible(false);
						next.setVisible(false);
					}
					else{
						error = true;
					}
				} else if (cardGame.getPlayer() == 2){
					prevScreen = 3;
					if (cardGame.getP2Points() > amt){
						cardGame.raise(2, amt);
						blind.setVisible(false);
						next.setVisible(false);

					}
					else{
						error = true;
					}
				}
				
				blind.setText("");
			}
			else{
				error1 = true;
				error = true;
			}
			if (error == false && error1 == false){
				cardGame.setScreen(3);
				screen = 3;
			}
			prevScreen = screen;
		}
		if (e.getSource() == add){
			screen = 4;
			cardGame.switching();
            if (max < 5){
				cardGame.addComCard();
				max++;
			}
			add.setEnabled(false);
			bet.setVisible(false);
			confirm.setVisible(false);
			switchPlayer.setEnabled(true);
			prevScreen = screen;
			key.setVisible(false);
			back.setVisible(false);
        }
		if (e.getSource() == switchPlayer){
            cardGame.switching();
			screen = cardGame.getScreen();
			if (screen == 2 || screen == 3){
				switchPlayer.setEnabled(false);
				call.setEnabled(true);
				raise.setEnabled(true);
				fold.setEnabled(true);
				callEnabled = true;
				prevScreen = screen;
				back.setVisible(true);
				key.setVisible(true);
			}
			else{
				back.setVisible(false);
				key.setVisible(false);
			}
			
			bet.setVisible(false);
			confirm.setVisible(false);
        }
		if (e.getSource() == call){
            if (screen == 2){
				cardGame.call(1);
			} else if (screen == 3){
				cardGame.call(2);
			}
			call.setEnabled(false);
			raise.setEnabled(false);
			fold.setEnabled(false);
			bet.setVisible(false);
			confirm.setVisible(false);
			switchPlayer.setEnabled(true);
			callEnabled = false;
			if (cardGame.getPlayer() == 1 && callEnabled == false && screen == 2){
				switchPlayer.setEnabled(false);
			}
			else if (cardGame.getPlayer() == 2 && callEnabled == false && screen == 3){
				switchPlayer.setEnabled(false);
			}
        }
		if (e.getSource() == raise){
			bet.setVisible(true);
			error = false;
			confirm.setVisible(true);	
        }
		if (e.getSource() == fold){
			if (screen == 2){
				cardGame.fold(1);
			} else if (screen == 3){
				cardGame.fold(2);
			}
			screen = 5;
			play.setVisible(false);
			add.setVisible(false);
			switchPlayer.setVisible(false);
			call.setVisible(false);
			raise.setVisible(false);
			bet.setVisible(false);
			confirm.setVisible(false);
			fold.setVisible(false);
			newRound.setVisible(true);
			key.setVisible(false);
			callEnabled = false;
			
        }
		if (e.getSource() == confirm){
			error = false;
			try {
				betting = Integer.parseInt(bet.getText());
				System.out.println("Integer value using valueOf(): " + betting);
			} catch (NumberFormatException x) {
			    System.err.println("Error: Invalid string format for valueOf().");
				error = true;
			}
            int amt = betting;
			System.out.println("P1 bet: " + cardGame.getP1Bet());
			System.out.println("Amt: " + amt);
			if (screen == 2){
				if (cardGame.getP1Points() > amt && amt > cardGame.getHighest()){
					cardGame.raise(1, amt);
					bet.setVisible(false);
					confirm.setVisible(false);

					switchPlayer.setEnabled(true);
					call.setEnabled(false);
					raise.setEnabled(false);
					fold.setEnabled(false);
					callEnabled = false;
					bet.setText("");
				}
				else{
					error = true;
				}

			} else if (screen == 3){
				if (cardGame.getP2Points() > amt && amt > cardGame.getHighest()){
					cardGame.raise(2, amt);
					bet.setVisible(false);
					confirm.setVisible(false);
					
					switchPlayer.setEnabled(true);
					call.setEnabled(false);
					raise.setEnabled(false);
					fold.setEnabled(false);
					callEnabled = false;
					bet.setText("");
				}
				else{
					error = true;
				}
			}
			if (cardGame.getPlayer() == 1 && callEnabled == false && screen == 2){
				switchPlayer.setEnabled(false);
			}
			else if (cardGame.getPlayer() == 2 && callEnabled == false && screen == 3){
				switchPlayer.setEnabled(false);
			}
			prevScreen = screen;
        }
		if (e.getSource() == newRound){
			cardGame.reset();
			cardGame.setScreen(1);
			screen = 1;
			error = false;
			play.setVisible(true);
			add.setVisible(false);
			switchPlayer.setVisible(false);
			call.setVisible(false);
			raise.setVisible(false);
			bet.setVisible(false);
			confirm.setVisible(false);
			fold.setVisible(false);
			next.setVisible(false);
			newRound.setVisible(false);
			prevScreen = screen;
		}
		if (e.getSource() == showdown){
			screen = 9;
			cardGame.setScreen(9);
			cardGame.showdown();
			showdown.setVisible(false);
			newRound.setVisible(true);
			key.setVisible(false);
			back.setVisible(false);
			prevScreen = screen;
		}
		if (e.getSource() == key){
			screen = 10;
			cardGame.setScreen(10);
			back.setVisible(true);
			key.setVisible(false);
		}
		if (e.getSource() == back){
			cardGame.setScreen(prevScreen);
			screen = prevScreen;
		}

		screen = cardGame.getScreen();
        repaint();
	}

	//O = 79
	//I = 73
	@Override 
	public void keyPressed(KeyEvent e){
		System.out.println("key: " + e.getKeyCode());
		if( e.getKeyCode() == 79){  //forward
			screen++;
			cardGame.setScreen(screen);
		}
		if( e.getKeyCode() == 73){  //back
			screen--;
			cardGame.setScreen(screen);
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e){}

	@Override
	public void keyTyped(KeyEvent e){}
    


	
}
