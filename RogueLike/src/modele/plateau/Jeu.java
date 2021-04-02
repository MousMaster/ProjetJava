/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.util.Observable;


public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    private int pause = 200; // période de rafraichissement

    private Heros heros;

    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];

    public Jeu() {
        initialisationDesEntites();
    }

    public Heros getHeros() {
        return heros;
    }

    public EntiteStatique[][] getGrille() {
        return grilleEntitesStatiques;
    }

	public EntiteStatique getEntite(int x, int y) {
		if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
			// L'entité demandée est en-dehors de la grille
			return null;
		}
		return grilleEntitesStatiques[x][y];
	}

    private void initialisationDesEntites() {
        heros = new Heros(this, 4, 4);
        heros.setNombreCle(1);


        addEntiteStatique(new Mur(this), 2, 6);
        addEntiteStatique(new Mur(this), 3, 6);

        addEntiteStatique(new Porte(this), 6, 6);

        //Ajout des deux portes de la salle

        addEntiteStatique(new PorteVerouille(this), 6, 6);
        addEntiteStatique(new PorteVerouille(this), 3, 8);


        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            if(this.getEntite(0,y)==null)
            {
                addEntiteStatique(new Mur(this), 0, y);
            }

            if(this.getEntite(19,y)==null) {
                addEntiteStatique(new Mur(this), 19, y);
            }
        }

        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            if(this.getEntite(x,0)==null)
            {
                addEntiteStatique(new Mur(this), x, 0);

            }

            if(this.getEntite(x,9)==null)
            {
                addEntiteStatique(new Mur(this), x, 9);

            }
        }









        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(this);
                }
            }
        }


    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while(true) {

            setChanged();
            notifyObservers();

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    private void addEntiteStatique(EntiteStatique e, int x, int y) {
        grilleEntitesStatiques[x][y] = e;

        if (e instanceof PorteVerouille) {
            PorteVerouille nouV = (PorteVerouille) e;
            ((PorteVerouille) e).setPosX(x);
            ((PorteVerouille) e).setPosY(y);
        } else {
            if (e instanceof Porte) {
                Porte nouv = (Porte) e;
                ((Porte) e).setPosX(x);
                ((Porte) e).setPosY(y);
            }
        }
    }

    //ajout fontion aleatoire
    private int aleatoirY()
    {
         int max =SIZE_Y-2;
         int min =2;
         int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
         return random_int;
    }

    public void relancer()
    {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleEntitesStatiques[x][y] =null;
            }
        }

        this.initialisationDesEntites();
    }

    public int indiceGlobal(int posY,int posX)
    {
        return (posX*SIZE_Y+posY);
    }

    //test voisinage
    public boolean voisin(Heros joueur,EntiteStatique porte)
    {
        if(porte instanceof PorteVerouille)
        {
            PorteVerouille nouvPorte = (PorteVerouille) porte;
            if(indiceGlobal(nouvPorte.getPosX(),nouvPorte.getPosY()) + 1 == indiceGlobal(joueur.getX(),joueur.getY())
                    || indiceGlobal(nouvPorte.getPosX(),nouvPorte.getPosY()) -1 == indiceGlobal(joueur.getX(),joueur.getY())
                        || indiceGlobal(nouvPorte.getPosX(),nouvPorte.getPosY()) + SIZE_Y == indiceGlobal(joueur.getX(),joueur.getY())
                    || indiceGlobal(nouvPorte.getPosX(),nouvPorte.getPosY()) - SIZE_Y == indiceGlobal(joueur.getX(),joueur.getY())
                    )
            {
                return true;
            }
        }
        return false;
    }



    public void ouvrePorte() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(grilleEntitesStatiques[x][y] instanceof PorteVerouille)
                {
                    if(voisin(heros,grilleEntitesStatiques[x][y]) && heros.getNombreCle()>0)
                    {
                            heros.decNombreCle();
                            grilleEntitesStatiques[x][y]=null;
                            addEntiteStatique(new Porte(this), x, y);
                    }
                }
            }
        }
    }

}
