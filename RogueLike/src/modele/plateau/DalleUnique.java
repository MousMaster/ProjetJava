package modele.plateau;

import Tools.Aleatoire;
import modele.plateau.CaseNormale;

public class DalleUnique extends CaseNormale {
    boolean dejaTraveree;
    int posX,posY;




    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isDejaTraveree() {
        return dejaTraveree;
    }

    public void fermer() {
        this.dejaTraveree = true;
    }

    public DalleUnique(Jeu _jeu) {
        super(_jeu);
        this.dejaTraveree=false;
    }
    public DalleUnique(Jeu _jeu,int px,int py) {
        super(_jeu);
        this.dejaTraveree=false;
        this.posX=px;
        this.posY=py;
    }


    @Override
    public boolean traversable() {
        return !dejaTraveree;
    }

    public void  gestionDall(Heros joueur)
    {
        if(joueur.getX()==this.posX && joueur.getY()==this.posY)
        {
            if(!this.dejaTraveree)
            {
                System.out.println("Fermeture dalle");
            }
            this.fermer();
        }
    }
    public void init(int largeur)
    {
        Aleatoire a=new Aleatoire(largeur,2);
        this.posX=a.genereNombre();
        this.posY=a.genereNombre();
        //this.posX=;
        //this.posY=;
    }

    public void affichePosition()
    {
        System.out.println("Position dalle :"+this.posX+" , "+this.posY);
    }
}