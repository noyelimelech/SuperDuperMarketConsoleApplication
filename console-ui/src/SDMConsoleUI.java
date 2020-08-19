public class SDMConsoleUI
{
    private SDMEngine engine;
    private final String[] mainMenu = {"Load new XML file.","Show all stores information","Show all items information.","Make new order.","Show all orders.","Exit."};

    public void start() {
        showMenu();

    }

    private void showMenu() {
        for (int i = 0; i < mainMenu.length; i++) {
            System.out.println(i+1 + mainMenu[i]);
        }


    }


}
