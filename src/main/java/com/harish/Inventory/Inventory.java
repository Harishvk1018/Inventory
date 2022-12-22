package com.harish.Inventory;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class Inventory 
{
	
		public static void main(String[] args) 
		{
			
			ArrayList<UserModel> users = new ArrayList<>();
			//Admin users
			users.add(new UserModel(1001,true,"Harish","8825907507","1234"));
			users.add(new UserModel(1002,true,"Ajai","8825987901","1234"));
			//Agent users
			users.add(new UserModel(1003,false,"Jeeva","9894409501","1234"));
			users.add(new UserModel(1004,false,"Akila","8248653667","1234"));
			
			Scanner scn=new Scanner(System.in);
			boolean isContinue = true;
			do {
				System.out.println("Menu\n1. Admin Login\n2. Agent Login\n3. Exit");
				int op = scn.nextInt();
				boolean isContinue1 = true;
				if(op == 1) {
					System.out.println("Enter admin username");
					String adminUname = scn.next();
					System.out.println("Enter admin password");
					String adminPassword = scn.next();
					int i = 0;
					for(i = 0;i<users.size();i++) {
						UserModel user = users.get(i);
						if(user.getUserName().equals(adminUname) && user.isAdmin) {
							if(user.getUserPassword().equals(adminPassword)) {
								System.out.println("Login success !");

								break;
							}else {
								System.out.println("Password is incorrect");
							}
						}
					}
					if(i != users.size()) {
						do {
							isContinue1 = adminPage(users.get(i).userId);
						}while(isContinue1);
					}else {
						System.out.println("Username does not exist");
					}
				}else if(op == 2) {
					System.out.println("Enter agent username");
					String adminUname = scn.next();
					System.out.println("Enter agent password");
					String adminPassword = scn.next();
					int i = 0;
					for(i = 0;i<users.size();i++) {
						UserModel user = users.get(i);
						if(user.getUserName().equals(adminUname) && !user.isAdmin) {
							if(user.getUserPassword().equals(adminPassword)) {
								System.out.println("Login success !");

								break;
							}else {
								System.out.println("Password is incorrect");
							}
						}
					}
					if(i != users.size()) {
						do {
							isContinue1 = agentPage(users.get(i).userId);
						}while(isContinue1);
					}else {
						System.out.println("Username does not exist");
					}
				}else if(op == 3) {
					isContinue = false;
				}else {
					System.out.println("Enter a number between 1-3");
				}
				
			}while(isContinue);
			
		}
		static boolean adminPage(int agentId) {
			Scanner scn=new Scanner(System.in);
			
			System.out.println("1. Add Product\n"
					+ "2. Display Inventory Details\n"
					+ "3. Logout ");
			int op1 = scn.nextInt();
			if(op1 == 1) {
				Product product = new Product();

				//Setting agent id and date
				product.setAgentId(agentId);
				product.setDate(System.currentTimeMillis()+"");
				product.setQuantity(0);

				System.out.println("Enter product ID");
				product.setId(scn.nextInt());
				
				System.out.println("Enter product name");
				product.setName(scn.next());
				
				System.out.println("Enter Minimum selling quantity");
				product.setMinSellQuantity(scn.nextInt());
				
				System.out.println("Enter product price");
				product.setPrice(scn.nextDouble());
				
				//SQL insert code
				saveProductDetails(product);
			}else if(op1 == 2) {
			
				//SQL get code
				getAllProductData();
			}else if(op1 == 3) {
				return false;
			}else {
				System.out.println("Enter a number between 1-3");
			}
			return true;
		}
		
		static boolean agentPage(int agentId) {
			Scanner scn=new Scanner(System.in);
			System.out.println("1. Buy / Sell\n"
					+ "2. Show History\n"
					+ "3. Logout ");
			int op1 = scn.nextInt();
			if(op1 == 1) {
				System.out.println("Enter product ID");
				int productId = scn.nextInt();
				System.out.println("Do you want to 1. Buy\n2. Sell?");
				int op2 = scn.nextInt();
				if(op2 == 1 || op2 == 2) {
					Product _product = getById(productId);
					if(_product.getName() != null && _product.getName().length()>0) {
						System.out.println("Product Name "+_product.getName()
						+"Product Quantity "+_product.getQuantity()
						+"Product Price "+_product.getPrice());
					}
					//After display
					System.out.println("Enter product quantity");
					int quantity = scn.nextInt();
					if(op2 == 1) {
						//Buy
						if(quantity <= _product.getMinSellQuantity()) {
							double totalCost = quantity * _product.getPrice();
							System.out.println("Total cost for "+quantity+" of "+_product.getName()+" is "+totalCost);
							System.out.println("To confirm press 1");
							if(scn.nextInt() == 1) {
								// Reduce quantity from sql
								updateQuantity(_product.getId(),(_product.getQuantity() - quantity));
							}else {
								System.out.println("Cancelled");
							}
						}else {
							// Quantity required is greater
							System.out.println("Quantity required is greater than the current stock!");
						}
					}else if (op2 == 2) {
						//Sell
						if(quantity >= _product.getMinSellQuantity()) {
							double totalCost = quantity * _product.getPrice();
							System.out.println("Total cost for "+quantity+" of "+_product.getName()+" is "+totalCost);
							System.out.println("To confirm press 1");
							if(scn.nextInt() == 1) {
								// Add quantity to sql
								updateQuantity(_product.getId(),(_product.getQuantity() + quantity));
							}else {
								System.out.println("Cancelled");
							}
						}else {
							// Lesser than min sell quantity
							System.out.println("Quantity to sell is lesser than the MinSellQuantity!");

						}
					}
				}else {
					System.out.println("Enter 1 or 2");	
				}
			}else if(op1 == 2) {
				//Get data of agent and display
				//Due to lack of time, I havent fully completed this part
				//getAgentProducts(agentId);
				
			}else if(op1 == 3) {
				return false;
			}else {
				System.out.println("Enter a number between 1-3");
			}
			return true;
		}
	
	static void saveProductDetails(Product product)
	{
		try
		{
			Configuration cfg=new Configuration();
			cfg.configure();
			SessionFactory sessionfactory=cfg.buildSessionFactory();
			Session session=sessionfactory.openSession();
			Transaction transcation=session.beginTransaction();
			session.save(product);
			transcation.commit();
			
		}
		catch(Exception e)
		{
			
			
		}
	}

	public static void getAllProductData()
	{
		Session session=SessionGiver.getSessionFactory().openSession();
		String query="from Product";
		Query createQuery = session.createQuery(query);
		List<Product> products = createQuery.list();
		for(Product product:products) {
			String name = product.getName();
			int id = product.getId();
			double price = product.getPrice();
			int quantity = product.getQuantity();
			
			if(name != null && name.length()>0 ) {
				System.out.println("Product Id "+product.getId()
				+"\nProduct Name "+name
				+"\nProduct Quantity available "+quantity
				+"\nPrice "+price
				+"\nTotal Cost "+(price*quantity));
				System.out.println();
			}
		}
	
	}
	
	public static void getAgentProducts(int agent_id)
	{
		Session session=SessionGiver.getSessionFactory().openSession();
		String query="from AgentTransactions where agent_id=:n";
		Query createQuery = session.createQuery(query);
		createQuery.setParameter("n", agent_id);

		List<Product> products = createQuery.list();
		for(Product product:products) {
			String name = product.getName();
			int id = product.getId();
			double price = product.getPrice();
			int quantity = product.getQuantity();
			
			if(name != null && name.length()>0 ) {
				System.out.println("Product Id "+product.getId()
				+"\nProduct Name "+name
				+"\nProduct Quantity available "+quantity
				+"\nPrice "+price
				+"\nTotal Cost "+(price*quantity));
				System.out.println();
			}
		}
	
	}
	
	public static Product getById(int id)
	{
		try {
		Configuration cfg=new Configuration();
		cfg.configure();
		SessionFactory sessionfactory=cfg.buildSessionFactory();
		Session session=sessionfactory.openSession();
		return session.get(Product.class, id);
		}catch(Exception e) {
			System.out.println("Product deosnt exist in DB");
			return null;
		}
	}

	public static void updateQuantity(int id,int quantity) {
		
		Session session = SessionGiver
							.getSessionFactory()
							.openSession();
		String query="update Product set quantity=:m where id=:n";
		Query createQuery = session.createQuery(query);
		Transaction transaction = session.beginTransaction();
		createQuery.setParameter("m", quantity);
		createQuery.setParameter("n", id);
		 createQuery.executeUpdate();
		 transaction.commit();
		
	}
	
}
