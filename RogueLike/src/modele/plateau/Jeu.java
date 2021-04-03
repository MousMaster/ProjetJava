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

    public static final int SIZE_X = 50;
    public static final int SIZE_Y = 10;
    public static final int SIZE_X_S = 10;
    public static final int SIZE_Y_S = 5;
    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];

    private boolean aideVu =false;


    private int pause = 200; // période de rafraichissement

    private Heros heros;

    public int getSizeY(){return this.SIZE_Y;}
    public int getSizeX(){return this.SIZE_X;}

    private MesDalles mesDalles;
    private Tresor monTresor;
    private int nombreDalle;

    private DalleUnique maDalle;

    public MesDalles getMesDalles() {
        return mesDalles;
    }


    public int getNombreDalle() {
        return this.mesDalles.getNomBreDalle();
    }




    public Tresor getTresor()
    {
        return monTresor;
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

    public DalleUnique getMaDalle() {
        return maDalle;
    }

    private void initialisationDesEntites() {

        heros = new Heros(this, 4, 4);

        heros.getInventaire().afficheInventaire();
        //appel des fonctions d'initialisation des contenus
        //initMur();
        Salle s = new Salle();
        s.setJeu(this);
        s.setGrilleEntitesStatiques(this.grilleEntitesStatiques);
        //s.initMur();
        for (int i = 0; i < 2;i++) {
            s.initMurSall(i);
        }
        s.applique();
        initPorte();
        initDalles();
        initTresor();
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
    private void addEntiteStatiqueToTab(EntiteStatique e, int x, int y)
    {

    }
/*
    public void addEntiteStatiqueP(EntiteStatique e, int x, int y)
    {
        addEntiteStatique(EntiteStatique e, int x, int y);
    }

 */
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
       }else
       {
           System.out.println("Manque de cle impossible d'ouvrir la porte");
       }
    }

    public void initTresor()
    {
        monTresor =new Tresor();
        monTresor.init(this);

    }

    public void initDalles()
    {
        mesDalles= new MesDalles(this);
        mesDalles.initialiser();

        for(int i=0;i<mesDalles.getNomBreDalle();i++)
        {
            addEntiteStatique(mesDalles.accees(i),mesDalles.accees(i).posX,mesDalles.accees(i).posY );
            //mesDalles.accees(i).affichePosition();
        }

    }


    public void recupererContenuTresor() {
        Voisinage voinsin =new Voisinage(SIZE_Y);
        if(voinsin.voisinTresorJoueur(monTresor,heros))
        monTresor.recupererContenuTresor(heros);
        System.out.println("Tresor non detecte");
    }

    public void visualiserTresor( ) {
        Voisinage voinsin =new Voisinage(this.getSizeY());
        if(this!=null)
            if(voinsin.voisinTresorJoueur(monTresor,heros))
                monTresor.Visionner();
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

    public void initPorte()
    {
        Aleatoire a =new Aleatoire();
        PorteVerouille porte1 =new PorteVerouille(this);
        porte1.setPosY(a.genereNombreBorneMinMax(SIZE_Y-1,2));
        porte1.setPosX(0);
        PorteVerouille porte2 =new PorteVerouille(this);
        porte2.setPosY(a.genereNombreBorneMinMax(SIZE_Y-1,2));
        porte2.setPosX(SIZE_X-1);
        addEntiteStatique(porte1,porte1.getPosX(),porte1.getPosY());
        addEntiteStatique(porte2,porte2.getPosX(),porte2.getPosY());
    }

    public void initMur()
    {
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
