package modele.plateau;

import Tools.Aleatoire;

public class MesDalles {
    private int nomBreDalle;
    private Jeu jeu;
    private DalleUnique tabDalle[];
    private boolean tabDallesBool[];

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
        Aleatoire a=new Aleatoire();

        this.nomBreDalle = a.genereNombreBorneMinMax(20,10);
        tabDalle = new DalleUnique[nomBreDalle];
        tabDallesBool =new boolean[nomBreDalle];

        for(int i=0;i<nomBreDalle;i++) {
            tabDallesBool[i] = false;
        }
    //empeche la supperposition des dalles
        for (int i = 0; i < this.nomBreDalle; i++) {
            if(!tabDallesBool[i])
            tabDalle[i] = new DalleUnique(jeu, a.genereNombreBorne(jeu.getSizeX()-5), a.genereNombreBorne(jeu.getSizeY()-6));
            tabDallesBool[i]=true;
        }
    }
}


