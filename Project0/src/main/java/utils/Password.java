package utils;

import models.Users;

import java.util.Scanner;

/**
 * This class contains all methods for obtaining and verifying passwords from the user console.
 * */
public class Password {
    Scanner input;
    Users users = new Users();

    public Password(Scanner input) {
        this.input = input;
    }
}
