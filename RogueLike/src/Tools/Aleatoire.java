package Tools;

public class Aleatoire {
    int max;
    int min;

    int Size_X,Size_y;

    public  Aleatoire()
    {

    }
    public Aleatoire(int m,int n){
        this.Size_X=m;
        this.Size_y=n;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int genereNombre()
    {
        int random_int = (int)Math.floor(Math.random()*(max-2+1)+2);
        return random_int;
    }

    public int genereX()
    {
       int x = (int)Math.floor(Math.random()*(Size_X-2 + 1)+2);
       return x;
    }

    public int genereY()
    {
        int y = (int)Math.floor(Math.random()*(Size_y-2+1)+2);
        return y;
    }

    public int genereNombreBorne(int leMax)
    {
        int y = (int)Math.floor(Math.random()*(leMax-2+1)+2);
        return y;
    }

    public int genereNombreBorneMinMax(int leMax,int leMin)
    {
        int y = (int)Math.floor(Math.random()*(leMax-leMin)+leMin);
        return y;
    }
}
