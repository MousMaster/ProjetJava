package modele.plateau;

public class Salle {

    public static final int SIZE_X = 50;
    public static final int SIZE_Y = 10;
    int tailleLongueur=10;
    int tailleLargeur=5;
    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];
    private Jeu jeu;

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public void setGrilleEntitesStatiques(EntiteStatique[][] grille)
    {
        this.grilleEntitesStatiques=grille;
    }


    public void initMur() {
        // murs extérieurs verticaux
        for (int y = 1; y < tailleLargeur-1; y++) {
            if (this.getEntite(0, y) == null) {
                addEntiteStatique(new Mur(jeu), 0, y);
            }

            if (this.getEntite(tailleLongueur-1, y) == null) {
                addEntiteStatique(new Mur(jeu), tailleLongueur-1, y);
            }
        }

        // murs extérieurs horizontaux
        for (int x = 0; x < tailleLongueur-1; x++) {
            if (this.getEntite(x, 0) == null) {
                addEntiteStatique(new Mur(jeu), x, 0);

            }

            if (this.getEntite(x, tailleLargeur-1) == null) {
                addEntiteStatique(new Mur(jeu), x, tailleLargeur-1);

            }
        }
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(jeu);
                }
            }
        }
    }
    public void initMurSall(int numSall) {
        // murs extérieurs verticaux
        for (int y = numSall; y < numSall+tailleLargeur; y++) {
            if (this.getEntite(0, y) == null) {
                addEntiteStatique(new Mur(jeu), 0, y);
            }

            if (this.getEntite(tailleLongueur-1, y) == null) {
                addEntiteStatique(new Mur(jeu), tailleLongueur-1, y);
            }
        }

        // murs extérieurs horizontaux
        for (int x = numSall; x < numSall+tailleLongueur; x++) {
            if (this.getEntite(x, 0) == null) {
                addEntiteStatique(new Mur(jeu), x, 0);

            }

            if (this.getEntite(x, tailleLargeur-1) == null) {
                addEntiteStatique(new Mur(jeu), x, tailleLargeur-1);

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


