/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import Tools.Voisinage;

import java.util.Observable;


public class Jeu extends Observable implements Runnable {
    public static final int NBRS =5 ;
    public static final int SIZE_X = 13 *NBRS ;
    public static final int SIZE_Y = 7;

    private MesTresors mesTresors;
    private MesDalles mesDalles;
    private MesCasesInterdites interdites;

    //
    private Tresor monTresor;





    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];

    public Tresor getTresor(){
        return monTresor;
    }

    private boolean aideVu =false;



    private int pause = 200; // période de rafraichissement

    private Heros heros;

    public int getSizeY(){return this.SIZE_Y;}
    public int getSizeX(){return this.SIZE_X;}

    public EntiteStatique[][] getGrilleEntitesStatiques() {
        return grilleEntitesStatiques;
    }
    public MesDalles getMesDalles() {
        return mesDalles;
    }


    public int getNombreDalle() {
        return this.mesDalles.getNomBreDalle();
    }


    public int getNombreTresor()
    {
        return mesTresors.getNombreTresors();
    }


    public Tresor accesTreso(int i)
    {
        return mesTresors.accesTresor(i);
    }

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

        heros.getInventaire().afficheInventaire();
        heros.setTabEntite(this.grilleEntitesStatiques);
        initSall();
        initDalles();
        initTresor();

    }

    public void initSall()
    {
        Salle s = new Salle();
        s.setJeu(this);
        s.setGrilleEntitesStatiques(this.grilleEntitesStatiques);
        s.initMur();
        s.initPorte();
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
                if(e instanceof DalleUnique)
                {
                    ((DalleUnique) e).setPosX(x);
                    ((DalleUnique) e).setPosY(y);
                }
            }
        }


    public void relancer()
    {
        aideVu=false;
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

        if(heros.getInventaire().getNombreCle()>0)
        {
            for (int x = 0; x < SIZE_X; x++) {
                for (int y = 0; y < SIZE_Y; y++) {
                    if (grilleEntitesStatiques[x][y] instanceof PorteVerouille) {
                        if (voisin.voisinJouerPorte(heros, grilleEntitesStatiques[x][y]) && heros.getInventaire().getNombreCle() > 0) {
                            PorteVerouille nouv =(PorteVerouille)  grilleEntitesStatiques[x][y];
                            nouv.ouvrir();
                            heros.getInventaire().decNombreCle();
                            heros.getInventaire().afficheInventaire();
                            heros.getInventaire().setNombreCapsule(0);
                        }
                    }
                }
            }
        }else
        {
            System.out.println("Manque de cle impossible d'ouvrir la porte");
        }
    }


    public void initTresor()
    {
        mesTresors = new MesTresors();
        mesTresors.setJeu(this);
        mesTresors.init();
        mesTresors.affichePos();
    }

    public void recupererContenuTresor() {
        mesTresors.recupererContenuTresor();
    }

    public void affichTresor()
    {
        mesTresors.affichTresor();
    }

    public void verifieDall()
    {

        for(int i=0;i<SIZE_X;i++)
        {
            for(int j=0;j<SIZE_Y;j++)
            {
                if(grilleEntitesStatiques[i][j] instanceof CaseInterdite)
                {
                    if(grilleEntitesStatiques[i+1][j]instanceof CaseNormale && heros.getOrientation()=="Est")
                    {
                        //heros.soter();
                    }


                   // heros.soter(maCase,this.grilleEntitesStatiques);
                }

            }
        }
        mesDalles.gestionDalles();
       // heros.soter();
    }

    public void etindrerFeu()
    {
        mesDalles.gestionDallesF();
    }
    public void initDalles()
    {
        interdites =new MesCasesInterdites(this);
        interdites.initialiser();

        mesDalles= new MesDalles(this);
        mesDalles.initialiser();

        for(int i=0;i<mesDalles.getNomBreDalle();i++)
        {
            addEntiteStatique(mesDalles.accees(i),mesDalles.accees(i).getPosX(),mesDalles.accees(i).getPosY() );
            addEntiteStatique(interdites.accees(i),interdites.accees(i).posX,interdites.accees(i).posY );
        }
    }

    public void commentJouer()
    {   if(!aideVu)
        System.out.println("Appuyer sur H pour afficher l'aide");
    }

    public void help()
    {
        System.out.println("Les commandes ");
        System.lineSeparator();
        System.out.println("Fleches : dirrige le prof");
        System.lineSeparator();
        System.out.println(" I :affiche inventaire");
        System.lineSeparator();
        System.out.println(" T :affiche contenu coffre");
        System.lineSeparator();
        System.out.println(" C : recupere cle");
        System.lineSeparator();
        System.out.println(" O :ouvre porte");
        System.lineSeparator();
        System.out.println(" R :pour reprendre");
        System.lineSeparator();
        aideVu=true;
    }


}
