import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class OptionMenuList extends Account {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'₹'###,##0.00");

	HashMap<Integer, Integer> user = new HashMap<Integer, Integer>();

	public void getLogin() throws IOException {
		int x = 1;
		int y = 1;

		do {
			try {
				user.put(123456789, 1234);
				user.put(123456700, 1200);
				user.put(123456780, 1230);
				if (y == 1)
					System.out.println("Welcome to Our ATM!");

				System.out.print("Enter Your Customer Number: ");
				setCustomerNumber(menuInput.nextInt());

				System.out.print("Enter Your PIN: ");
				setPinNumber(menuInput.nextInt());
			} catch (Exception e) {
				System.out.println("\n" + "ERROR!!!! Invalid character(s). Enter Only NUMBERS." + "\n");
				System.out.println("Thank You for using our ATM, Visit Again." + "\n");
				x = 2;
				break;
			}
			for (Entry<Integer, Integer> entry : user.entrySet()) {
				if (entry.getKey() == getCustomerNumber() && entry.getValue() == getPinNumber()) {
					getAccountType();
				}
			}
			System.out.println("\n" + "ERROR!!!! Wrong Customer Number or PIN." + "\n");
			y = 2;
		} while (x == 1);
	}

	public void getAccountType() {
		System.out.println("Select the Type of Account you want to access: ");
		System.out.println("Type 1 - Current Account");
		System.out.println("Type 2 - Savings Account");
		System.out.println("Type 3 - Exit");
		System.out.print("Choice: ");

		selection = menuInput.nextInt();
		System.out.println();
		switch (selection) {
			case 1:
				getCurrent();
				break;

			case 2:
				getSavings();
				break;

			case 3:
				System.out.println("Thank You for using our ATM, Visit Again.");
				break;

			default:
				System.out.println("\n" + "Invalid Choice." + "\n");
				getAccountType();
		}
	}

	public void getCurrent() {
		System.out.println("Current Account: ");
		System.out.println("Type 1 - View Balance");
		System.out.println("Type 2 - Withdraw Funds");
		System.out.println("Type 3 - Deposit Funds");
		System.out.println("Type 4 - Go back to Main Menu");
		System.out.println("Type 5 - Exit");
		System.out.print("Choice: ");

		selection = menuInput.nextInt();

		switch (selection) {
			case 1:
				System.out.println("Current Account Balance: " + moneyFormat.format(getCurrentBalance()) + "\n");
				getCurrent();
				break;

			case 2:
				getCurrentWithdrawInput();
				getCurrent();
				break;

			case 3:
				getCurrentDepositInput();
				getCurrent();
				break;

			case 4:
				getAccountType();
				break;

			case 5:
				System.out.println("Thank You for using our ATM, Visit Again.");
				break;

			default:
				System.out.println("\n" + "Invalid choice." + "\n");
				getCurrent();
		}
	}

	public void getSavings() {
		System.out.println("Savings Account: ");
		System.out.println("Type 1 - View Balance");
		System.out.println("Type 2 - Withdraw Funds");
		System.out.println("Type 3 - Deposit Funds");
		System.out.println("Type 4 - Go back to Main Menu");
		System.out.println("Type 5 - Exit");
		System.out.print("Choice: ");

		selection = menuInput.nextInt();

		switch (selection) {
			case 1:
				System.out.println("Savings Account Balance: " + moneyFormat.format(getSavingsBalance()) + "\n");
				getSavings();
				break;

			case 2:
				getSavingsWithdrawInput();
				getSavings();
				break;

			case 3:
				getSavingsDepositInput();
				getSavings();
				break;

			case 4:
				getAccountType();
				break;

			case 5:
				System.out.println("Thank You for using our ATM, Visit Again.");
				break;

			default:
				System.out.println("\n" + "Invalid choice." + "\n");
				getSavings();
		}
	}

	int selection;
}