package modele.plateau;

public class PorteNonTraverssable extends Porte{
    public PorteNonTraverssable(Jeu _jeu) { super(_jeu); }
    @Override
    public boolean traversable() {
        return false;
    }
}
