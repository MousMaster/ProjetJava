/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import Tools.Aleatoire;
import Tools.Voisinage;

import java.util.Observable;
import java.util.Vector;


public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    private MesDalles mesDalles;
    private int nombreDalle;

    private DalleUnique maDalle;

    public MesDalles getMesDalles() {
        return mesDalles;
    }

    public MesDalles accesDalles() {
        return mesDalles;
    }

    public int getNombreDalle() {
        return this.mesDalles.getNomBreDalle();
    }

    public void setNombreDalle(int nombreDalle) {
        this.nombreDalle = nombreDalle;
    }

    private int pause = 200; // période de rafraichissement

    private Heros heros;
    private Tresor monTresor;

    public Tresor getTresor()
    {
        return monTresor;
    }
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

    public DalleUnique getMaDalle() {
        return maDalle;
    }

    private void initialisationDesEntites() {

        heros = new Heros(this, 4, 4);
        heros.getInventaire().setNombreCle(1);




        addEntiteStatique(new Porte(this), 6, 6);

        //Ajout des deux portes de la salle

        addEntiteStatique(new PorteVerouille(this), 6, 6);
        addEntiteStatique(new PorteVerouille(this), 3, 8);


        Aleatoire alea=new Aleatoire(1,SIZE_Y);
        addEntiteStatique(new PorteVerouille(this),0,alea.genereNombre());

        addEntiteStatique(new PorteVerouille(this),SIZE_X-1,alea.genereNombre());




        maDalle =new DalleUnique(this,3,7);



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

        initTresor();
        //affichage inventaire jours
        heros.getInventaire().afficheInventaire();
        afficherCordonneDalle();


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
            ((PorteVerouille) e).setPosX(x);
            ((PorteVerouille) e).setPosY(y);
        } else {
            if (e instanceof Porte) {
                ((Porte) e).setPosX(x);
                ((Porte) e).setPosY(y);
            } else {
                if(e instanceof DalleUnique)
                {
                    ((DalleUnique) e).setPosX(x);
                    ((DalleUnique) e).setPosY(y);
                }
            }
        }
    }

    //ajout fontion aleatoire


    public void relancer()
    {
        System.lineSeparator();
        System.out.println("Initialisation du jeu");
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleEntitesStatiques[x][y] =null;
            }
        }

        this.initialisationDesEntites();
    }



    public void ouvrePorte() {
        Voisinage voisin =new Voisinage(SIZE_Y);

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] instanceof PorteVerouille) {
                    if (voisin.voisinJouerPorte(heros, grilleEntitesStatiques[x][y]) && heros.getInventaire().getNombreCle() > 0) {
                        System.lineSeparator();
                        System.out.println("Ouverture porte");
                        heros.getInventaire().decNombreCle();
                        heros.getInventaire().afficheInventaire();
                        grilleEntitesStatiques[x][y] = null;
                        addEntiteStatique(new Porte(this), x, y);
                    }
                }
            }
        }
    }

    public void initTresor()
    {
        monTresor=new Tresor();
        monTresor.setOuvert(false);
        monTresor.setPoX(5);
        monTresor.setPoY(5);
        Cle c=new Cle();
        c.setNombreCle(3);
        monTresor.add(c);
    }

    public void visualiserTresor() {
        if(monTresor!=null)
        monTresor.Visionner();
    }
    public void recupererContenuTresor() {
        monTresor.recupererContenuTresor(heros);
    }

    public void verifieDall()
    {
        for(int i=0;i<SIZE_X;i++)
        {
            for(int j=0;j<SIZE_Y;j++)
            {
                if(grilleEntitesStatiques[i][j] instanceof DalleUnique)
                {
                    ((DalleUnique) grilleEntitesStatiques[i][j]).gestionDall(heros);
                }

            }
        }
    }

    public void afficherCordonneDalle()
    {
        for(int i=0;i<SIZE_X;i++)
        {
            for(int j=0;j<SIZE_Y;j++)
            {
                /*
                //if(grilleEntitesStatiques[i][j] instanceof DalleUnique)
                for(int k=0;i<nombreDalle;k++)
                {
                    if((this.mesDalles.accees(i)) instanceof DalleUnique))
                    {
                        System.out.println("Coordonne dalleUnique :"+((DalleUnique) grilleEntitesStatiques[i][j]).posX +" "
                                +((DalleUnique) grilleEntitesStatiques[i][j]).posY);
                    }

                }

                 */

            }
        }
    }





}
