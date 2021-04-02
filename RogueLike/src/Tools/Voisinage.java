package Tools;

import modele.plateau.*;

public class Voisinage {
    private int largeur;

    public Voisinage(int l) {
        largeur = l;
    }


    void setLargeur(int l) {
        this.largeur = l;
    }

    public int indiceGlobal(int posY, int posX) {
        return (posX * largeur + posY);
    }



    public boolean testVoinsin(int entite_a_x,int entite_a_y,int entite_b_x,int entite_b_y) {

        if (indiceGlobal(entite_a_x, entite_a_y) + 1 == indiceGlobal(entite_b_x, entite_b_y)
                || indiceGlobal(entite_a_x, entite_a_y) - 1 == indiceGlobal(entite_b_x, entite_b_y)
                || indiceGlobal(entite_a_x, entite_a_y) + largeur == indiceGlobal(entite_b_x, entite_b_y)
                || indiceGlobal(entite_a_x, entite_a_y) - largeur == indiceGlobal(entite_b_x, entite_b_y)
        ) {
            return true;
        }
        return false;
    }

    public boolean voisinJouerPorte(Heros joueur, EntiteStatique porte) {

        if(porte instanceof PorteVerouille)
        {
            PorteVerouille nouvPorte = (PorteVerouille) porte;
           return (testVoinsin(joueur.getX(),joueur.getY(),nouvPorte.getPosX(),nouvPorte.getPosY()));
        }
        return false;
    }



    public boolean voisinTresorJoueur(Tresor tresor, Heros joueur) {
      return   testVoinsin(tresor.getPosX(), tresor.getPosY(), joueur.getX(), joueur.getY());
    }

    public boolean estPasseSurDalle(DalleUnique dalle,Heros jouer)
    {
        return (dalle.getPosX() == jouer.getX() && dalle.getPosY() == jouer.getY());
    }
}
