package modele.plateau;

public class MesTresors {

    private Jeu jeu;
    private Tresor tabTresor[];
    private int nombreTresors;
    private int nombreTresorOuvert;

    public void decNombreTresor() {
        this.nombreTresorOuvert--;
    }


    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public Tresor[] getTabTresor() {
        return tabTresor;
    }

    public Tresor accesTresor(int i) {
        return tabTresor[i];
    }

    public void setTabTresor(Tresor[] tabTresor) {
        this.tabTresor = tabTresor;
    }

    public int getNombreTresors() {
        return nombreTresors;
    }

    public void setNombreTresors(int nombreTresors) {
        this.nombreTresors = nombreTresors;
    }

    public void init() {
        nombreTresors = 5;
        this.nombreTresors = nombreTresors;
        tabTresor = new Tresor[nombreTresors];
        for (int i = 0; i < nombreTresors; i++) {
            tabTresor[i] = new Tresor();
        }

        for (int i = 0; i < nombreTresors; i++) {
            tabTresor[i].init(jeu, i);
            tabTresor[i].setOuvert(false);
        }
    }

    public void affichePos() {
        for (int i = 0; i < this.nombreTresors; i++) {
            System.out.println("Pos T " + i + " " + tabTresor[i].getPosX() + " " + " " + tabTresor[i].getPosY());
        }
    }


    public void recupererContenuTresor() {
        for (int i = 0; i < jeu.SIZE_X - 1; i++) {
            for (int j = 0; j < jeu.SIZE_Y - 1; j++) {
                for (int k = 0; k < this.getNombreTresors(); k++) {

                    if (this.accesTresor(k).getPosX() == i && this.accesTresor(k).getPosY() == j) {
                        if (isVoisin(jeu.getHeros(), i, j))
                            this.accesTresor(k).recupererContenuTresor(jeu.getHeros());
                    }
                }

            }
        }
    }

    public void affichTresor() {
        for (int i = 0; i < jeu.SIZE_X - 1; i++) {
            for (int j = 0; j < jeu.SIZE_Y - 1; j++) {
                for (int k = 0; k < this.getNombreTresors(); k++) {
                    if (this.accesTresor(k).getPosX() == i && this.accesTresor(k).getPosY() == j) {
                        {
                            if (isVoisin(jeu.getHeros(), i, j)) {
                                this.accesTresor(k).Visionner();
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean isVoisin(Heros joueur, int px, int py) {
        return ((joueur.getX() == px - 1 && joueur.getY() == py)
                || (joueur.getX() == px + 1 && joueur.getY() == py)
                || (joueur.getX() == px && joueur.getY() == py + 1)
                || (joueur.getX() == px && joueur.getY() == py - 1));
    }

}