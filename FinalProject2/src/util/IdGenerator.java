/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Random;

/**
 *
 * @author User
 */
public class IdGenerator {
    public static String generateId(int year){
        Random random = new Random();
        int r = random.nextInt(1000000);  
        return String.format("%d-%06d", year, r); 
    }
    
}
