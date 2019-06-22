package com.justincamp.demo.util;

/*
    Original Code, based on a medium.com article @
    https://medium.com/google-developer-experts/using-navigation-architecture-component-in-a-large-banking-app-ac84936a42c2
 */

import androidx.navigation.NavDirections;

public class NavigationCommand {

    public enum NAVIGATION_TYPE {
        TO,
        BACK,
        BACK_TO,
        TO_ROOT
    }

    private NAVIGATION_TYPE type;
    private NavDirections directions;
    private int destination;

    private NavigationCommand(NAVIGATION_TYPE type) {
        this.type = type;
    }

    private NavigationCommand(NAVIGATION_TYPE type, NavDirections directions) {
        this.type = type;
        this.directions = directions;
    }

    private NavigationCommand(NAVIGATION_TYPE type, int destination) {
        this.type = type;
        this.destination = destination;
    }

    public NAVIGATION_TYPE getType() {
        return type;
    }

    public NavDirections getDirections() {
        return directions;
    }

    public int getDestination() {
        return destination;
    }

    /**
     * Command to navigate to a given screen
     * @param directions
     * @return NavigationCommand object instance
     */
    public static NavigationCommand To(NavDirections directions) {
        return new NavigationCommand(NAVIGATION_TYPE.TO, directions);
    }

    /**
     * Command to navigate backwards one screen
     * @return NavigationCommand object instance
     */
    public static NavigationCommand Back() {
        return new NavigationCommand(NAVIGATION_TYPE.BACK);
    }

    /**
     * Command to navigate backwards to the given screen
     * @param directions
     * @return NavigationCommand object instance
     */
    public static NavigationCommand BackTo(int destination) {
        return new NavigationCommand(NAVIGATION_TYPE.BACK_TO, destination);
    }

    /**
     * Command to navigate back to the root/home screen
     * @return NavigationCommand object instance
     */
    public static NavigationCommand toRoot() {
        return new NavigationCommand(NAVIGATION_TYPE.TO_ROOT);
    }

}
