package modele.plateau;

import Tools.Aleatoire;
import modele.plateau.CaseNormale;

public class DalleUnique extends CaseNormale {
    boolean dejaTraveree;
    private int posX,posY;


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

    public void etindreFeux()
    {
        this.dejaTraveree=false;
    }

    public void incendier() {
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
                System.out.println("Incendiée");
            }
            this.incendier();
        }
    }

    public void  gestionDallF(Heros joueur)
    {
        if(joueur.getInventaire().getNombreCapsule()>0)
        {
            if((this.isDejaTraveree() && joueur.getX()==this.posX-1 && joueur.getY()==this.getPosY() && joueur.getOrientation()=="Est")
                    || (this.isDejaTraveree() && joueur.getX()==this.posX+1 && joueur.getY()==this.getPosY() && joueur.getOrientation()=="Ouest")
                    || (this.isDejaTraveree() && joueur.getX()==this.posX && joueur.getY()==this.getPosY()+1 && joueur.getOrientation()=="Nord")
                    || (this.isDejaTraveree() && joueur.getX()==this.posX && joueur.getY()==this.getPosY()-1 && joueur.getOrientation()=="Sud"))
            {
                System.out.println("Etindre feu");
                this.etindreFeux();
                joueur.getInventaire().decNombreCapsules();
                joueur.getInventaire().afficheInventaire();
            }
        }else
        {
            System.out.println("Pas de capsule");

        }
    }




    public void affichePosition()
    {
        System.out.println("Position dalle :"+this.posX+" , "+this.posY);
    }
}
