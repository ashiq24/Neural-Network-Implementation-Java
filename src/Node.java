/**
 * Created by Ashiqur Rahman on 3/25/2017.
 */
import java.util.Random;
public class Node {
    double weights[];
    double delta;
    double X;
    double S;

    public Node(int n){
        this.weights = new double[n];
        Random r=new Random();
        for( int i=0;i<weights.length;i++) weights[i]=0;//r.nextDouble();
    }
}

