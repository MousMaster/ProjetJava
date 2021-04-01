package modele.plateau;

public class Porte extends EntiteStatique{
    public Porte(Jeu _jeu) { super(_jeu); }
    @Override
    public boolean traversable() {
        return true;
    }
}
