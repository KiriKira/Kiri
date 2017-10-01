import java.util.Scanner;

class ScannerDemo
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("It's the first");

        double sum = 0;
        int m = 0;

        while(scan.hasNextDouble())
        {
            double x = scan.nextDouble();
            m = m + 1;
            sum = sum + x;
        }

        System.out.println(m+"个数的和为"+sum);
        System.out.println(m+"个数的平均值是"+(sum/m));
    }
}