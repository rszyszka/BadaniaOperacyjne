package model;

public class CycleNotFoundException extends Exception {
    CycleNotFoundException(){
        super("Cycle could not be found!");
    }
}