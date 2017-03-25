import java.io.IOException;
import java.util.Random;

/**
 * Created by Ashiqur Rahman on 3/25/2017.
 */
public class NeuralNet {
    Input input;
    int working_input;
    Node net[][];
    int layers;
    int feachers;
    int size;
    double learning_rate;
    Node ara[];
    NeuralNet(int l, int f, int s)
    {
        feachers=f;
        size=s;
        layers=l;
        input=new Input(f,s);
        net=new Node[l][f];
        learning_rate=0.1;
        for(int i=0;i<l;i++)
        {
            for( int j=0;j<f;j++) net[i][j]=new Node(f);
        }
    }
    double nonlinear(double s)
    {
        if(s>-90)return 1/(1+Math.exp(-1*s));
        else return 1/10000000.01;
    }
    double activate(int i, int j)
    {
        double sum=0;
        if(i==0)
        {

            int ran=working_input;
            for (int n = 0; n < feachers; n++)
                sum += net[i][j].weights[n] * input.inputs[ran][n];
        }
        else {
            for (int n = 0; n < feachers; n++)
                sum += net[i][j].weights[n] * net[i - 1][n].X;
        }
        return sum;
    }

    void ForwordPropagate()
    {
        Random r=new Random();
        working_input=(int) r.nextDouble()*size;
        for( int i=0;i<layers;i++)
        {
            for( int j=0;j<feachers;j++)
            {
                double s=activate(i,j);
                net[i][j].S=s;
                net[i][j].X=s;
                if(i!=layers-1 || 1==1)net[i][j].X=nonlinear(s);
            }
        }
    }
    /**here error calculation is a simple squared error. and as a non linearity sigmoid
     * function is used.
     * below start the main back propagate part
     */
    double derivativenonlinear(int i, int j)
    {
        return net[i][j].X*(1-net[i][j].X);
    }
    double finalDelta(int layers)
    {
        int pos=layers-1;
        net[pos][0].delta=2*(input.inputs[working_input][feachers]-net[pos][0].X)*derivativenonlinear(pos,0);
        return net[pos][0].delta;
    }
    void Backpropagate()
    {
        for( int i=layers-2;i>=0;i--)
        {
            if(i==feachers-2)
            {
                for( int j=0;j<feachers;j++)
                {
                    double sum=0;
                    sum+=net[i+1][0].delta*net[i+1][0].weights[j];
                    sum=sum*derivativenonlinear(i,j);
                    net[i][j].delta=sum;

                }
            }
            else
            {
                for( int j=0;j<feachers;j++)
                {
                    double sum=0;
                    for(int k=0;k<feachers;k++)sum+=net[i+1][k].delta*net[i+1][k].weights[j];
                    sum=sum*derivativenonlinear(i,j);
                    net[i][j].delta=sum;
                }
            }
        }

    }

    void Update()
    {
        for(int i=0;i<layers;i++)
        {
            for( int j=0;j<feachers;j++)
            {
                if(i==0)
                {
                    for (int k = 0; k < feachers; k++) {
                        net[i][j].weights[k] -= net[i][j].delta * input.inputs[working_input][k] * learning_rate;
                    }
                }
                else {
                    for (int k = 0; k < feachers; k++) {
                        net[i][j].weights[k] -= net[i][j].delta * net[i - 1][k].X * learning_rate;
                    }
                }



            }
        }
    }


    void  teach(int times)
    {
        while(times!=0)
        {
            ForwordPropagate();
            finalDelta(layers);
            Backpropagate();
            Update();
            times--;
        }

        System.out.println("TRAIN DONE ONCE\n");
    }

    double Error()
    {
        int r=0,w=0;
        for( int n=0;n<size;n++)
        {
            working_input=n;
            ForwordPropagate();
            if(Math.abs(net[layers-1][0].X-input.inputs[n][feachers])<=0.5)r++;
            else w++;
        }
        System.out.println(r+" "+w);
        return r*100/(r+w);
    }

    public static void main(String[] args) {
        Input input=new Input(9,699);
        NeuralNet NN=new NeuralNet(3,9,699);
        NN.input=input;
        try {
            NN.input.takeinput();
        } catch (IOException e) {
            System.out.println("PROBLM WITH INPUT ");
            e.printStackTrace();
        }
        NN.teach(2000);
        System.out.println(NN.Error());

    }



}
























