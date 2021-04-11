package modele.plateau;

import Tools.Aleatoire;

public class MesDalles {
    private int nomBreDalle;
    private Jeu jeu;
    private DalleUnique tabDalle[];
    private boolean tabDallesBool[];
    private EntiteStatique[][] grilleEntitesStatiques ;


    public MesDalles(Jeu jeu) {
        this.jeu = jeu;
    }

    public int getNomBreDalle() {
        return this.nomBreDalle;
    }

    public DalleUnique accees(int i) {
        return this.tabDalle[i];
    }

    public void initialiser() {
        grilleEntitesStatiques =new EntiteStatique[jeu.getSizeX()][jeu.getSizeY()];

        Aleatoire a=new Aleatoire();

        this.nomBreDalle = 5*jeu.NBRS ;
        tabDalle = new DalleUnique[nomBreDalle];
        tabDallesBool =new boolean[nomBreDalle];

        for(int i=0;i<nomBreDalle;i++) {
            tabDallesBool[i] = false;
        }
        //empeche la supperposition des dalles
        for( int z = 0 ; z <jeu.NBRS ; z++){
            for (int i = 0 + z*5 ; i < 5+5*z; i++) {
                if(!tabDallesBool[i])
                    tabDalle[i] = new DalleUnique(jeu, a.genereNombreBorne(Salle.SIZE_X-1)+Salle.SIZE_X*z, a.genereNombreBorne(Salle.SIZE_Y-2));
                tabDallesBool[i]=true;
            }
        }



    }

    public void gestionDalles()
    {
        for(int i=0;i<jeu.getSizeX();i++)
        {
            for(int j=0;j<jeu.getSizeY();j++)
            {
                if(jeu.getGrilleEntitesStatiques()[i][j] instanceof DalleUnique)
                {
                    ((DalleUnique) jeu.getGrilleEntitesStatiques()[i][j]).gestionDall(jeu.getHeros());
                }

            }
        }
    }

    public void gestionDallesF()
    {
        for(int i=0;i<jeu.getSizeX();i++)
        {
            for(int j=0;j<jeu.getSizeY();j++)
            {
                if(jeu.getGrilleEntitesStatiques()[i][j] instanceof DalleUnique)
                {
                    ((DalleUnique) jeu.getGrilleEntitesStatiques()[i][j]).gestionDall(jeu.getHeros());
                }

            }
        }
    }
}


