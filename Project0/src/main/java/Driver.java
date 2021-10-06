// This class is my Driver class.

import menus.MainMenu;

/**
 * This is where our program starts. The main method contains the primary loop as well as call to the Main menu.
 */
public class Driver {
    public static void main(String[] args){
        MainMenu mainMenu = new MainMenu();

        /*
          This is the main loop. It will keep running until the variable called running, in the MainMenu class, is set
         equal to false. This occurs when the user chooses option 3 in the Main menu.
         */
        while(mainMenu.isRunning()) {
            mainMenu.viewMainMenu();
        }
    }

}

