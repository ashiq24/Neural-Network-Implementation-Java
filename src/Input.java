/**
 * Created by Ashiqur Rahman on 3/25/2017.
 * this a simple ANN. without regularization .
 * mainly give to adopt the breast cancer data set.
 * it 65% of accuracy
 * it is just assigning 0 to missing attribute
 */
import java.io.*;
public class Input {
    int feacher;
    int size;
    int inputs[][];
    BufferedReader cin;
    public Input(int f, int s)
    {
        feacher=f;
        size=s;
        inputs=new int[size+1][feacher+1];
        cin=new BufferedReader(new InputStreamReader(System.in));
    }
    boolean  takeinput() throws IOException {
        int flag=0;
        for(int i=0;i<size;i++)
        {
            String s=cin.readLine();
            String num[]=s.split(",");
            for(int n=1;n<num.length;n++)
            {
                if(!num[n].equals("?"))
                {
                    inputs[i][n-1]=Integer.parseInt(num[n]);
                    flag++;
                }
                else inputs[i][n-1]=0;
            }
            if(inputs[i][feacher]==2) inputs[i][feacher]=1;
            else inputs[i][feacher]=0;
        }
        System.out.println(flag);
        return true;
    }

    public static void main(String[] args) {
    }
}
