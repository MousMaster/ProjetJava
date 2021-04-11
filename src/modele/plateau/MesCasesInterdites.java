package modele.plateau;

import Tools.Aleatoire;

public class MesCasesInterdites {
    private int nomBreCase;
    private Jeu jeu;
    private CaseInterdite tabCase[];
    private boolean tabCasesBool[];
    private EntiteStatique[][] grilleEntitesStatiques ;


    public MesCasesInterdites(Jeu jeu) {
        this.jeu = jeu;
    }

    public int getNomBre() {
        return this.nomBreCase;
    }

    public CaseInterdite MesCasesInterdites(int i) {
        return this.tabCase[i];
    }

    public void initialiser() {
        grilleEntitesStatiques =new EntiteStatique[jeu.getSizeX()][jeu.getSizeY()];

        Aleatoire a=new Aleatoire();

        this.nomBreCase = 5*jeu.NBRS ;
        tabCase = new CaseInterdite[nomBreCase];
        tabCasesBool =new boolean[nomBreCase];

        for(int i=0;i<nomBreCase;i++) {
            tabCasesBool[i] = false;
        }
        //empeche la supperposition des dalles
        for( int z = 0 ; z <jeu.NBRS ; z++){
            for (int i = 0 + z*5 ; i < 5+5*z; i++) {
                if(!tabCasesBool[i])
                    tabCase[i] = new CaseInterdite(jeu, a.genereNombreBorne(Salle.SIZE_X-1)+Salle.SIZE_X*z, a.genereNombreBorne(Salle.SIZE_Y-2));
                tabCasesBool[i]=true;
            }
        }
    }
}



