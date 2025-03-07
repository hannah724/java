import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class Card {

    private String name;
    private int value;
    private String suit;
    private BufferedImage suitImage, back;
    private Color white = new Color(255, 255, 255);
    private Color black = new Color(0, 0, 0);
    private Color red = new Color(255, 0, 0);
    private boolean top = true;

    public Card(String name, int value, String suit){
        this.name = name;
        this.value = value;
        this.suit = suit;
        //top = true means facing up

        if(suit.equals("heart")){
            try{
                suitImage = ImageIO.read(new File("heart.png"));
            } catch(IOException e){
                System.out.println(e);
            }
        }
        else if(suit.equals("spade")){
            try{
                suitImage = ImageIO.read(new File("spade.png"));
            } catch(IOException e){
                System.out.println(e);
            }
        }
        else if(suit.equals("club")){
            try{
                suitImage = ImageIO.read(new File("club.png"));
            } catch(IOException e){
                System.out.println(e);
            }
        }
        else if(suit.equals("diamond")){
            try{
                suitImage = ImageIO.read(new File("diamond.png"));
            } catch(IOException e){
                System.out.println(e);
            }
        }
        try{
            back = ImageIO.read(new File("back.png"));
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public void setCard(String type){
        suit = type;
    }

    public void drawMe(Graphics g, int x, int y){
        //draw card white
        if (top == true){
            g.setColor(white);
            g.fillRect(x, y, 120, 150);
            g.setColor(black);
            g.drawRect(x, y, 120, 150);
            
            //draw name
            if(suit.equals("heart") || suit.equals("diamond")){
                g.setColor(red);
            }
            else{
                g.setColor(black);
            }

            Font font = new Font("Serif", Font.PLAIN, 50);

            g.setFont(font);
            if(name.equals("10")){
                g.drawString(name, x + 30, y + 100);
            }
            else{
                g.drawString(name, x + 40, y + 100);
            }
            
            Font small = new Font("Serif", Font.PLAIN, 20);
            g.setFont(small);
            g.drawString(name, x + 8, y + 25);

            //draws suit
            if (suit.equals("heart")){
                g.drawImage(suitImage, x + 8, y + 30, 15, 15, null);
            }
            else{
                g.drawImage(suitImage, x + 2, y + 26, 25, 25, null);
            }
        }
        else{
            g.drawImage(back, x, y, 120, 150, null);
        }
    }

    public int getValue(){
        return value;
    }

    public String getSuit(){
        return suit;
    }

    public void flip(){
        top = !top;
    }

    public void setSide(boolean up){
        top = up;
    }

}
