public class Tester {

	public static void main(String[] args) {	
				// Request[] array1 = {"Hello", "How are you?", "Nice to meet you", "What's up?", "Good morning"};
		 String[] array2 = {"I love Java", "Programming is fun", "Java is easy", "Coding is life", "I am a programmer"};
		// Request[] array3 = {"Pizza is delicious", "I like sushi", "Burgers are my favorite", "Tacos are amazing", "I love ice cream"};
		// Request[] array4 = {"Reading is a good habit", "I enjoy playing sports", "Traveling is fun", "Spending time with family is important", "Learning new things is exciting"};
		// Request[] array5 = {"Life is short", "Be kind to others", "Stay positive", "Believe in yourself", "Chase your dreams"};
		// Request[] requests1 = {
			// new Request("Fix bug in login feature", 3, 2, 4),
			// new Request("Add new payment gateway", 5, 3, 1),
			// new Request("Optimize database queries", 2, 1, 5),
			// new Request("Update user interface", 4, 4, 2),
			// new Request("Implement new search algorithm", 1, 5, 3)
		// };
		// Request[] requests = new Request[5];
		// requests[0] = new Request("description 1", 1, 2, 3);
		// requests[1] = new Request("description 2", 3, 4, 5);
		// requests[2] = new Request("description 3", 2, 1, 4);
		// requests[3] = new Request("description 4", 5, 3, 2);
		// requests[4] = new Request("description 5", 4, 5, 1);
		Request[] requests = new Request[3];
		String[] itemsToBuy = {"item1", "item2", "item3"};
		requests[0] = new BuyingProducts("Buy items", 1, 5, itemsToBuy);
		String[] questions = {"Question 1", "Question 2", "Question 3"};
		requests[1] = new InformationRequest("Information request", 2, 3, questions);
		String[] itemsToReturn = {"item4", "item5", "item6", "ho"};
		requests[2] = new ReturningItems("Return items", 3, 4, itemsToReturn);
        // requests[1] = new Request("Implement feature", 1, 5, 2);
        // requests[2] = new Request("Refactor code", 3, 2, 3);
        // requests[3] = new Request("Write tests", 4, 1, 1);
        // requests[4] = new Request("Optimize performance", 5, 4, 5);
		Client a1 = new Client("name1", "n", 1232, "MALE", 2, 2, requests);
		Client a2 = new Client("name2", "p",1332, "MALE", 3, 3, requests);
		Client a3 = new Client("name3 s","s", 1432, "MALE", 2, 2, requests);
		Client a4 = new Client("name4 k","k", 1572, "MALE", 1, 1,requests);

		Client[] hi = {a1, a2, a3, a4};
		QueueSystem a5 = new QueueSystem(3, true, false);
		
		QueueSystem.setClientsWorld(hi);
		a5.increaseTime();
	
	
	
	
	
	
	
	
	
	}






}