/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;

    int orientation;
    private EntiteStatique tabEntite[][];

    public void setTabEntite(EntiteStatique[][] tabEntite) {
        this.tabEntite = tabEntite;
    }

    void setOrientationN() {
        this.orientation = 1;
    }

    void setOrientationS() {
        this.orientation = 2;
    }

    void setOrientationE() {
        this.orientation = 3;
    }

    void setOrientationO() {
        this.orientation = 4;
    }

    public String getOrientation() {
        if (orientation == 1) {
            return "Nord";
        } else if (orientation == 2) {
            return "Sud";
        } else if (orientation == 3) {
            return "Est";
        } else {
            return "Ouest";
        }
    }


    private Jeu jeu;

    private Inventaire inventaire;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }


    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
        inventaire = new Inventaire();
        orientation = 3;
    }

    public void droite() {
        if (traversable(x + 1, y)) {
            x++;

            //  this.affichePos();

            this.setOrientationE();
        }
    }

    public void gauche() {
        if (traversable(x - 1, y)) {
            x--;

            //  this.affichePos();


            this.setOrientationO();


        }
    }

    public void bas() {
        if (traversable(x, y + 1)) {
            y++;

            //   this.affichePos();

            this.setOrientationS();


        }
    }

    public void haut() {
        if (traversable(x, y - 1)) {
            y--;

            //   this.affichePos();

            this.setOrientationN();


        }
    }

    private boolean traversable(int x, int y) {

        if (x >= 0 && x <= jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }

    public void soter()
    {
        if(this.getOrientation()=="Est")
        {
            this.droite();
            this.droite();

        }

        if(this.getOrientation()=="Ouest")
        {
            this.gauche();
            this.gauche();
        }

        if(this.getOrientation()=="Nord")
        {
            this.haut();
            this.haut();
        }

        if(this.getOrientation()=="Sud")
        {
            this.bas();
            this.bas();
        }
    }




    public void affichePos()
    {
        System.out.println("PosX: "+this.getX()+" PosY :"+this.getY()+this.getOrientation() );
    }


}
