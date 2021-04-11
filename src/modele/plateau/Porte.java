package modele.plateau;

public class Porte extends EntiteStatique{
    private int posX,posY;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public Porte(Jeu _jeu) { super(_jeu); }
    @Override
    public boolean traversable() {
        return true;
    }
}
