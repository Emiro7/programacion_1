package Clase4;

import java.util.Scanner;

public class Ejercio41b {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        // si la temperatura esta por encima de 100 sube el punto de ebullicion
        System.out.println("digite la temperatura...");
        int temp = sc.nextInt();
        if (temp >= 100) {
            System.out.println("punto de ebullicion ");
        } else {
            System.out.println("no esta en punto de ebullicion ");

        }
        sc.close();
    }

}
