package modele.plateau;

import Tools.Voisinage;

public class PorteVerouille extends EntiteStatique{
    private int posX,posY;
    private int numPorte;

    private boolean ouverte;

    public int getNumPorte() {
        return numPorte;
    }

    public void setNumPorte(int numPorte) {
        this.numPorte = numPorte;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public PorteVerouille(Jeu _jeu) { super(_jeu); }

    public PorteVerouille(Jeu _jeu,int posX,int posY)
    {
        super(_jeu);
        this.posX=posX; this.posY=posY;
        this.ouverte=false;
    }

    @Override
    public boolean traversable() {

        return ouverte;
    }

    public void ouvrir() {
            System.out.println("Ouverture porte");
            System.out.println(" " + this.getNumPorte());
            this.ouverte=true;
    }
}
