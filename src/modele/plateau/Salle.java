package modele.plateau;

import Tools.Aleatoire;
import static modele.plateau.Jeu.NBRS;
import static modele.plateau.Jeu.SIZE_X;
import static modele.plateau.Jeu.SIZE_Y;

public class Salle {

    public static final int SIZE_X = 12;
    public static final int SIZE_Y = 7;
    public int NumSalle = 0 ;
    int tailleLongueur=10;
    int tailleLargeur=5;
    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];
    private Jeu jeu;
    public Salle (){
        this.NumSalle = NumSalle ;
         NumSalle++;
    }
    
    public int getSize_X(){
        return this.SIZE_X ;
    }
    
    public int getSize_Y(){
        return this.SIZE_Y ;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public void setGrilleEntitesStatiques(EntiteStatique[][] grille)
    {
        this.grilleEntitesStatiques=grille;
    }

public void initPorte()
    {
        Aleatoire a =new Aleatoire();
        /*PorteVerouille porte1 =new PorteVerouille(this);
        porte1.setPosY(a.genereNombreBorneMinMax(Salle.SIZE_Y-1,1));
 
        porte1.setPosX(0);*/
       for(int i=0 ; i<jeu.NBRS ; i++){
        PorteVerouille porte2 =new PorteVerouille(jeu);
        porte2.setPosY(a.genereNombreBorne(5));
        //porte2.setPosX(SIZE_X-1);
        porte2.setPosX(12+12*i);
        //addEntiteStatique(porte1,porte1.getPosX(),porte1.getPosY());
        addEntiteStatique(porte2,porte2.getPosX(),porte2.getPosY());
    }}

    public void initMur() {
        // murs extérieurs verticaux
        for ( int i =0; i<jeu.NBRS;i++){
        for (int y = 0; y < SIZE_Y; y++) {
            if (this.getEntite(0, y) == null) {
                addEntiteStatique(new Mur(jeu), (this.NumSalle-1)*SIZE_X, y);
            }
             if (this.getEntite(SIZE_X+SIZE_X*i, y) == null) {
                addEntiteStatique(new Mur(jeu), SIZE_X+SIZE_X*i, y);
            }}
          
        }

        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X*Jeu.NBRS; x++) {
            if (this.getEntite(x, 0) == null) {
                addEntiteStatique(new Mur(jeu), x, 0);

            }

            if (this.getEntite(x, SIZE_Y-1) == null) {
                addEntiteStatique(new Mur(jeu), x, SIZE_Y-1);

            }
        }
       for ( int i =0 ; i < jeu.NBRS ; i++){
        for (int x = 0 + SIZE_X*i; x < SIZE_X+SIZE_X*i; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(jeu);
                   
                }
            }
            }
        }
    }

    public void applique()
    {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(jeu);
                }
            }
        }
    }




    private void addEntiteStatique(EntiteStatique e, int x, int y) {
        grilleEntitesStatiques[x][y] = e;

        if (e instanceof PorteVerouille) {
            ((PorteVerouille) e).setPosX(x);
            ((PorteVerouille) e).setPosY(y);
        } else {
            if (e instanceof Porte) {
                ((Porte) e).setPosX(x);
                ((Porte) e).setPosY(y);
            } else {
                if (e instanceof DalleUnique) {
                    ((DalleUnique) e).setPosX(x);
                    ((DalleUnique) e).setPosY(y);
                }
            }
        }
    }

    public EntiteStatique getEntite(int x, int y) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
            // L'entité demandée est en-dehors de la grille
            return null;
        }
        return grilleEntitesStatiques[x][y];
    }
}


