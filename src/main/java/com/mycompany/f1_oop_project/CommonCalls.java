/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.f1_oop_project;

/**
 *
 * @author X1 G7
 */
public class CommonCalls {
    
    public static final FileHandler<UserAccountInfo> userHandler = new UserHandler();
    public static final AdminHandler adminHandler = new AdminHandler();
    public static final UserMenu userMenu = new UserMenu();
    public static final AdminMenu adminMenu = new AdminMenu();
    public static final StockHandler stockHandler = new StockHandler();

    CommonCalls(){
        
    }
}
