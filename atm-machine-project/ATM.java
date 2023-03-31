/*  Main Driver Code for this ATM Project */

import java.io.IOException;

public class ATM extends OptionMenuList {
	public static void main(String[] args) throws IOException {
		OptionMenuList optionMenu = new OptionMenuList();

		optionMenu.getLogin();
	}
}
