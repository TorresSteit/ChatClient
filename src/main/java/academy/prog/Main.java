package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

			System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				String to = "";


				if (text.equalsIgnoreCase("history")) {
					// Логика для просмотра истории сообщений
					// Например, вызов метода для получения списка отправленных сообщений
					// и вывод их на экран
					continue;
				}

				
				if (text.startsWith("@")) {
					int spaceIndex = text.indexOf(' ');
					if (spaceIndex != -1) {
						to = text.substring(1, spaceIndex);  // Извлекаем получателя из сообщения
						text = text.substring(spaceIndex + 1);  // Удаляем получателя из сообщения
					} else {
						System.out.println("Invalid private message format. Please use '@recipient message'");
						continue;
					}
				}

				Message m = new Message(login, to, text);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) {
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			scanner.close();
		}
	}
}
