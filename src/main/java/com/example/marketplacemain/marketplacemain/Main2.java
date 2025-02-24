package com.example.marketplacemain.marketplacemain;

import java.util.HashMap;
import java.util.Map;

public class Main2 {

    public static void main(String[] args) {
         
            // guardar los datos
            // Producto producto = new Producto();
            // int[] nums = {2,7,11,15};
            // int target = 9;
            // int[] t = twoSum(nums, target);

            // System.out.println(t[0] + " : "+ t[1]);
        
           
           
            String[] strs = {"flower","flow","flight"};

            String response = longestCommonPrefix(strs);
            System.out.println(response);

    }
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> retornar = new HashMap<>();


        for(int i = 0; i < nums.length; i++ ){

            int restante = target - nums[i];


            if (  retornar.containsKey(restante)) {
                
                int[] valor =  {retornar.get(restante) ,  i};
                return valor;
            }
            retornar.put(nums[i], i);
            
        }

        int[] valor2 = {};
        return valor2;
        
    }

    // public static String longestCommonPrefix(String[] strs) {

    //     String cadena = "";
    //     String[] comparatve =  strs[0].split("");
    //     for (int i = 0; i < comparatve.length; i++) {
    //         int counter = 0;
    //         for (int j = 0; j < strs.length; j++) {
    //             if ( i >= strs[j].split("").length   ) {
    //                 break;
    //             }
    //             if (comparatve[i].equals(strs[j].split("")[i]) ) {
    //                 counter = counter + 1;
    //             }
    //             if (counter == strs.length) {
    //                 cadena = cadena + comparatve[i];
    //             }
    //         }
    //         if (counter != strs.length) {
    //             break;
                
    //         }
    //     }
    //     return cadena;
    // }


    public static String longestCommonPrefix(String[] strs) {

        String cadena = "";
        String[] first =  strs[0].split("");
        String[] ultimp =  strs[strs.length - 1].split("");

        for (int i = 0; i < Math.min(first.length, ultimp.length); i++) {
            
        }
        
        return cadena;
    }
}
