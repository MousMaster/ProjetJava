package modele.plateau;

public class Cle implements Elem{
    private int type_objet;
    int posX,posY;

    private int nombreCle=4;

    public int getNombreCle()
    {
        return nombreCle;
    }

    public void setNombreCle(int n)
    {
        nombreCle=n;
    }

    public int getPosX()
    {
        return posX;
    }
    public int getPosY()
    {
        return posY;
    }
    public void setPoX(int p)
    {
        this.posX=p;
    }
    public void setPoY(int p)
    {
        this.posY=p;
    }
    @Override
    public int getTypeObjet() {
        return type_objet;
    }
}
