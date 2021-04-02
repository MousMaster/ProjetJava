package Tools;

public class Aleatoire {
    int max;
    int min;

    public  Aleatoire()
    {

    }
    public Aleatoire(int m,int n){
        this.max=m;
        this.min=n;
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
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }



}
